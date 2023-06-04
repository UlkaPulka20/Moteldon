package ulkapulka.me.android.app.moteldon.storage

import kotlinx.serialization.Serializable
import ulkapulka.me.android.app.moteldon.MainActivity
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.storage.data.Room
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Serializable
class DataStorage {

    val enters = mutableListOf<GuestEnter>()
    val guests = mutableListOf<Guest>()

    fun addEnter(enter: GuestEnter) {
        enters.remove(enter)
        enters.add(enter)
        saveToFile()
    }

    fun removeEnter(enter: GuestEnter) {
        enters.remove(enter)
        saveToFile()
    }

    fun addGuest(guest: Guest) {
        guests.remove(guest)
        guests.add(guest)
        saveToFile()
    }

    fun removeGuest(guest: Guest) {
        guests.remove(guest)
        saveToFile()
    }

    private fun saveToFile() {
        Utils.writeToFile(this, MainActivity.dataFile!!)
    }

    fun getGuestByName(name: String): Guest? {
        val currentName = name.lowercase().replace(" ", "")
        guests.forEach {
            if (currentName == it.name.lowercase().replace(" ", "")) {
                return it
            }
        }
        return null
    }

    fun getGuestsByRoom(room: Int): List<Guest> {
        val list = mutableListOf<Guest>()
        guests.forEach {
            if (it.room.number == room) {
                list.add(it)
            }
        }
        return list
    }

    fun countRooms(): Int {
        val rooms = mutableListOf<Room>()
        guests.forEach {
            if (!rooms.contains(it.room)) {
                rooms.add(it.room)
            }
        }
        return rooms.count()
    }

}