package andryuh.football.feature_auth.screens.origination.utils

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NameTextFieldValidatorTest :
  FunSpec({
    test("Test with incorrect names") {
      NameTextFieldValidator.isCorrect("          ").shouldBe(false)
      NameTextFieldValidator.isCorrect("31231231").shouldBe(false)
      NameTextFieldValidator.isCorrect(" a").shouldBe(false)
      NameTextFieldValidator.isCorrect("          ").shouldBe(false)
      NameTextFieldValidator.isCorrect("a ").shouldBe(false)
      NameTextFieldValidator.isCorrect("l5").shouldBe(false)
      NameTextFieldValidator.isCorrect("5215215 1251512").shouldBe(false)
      NameTextFieldValidator.isCorrect("??????").shouldBe(false)
      NameTextFieldValidator.isCorrect(" a!").shouldBe(false)
      NameTextFieldValidator.isCorrect("a!").shouldBe(false)
      NameTextFieldValidator.isCorrect("5151").shouldBe(false)
      NameTextFieldValidator.isCorrect("A ").shouldBe(false)
      NameTextFieldValidator.isCorrect("Test Testovich&").shouldBe(false)
      NameTextFieldValidator.isCorrect("TEST??").shouldBe(false)
      NameTextFieldValidator.isCorrect("()!@#$%^&*()_+?><").shouldBe(false)
      NameTextFieldValidator.isCorrect("####").shouldBe(false)
      NameTextFieldValidator.isCorrect("Я ").shouldBe(false)
    }
    test("Test with correct names") {
      NameTextFieldValidator.isCorrect("Ян").shouldBe(true)
      NameTextFieldValidator.isCorrect("Вася").shouldBe(true)
      NameTextFieldValidator.isCorrect(" Ян").shouldBe(true)
      NameTextFieldValidator.isCorrect(" Ян     ").shouldBe(true)
      NameTextFieldValidator.isCorrect("Анатолий").shouldBe(true)
    }
  })
