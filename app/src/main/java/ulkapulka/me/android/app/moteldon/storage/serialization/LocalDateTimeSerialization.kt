package ulkapulka.me.android.app.moteldon.storage.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object LocalDateTimeSerialization : KSerializer<LocalDateTime> {

    override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val data = decoder.decodeString().split("#(-)#")
        return LocalDateTime.of(LocalDate.ofEpochDay(data[0].toLong()), LocalTime.ofSecondOfDay(data[1].toLong()))
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val date = value.toLocalDate().toEpochDay()
        val time = value.toLocalTime().toSecondOfDay()
        encoder.encodeString("$date#(-)#$time")
    }
}