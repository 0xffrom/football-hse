package goshka133.football.feature_auth.screens.auth.page

import goshka133.football.feature_auth.screens.auth.page.phone.phoneFormat
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty

class PhonePageKtTest :
  FunSpec({
    test("PhonePage phone formatter") {
      "".phoneFormat().shouldBeEmpty()
      "9".phoneFormat().shouldBe("-9")
      "98".phoneFormat().shouldBe("-98")
      "988".phoneFormat().shouldBe("-988")
      "9887".phoneFormat().shouldBe("-988-7")
      "98870".phoneFormat().shouldBe("-988-70")
      "988702".phoneFormat().shouldBe("-988-702")
      "9887026".phoneFormat().shouldBe("-988-702-6")
      "98870269".phoneFormat().shouldBe("-988-702-69")
      "988702693".phoneFormat().shouldBe("-988-702-69-3")
      "9887026936".phoneFormat().shouldBe("-988-702-69-36")
    }
  })
