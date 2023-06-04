package ulkapulka.me.android.app.moteldon.storage.data

import kotlinx.serialization.Serializable
import ulkapulka.me.android.app.moteldon.storage.serialization.LocalDateSerialization
import java.time.LocalDate

@Serializable
data class Guest(val name: String, @Serializable(with = LocalDateSerialization::class) val birthday: LocalDate, val room: Room)
