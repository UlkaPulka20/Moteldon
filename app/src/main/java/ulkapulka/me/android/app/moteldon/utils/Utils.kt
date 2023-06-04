package ulkapulka.me.android.app.moteldon.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer
import ulkapulka.me.android.app.moteldon.MainActivity
import ulkapulka.me.android.app.moteldon.R
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter


object Utils {

    fun sendErrorDialog(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    inline fun <reified T> writeToFile(value: T, file: File) {
        val stream = FileOutputStream(file)
        try {
            stream.write(Json.encodeToString(serializersModule.serializer(), value).toByteArray())
        } catch (e: Exception) {
            sendErrorDialog(MainActivity.context!!, e.message)
        } finally {
            stream.close()
        }
    }

    inline fun <reified T> readFromFile(file: File): T {
        val bytes = ByteArray(file.length().toInt())
        val inputStream = FileInputStream(file)
        try {
            inputStream.read(bytes)
        } catch (e: Exception) {
            sendErrorDialog(MainActivity.context!!, e.message)
        } finally {
            inputStream.close()
        }
        return Json.decodeFromString(serializersModule.serializer(), String(bytes))
    }

    fun createGuestEnterLayout(context: Context, enter: GuestEnter, layout: LinearLayout) {
        val enterLayout = LinearLayout(context)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            50)
        params.height = 170
        params.setMargins(10, 10, 10, 10)
        enterLayout.layoutParams = params
        enterLayout.orientation = LinearLayout.HORIZONTAL

        val nameLayout = LinearLayout(context)
        val nameParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        nameLayout.layoutParams = nameParams
        nameLayout.orientation = LinearLayout.HORIZONTAL

        val otherLayout = LinearLayout(context)
        val otherParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        otherLayout.layoutParams = otherParams
        otherLayout.orientation = LinearLayout.HORIZONTAL

        enterLayout.addView(otherLayout)
        enterLayout.addView(nameLayout)

        createButton(context, enter.guest.name, enterLayout,
            ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_name),
            ContextCompat.getColor(context, R.color.black))
        if (enter.type == EnterType.JOIN) {
            createButton(context, "Зашёл", otherLayout,
                ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_enter),
                ContextCompat.getColor(context, R.color.black))
        } else {
            createButton(context, "Вышел", otherLayout,
                ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_exit),
                ContextCompat.getColor(context, R.color.white))
        }
        createButton(context, enter.time.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")), otherLayout,
            ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_time),
            ContextCompat.getColor(context, R.color.white))

        enterLayout.id = "${enter.guest.name}_${enter.guest.birthday}_${enter.time}_${enter.type}_1".hashCode()
        nameLayout.id = "${enter.guest.name}_${enter.guest.birthday}_${enter.time}_${enter.type}_2".hashCode()
        otherLayout.id = "${enter.guest.name}_${enter.guest.birthday}_${enter.time}_${enter.type}_3".hashCode()
        layout.addView(enterLayout)
    }

    fun createGuestLayout(context: Context, guest: Guest, layout: LinearLayout) {
        val enterLayout = LinearLayout(context)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            50)
        params.height = 170
        params.setMargins(10, 10, 10, 10)
        enterLayout.layoutParams = params
        enterLayout.orientation = LinearLayout.HORIZONTAL

        val nameLayout = LinearLayout(context)
        val nameParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        nameLayout.layoutParams = nameParams
        nameLayout.orientation = LinearLayout.HORIZONTAL

        val otherLayout = LinearLayout(context)
        val otherParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        otherLayout.layoutParams = otherParams
        otherLayout.orientation = LinearLayout.HORIZONTAL

        createButton(context, "Комната: ${guest.room.number}", otherLayout,
            ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_room),
            ContextCompat.getColor(context, R.color.black))
        createButton(context, guest.birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), otherLayout,
            ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_birthday),
            ContextCompat.getColor(context, R.color.black))
        createButton(context, guest.name, enterLayout,
            ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_name),
            ContextCompat.getColor(context, R.color.black))

        enterLayout.addView(nameLayout)
        enterLayout.addView(otherLayout)

        enterLayout.id = "${guest.name}_${guest.birthday}".hashCode()
        nameLayout.id = "${guest.name}_${guest.birthday}".hashCode()
        otherLayout.id = "${guest.name}_${guest.birthday}".hashCode()
        layout.addView(enterLayout)
    }

    fun createRoomsLayout(context: Context, room: Int, guests: List<Guest>, layout: LinearLayout) {
        val enterLayout = LinearLayout(context)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            50)
        params.height = 170
        params.setMargins(10, 10, 10, 10)
        enterLayout.layoutParams = params
        enterLayout.orientation = LinearLayout.HORIZONTAL

        val nameLayout = LinearLayout(context)
        val nameParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        nameLayout.layoutParams = nameParams
        nameLayout.orientation = LinearLayout.HORIZONTAL

        val otherLayout = LinearLayout(context)
        val otherParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        otherLayout.layoutParams = otherParams
        otherLayout.orientation = LinearLayout.HORIZONTAL

        createButton(context, "Комната: $room", otherLayout,
            ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_room),
            ContextCompat.getColor(context, R.color.black))

        guests.forEach {
            createButton(context, it.name, enterLayout,
                ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_name),
                ContextCompat.getColor(context, R.color.black))
        }

        enterLayout.addView(otherLayout)
        enterLayout.addView(nameLayout)

        enterLayout.id = "room_${room}_1".hashCode()
        nameLayout.id = "room_${room}_2".hashCode()
        otherLayout.id = "room_${room}_3".hashCode()
        layout.addView(enterLayout)
    }

    @SuppressLint("SetTextI18n")
    fun createButton(context: Context, text: String, layout: LinearLayout, background: Drawable?, color: Int) {
        val btnTag = Button(context)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        params.height = 150
        params.setMargins(10, 10, 15, 0)
        btnTag.layoutParams = params
        btnTag.background = background
        btnTag.setTextColor(color)
        btnTag.text = "  $text  "
        btnTag.textSize = 12F
        layout.addView(btnTag)
    }

}