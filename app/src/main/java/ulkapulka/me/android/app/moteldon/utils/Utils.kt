package ulkapulka.me.android.app.moteldon.utils

import android.content.Context
import android.widget.Toast
import org.mindrot.jbcrypt.BCrypt

object Utils {

    fun bcryptHash(input: String): String {
        return BCrypt.hashpw(input, BCrypt.gensalt())
    }

    fun bcryptCheck(input: String, hash: String): Boolean {
        return BCrypt.checkpw(input, hash)
    }

    fun sendErrorDialog(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}