package andryuh.football.domain_search.filters

import android.os.Parcelable
import andryuh.football.core_models.PlayerPosition
import andryuh.football.core_models.Tournament
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
  val positions: List<PlayerPosition> = PlayerPosition.values().toList(),
  val tournaments: List<Tournament> = Tournament.values().toList(),
) : Parcelable
