package goshka133.football.di

import dagger.Module
import goshka133.football.core_network.di.NetworkModule

@Module(includes = [NetworkModule::class])
object CoreModule