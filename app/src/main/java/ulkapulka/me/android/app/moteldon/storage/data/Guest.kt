package ulkapulka.me.android.app.moteldon.storage.data

import kotlinx.serialization.Serializable
import java.time.LocalDate

data class Guest(val name: String, val birthday: LocalDate, val room: Room)
