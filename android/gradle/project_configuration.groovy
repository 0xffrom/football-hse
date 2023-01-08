import java.util.regex.Pattern

class ProjectConfiguration {

    final ROOT_MODULE_PROPERTY_NAME = "rootModule"
    final PROPERTIES_FILE_NAME = "local.properties"
    final MODULE_BUILD_FILE_NAME_KTS = "build.gradle.kts"
    final MODULE_BUILD_FILE_NAME = "build.gradle"
    Pattern PROJECT_DEPENDENCY_REGEX = ~"project\\(\":([a-zA-Z_]+)\"\\)"

    final sourceRoot = new File(projectRoot, "sources")
    File projectRoot

    ProjectConfiguration(File projectRoot) {
        this.projectRoot = projectRoot
    }

    void apply(Closure include) {
        final rootModuleName = readRootModuleFromProperties()
        if (rootModuleName == null) {
            includeAllModules(include)
        } else {
            includeUsedModules(rootModuleName, include)
        }
    }

    void includeAllModules(Closure include) {
        final foundModuleDirs = new LinkedList([sourceRoot])
        while (!foundModuleDirs.isEmpty()) {
            final moduleDir = foundModuleDirs.poll()
            if (isModuleRoot(moduleDir)) {
                include(":${moduleDir.name}", moduleDir)
            } else {
                foundModuleDirs.addAll(moduleDir.listFiles() ?: new File[0])
            }
        }
    }

    void includeUsedModules(String rootModuleName, Closure include) {
        final rootModuleDir = findModuleDirByName(rootModuleName)
        final foundModuleDirs = new LinkedList([rootModuleDir])
        while (!foundModuleDirs.isEmpty()) {
            final moduleDir = foundModuleDirs.poll()
            include(":${moduleDir.name}", moduleDir)
            final dependencies = listDirectModuleDependencies(moduleDir)
            foundModuleDirs.addAll(dependencies)
        }
    }

    String readRootModuleFromProperties() {
        final properties = new Properties()
        final propertiesFile = new File(PROPERTIES_FILE_NAME)
        if (!propertiesFile.exists()) {
            return null
        }
        properties.load(new FileInputStream(propertiesFile))
        return properties.getProperty(ROOT_MODULE_PROPERTY_NAME) ?: null
    }

    List<File> listDirectModuleDependencies(File moduleDir) {
        final ktsBuildScript = new File(moduleDir, MODULE_BUILD_FILE_NAME_KTS)
        final groovyBuildScript = new File(moduleDir, MODULE_BUILD_FILE_NAME)
        File buildScript
        if (ktsBuildScript.exists()) {
            buildScript = ktsBuildScript
        } else if (groovyBuildScript.exists()) {
            buildScript = groovyBuildScript
        } else {
            throw new IllegalStateException("There's no valid buildscript in module : $moduleDir.absolutePath")
        }
        return checkDependenciesInFile(buildScript)
    }

    List<File> checkDependenciesInFile(File file) {
        return file.readLines()
                .collect {
                    final result = PROJECT_DEPENDENCY_REGEX.matcher(it)
                    if (result.find()) result.group(1) else null
                }
                .findAll { it != null }
                .collect { findModuleDirByName(it) }
    }

    File findModuleDirByName(String moduleName) {
        final moduleSearchNodes = new LinkedList([sourceRoot])
        while (!moduleSearchNodes.isEmpty()) {
            final searchNode = moduleSearchNodes.poll()
            moduleSearchNodes.addAll(searchNode.listFiles() ?: new File[0])
            if (isModuleRoot(searchNode)) {
                if (searchNode.name == moduleName) {
                    return searchNode
                }
            }
        }
        throw IllegalArgumentException("Module with name $moduleName was not found in source root ${sourceRoot.absolutePath}")
    }

    boolean isModuleRoot(File file) {
        return new File(file, MODULE_BUILD_FILE_NAME_KTS).exists() || new File(file, MODULE_BUILD_FILE_NAME).exists()
    }
}

new ProjectConfiguration(rootProject.projectDir).apply { moduleName, moduleDir ->
    include(moduleName)
    project(moduleName).projectDir = moduleDir
}

