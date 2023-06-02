package ulkapulka.me.android.app.moteldon.storage.data

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

data class GuestEnter(val guest: Guest, val type: EnterType, val time: LocalDateTime)
