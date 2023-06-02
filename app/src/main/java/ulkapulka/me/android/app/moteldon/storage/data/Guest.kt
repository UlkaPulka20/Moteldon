package ulkapulka.me.android.app.moteldon.storage.data

import kotlinx.serialization.Serializable

@Serializable
data class Guest(val name: String, val birthday: String, val room: Room)
