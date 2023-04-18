package andryuh.football.core_network.serializers

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class EnumListAsIntSerializer<T : Enum<*>>(
  serialName: String,
  val enumArray: Array<T>,
) : KSerializer<List<T>> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)

  override fun serialize(encoder: Encoder, value: List<T>) {
    var sum = 0

    value.forEach { enum ->
      val index = enumArray.indexOf(enum)

      sum += 2.0.pow(index.toDouble()).roundToInt()
    }

    encoder.encodeInt(sum)
  }

  override fun deserialize(decoder: Decoder): List<T> {
    var value = decoder.decodeInt()

    val list: List<T> = buildList {
      var index = 0
      while (value != 0) {
        if (value % 2 == 0) {
          add(enumArray[index])
        }
        value = value shr 1
        index += 1
      }
    }

    return list
  }
}
