package ulkapulka.me.android.app.moteldon.storage

import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.storage.data.Room
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class DataStorage {

    val enters = mutableListOf<GuestEnter>()
    val guests = mutableListOf<Guest>()

    init {
        val firstGuest = Guest("Гарри Поттер", LocalDate.now(), Room(3))
        val secondGuest = Guest("Иван Герасимов", LocalDate.now(), Room(5))
        val thirdGuest = Guest("Олег Авдеев", LocalDate.now(), Room(17))
        val fourthGuest = Guest("Гермиона Грейнджер", LocalDate.now(), Room(3))
        addGuest(firstGuest)
        addGuest(secondGuest)
        addGuest(thirdGuest)
        addGuest(fourthGuest)
        addEnter(GuestEnter(firstGuest, EnterType.JOIN, LocalDateTime.ofInstant(Instant.now().plusSeconds(10000), ZoneId.systemDefault())))
        addEnter(GuestEnter(firstGuest, EnterType.EXIT, LocalDateTime.ofInstant(Instant.now().plusSeconds(180000), ZoneId.systemDefault())))
        addEnter(GuestEnter(secondGuest, EnterType.JOIN, LocalDateTime.ofInstant(Instant.now().plusSeconds(50000), ZoneId.systemDefault())))
        addEnter(GuestEnter(secondGuest, EnterType.EXIT, LocalDateTime.ofInstant(Instant.now().plusSeconds(230000), ZoneId.systemDefault())))
        addEnter(GuestEnter(thirdGuest, EnterType.JOIN, LocalDateTime.ofInstant(Instant.now().plusSeconds(80000), ZoneId.systemDefault())))
        addEnter(GuestEnter(thirdGuest, EnterType.EXIT, LocalDateTime.ofInstant(Instant.now().plusSeconds(740000), ZoneId.systemDefault())))
        addEnter(GuestEnter(fourthGuest, EnterType.JOIN, LocalDateTime.ofInstant(Instant.now().plusSeconds(100000), ZoneId.systemDefault())))
        addEnter(GuestEnter(fourthGuest, EnterType.EXIT, LocalDateTime.ofInstant(Instant.now().plusSeconds(900000), ZoneId.systemDefault())))
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