package ulkapulka.me.android.app.moteldon.storage.data

import kotlinx.serialization.Serializable

@Serializable
data class GuestEnter(val guest: Guest, val type: EnterType, val time: String)
