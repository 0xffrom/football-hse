package goshka133.football.domain_profile.dto

import goshka133.football.core_network.serializers.EnumAsIntSerializer
import kotlinx.serialization.Serializable

@Serializable(with = RoleSerializer::class)
enum class RoleType(val value: Int) {
  Student(0),
  Alumni(1),
  Legioner(2),
  Employee(3),
}

private class RoleSerializer :
  EnumAsIntSerializer<RoleType>(
    "hseRole",
    { it.value },
    { v -> RoleType.values().first { it.value == v } }
  )
