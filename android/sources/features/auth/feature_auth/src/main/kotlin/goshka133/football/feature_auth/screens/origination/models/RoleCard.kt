package goshka133.football.feature_auth.screens.origination.models

import android.os.Parcelable
import goshka133.football.domain_profile.dto.RoleType
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class RoleCard(
  val type: RoleType,
  val title: String,
) : Parcelable {

  companion object {

    fun createList() = buildList {
      add(RoleCard(type = RoleType.Student, title = "Студент"))
      add(RoleCard(type = RoleType.Alumni, title = "Выпускник"))
      add(RoleCard(type = RoleType.Employee, title = "Сотрудник"))
      add(RoleCard(type = RoleType.Legioner, title = "Легионер"))
    }
  }
}
