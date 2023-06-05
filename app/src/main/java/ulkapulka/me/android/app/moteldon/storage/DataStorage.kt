package ulkapulka.me.android.app.moteldon.storage

import kotlinx.serialization.Serializable
import ulkapulka.me.android.app.moteldon.MainActivity
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.storage.data.Room
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.lang.Exception
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Serializable
class DataStorage {

    val enters = mutableListOf<GuestEnter>()
    val guests = mutableListOf<Guest>()

    fun addEnter(enter: GuestEnter) {
        removeEnter(enter)
        enters.add(enter)
        saveToFile()
    }

    fun replaceEnter(enter: GuestEnter) {
        enters.removeIf { it.guestId == enter.guestId && it.type == enter.type && it.time.isEqual(enter.time) }
        enters.add(enter)
        saveToFile()
    }

    fun removeEnter(enter: GuestEnter) {
        enters.removeIf { it.guestId == enter.guestId && it.type == enter.type && it.time.isEqual(enter.time) }
        saveToFile()
    }

    fun addGuest(guest: Guest) {
        removeGuest(guest)
        guests.add(guest)
        saveToFile()
    }

    fun replaceGuest(guest: Guest) {
        guests.removeIf { it.id == guest.id }
        guests.add(guest)
        saveToFile()
    }

    fun removeGuest(guest: Guest) {
        guests.removeIf { it.id == guest.id }
        enters.removeIf { it.guestId == guest.id }
        saveToFile()
    }

    private fun saveToFile() {
        Utils.writeToFile(this, MainActivity.dataFile!!)
    }

    fun getGuest(guestEnter: GuestEnter): Guest? {
        val id = guestEnter.guestId
        guests.forEach {
            if (id == it.id) {
                return it
            }
        }
        return null
    }

    fun getGuestByName(name: String): Guest? {
        val nameLower = name.lowercase().replace("ё", "е")
        val nameReplace = nameLower.replace(" ", "")
        val nameSplit = nameLower.split(" ", "").toMutableList()
        nameSplit.removeIf { it.isEmpty() }
        guests.forEach { guest ->
            val currentLower = guest.name.lowercase().replace("ё", "е")
            val currentReplace = currentLower.replace(" ", "")
            val currentSplit = currentLower.split(" ", "").toMutableList()
            currentSplit.removeIf { it.isEmpty() }

            if (nameReplace == currentReplace || nameSplit.containsAll(currentSplit)) {
                return guest
            }
        }
        return null
    }

    fun getGuestsByRoom(room: Room): List<Guest> {
        val list = mutableListOf<Guest>()
        guests.forEach {
            if (it.room.number == room.number) {
                list.add(it)
            }
        }
        return list
    }

    fun getRooms(): MutableList<Room> {
        val rooms = mutableListOf<Room>()
        guests.forEach {
            if (!rooms.contains(it.room)) {
                rooms.add(it.room)
            }
        }
        return rooms
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