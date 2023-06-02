package ulkapulka.me.android.app.moteldon.storage

import kotlinx.serialization.Serializable
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.storage.data.Room

@Serializable
class DataStorage {

    val enters = mutableListOf<GuestEnter>()
    val guests = mutableListOf<Guest>()

    init {
        val firstGuest = Guest("Гарри Поттер", "10.05.2001", Room(3))
        val secondGuest = Guest("Иван Герасимов", "06.02.1995", Room(5))
        val thirdGuest = Guest("Олег Авдеев", "25.12.1993", Room(17))
        val fourthGuest = Guest("Гермиона Грейнджер", "17.11.2001", Room(3))
        addGuest(firstGuest)
        addGuest(secondGuest)
        addGuest(thirdGuest)
        addGuest(fourthGuest)
        addEnter(GuestEnter(firstGuest, EnterType.JOIN, "10:23"))
        addEnter(GuestEnter(firstGuest, EnterType.EXIT, "12:00"))
        addEnter(GuestEnter(secondGuest, EnterType.JOIN, "18:30"))
        addEnter(GuestEnter(secondGuest, EnterType.EXIT, "23:15"))
        addEnter(GuestEnter(thirdGuest, EnterType.JOIN, "12:37"))
        addEnter(GuestEnter(thirdGuest, EnterType.EXIT, "16:12"))
        addEnter(GuestEnter(fourthGuest, EnterType.JOIN, "11:28"))
        addEnter(GuestEnter(fourthGuest, EnterType.EXIT, "12:01"))
    }

    fun addEnter(enter: GuestEnter) {
        enters.remove(enter)
        enters.add(enter)
    }

    fun removeEnter(enter: GuestEnter) {
        enters.remove(enter)
    }

    fun addGuest(guest: Guest) {
        guests.remove(guest)
        guests.add(guest)
    }

    fun removeGuest(guest: Guest) {
        guests.remove(guest)
    }

    fun getGuestByName(name: String): Guest? {
        guests.forEach {
            if (it.name.equals(name, ignoreCase = true)) {
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