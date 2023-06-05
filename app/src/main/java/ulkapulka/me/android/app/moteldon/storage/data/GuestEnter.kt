package ulkapulka.me.android.app.moteldon.storage.data

import kotlinx.serialization.Serializable
import ulkapulka.me.android.app.moteldon.storage.serialization.LocalDateTimeSerialization
import java.time.LocalDateTime

@Serializable
data class GuestEnter(val guestId: String, val type: EnterType, @Serializable(with = LocalDateTimeSerialization::class) val time: LocalDateTime)
