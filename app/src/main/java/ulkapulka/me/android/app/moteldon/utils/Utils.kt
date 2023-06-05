package ulkapulka.me.android.app.moteldon.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
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

    fun sendDialog(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    inline fun <reified T> writeToFile(value: T, file: File) {
        val stream = FileOutputStream(file)
        try {
            stream.write(Json.encodeToString(serializersModule.serializer(), value).toByteArray())
        } catch (e: Exception) {
            sendDialog(MainActivity.context!!, e.message)
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
            sendDialog(MainActivity.context!!, e.message)
        } finally {
            inputStream.close()
        }
        return Json.decodeFromString(serializersModule.serializer(), String(bytes))
    }

}