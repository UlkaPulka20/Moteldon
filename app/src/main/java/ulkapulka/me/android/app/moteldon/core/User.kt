package ulkapulka.me.android.app.moteldon.core

import ulkapulka.me.android.app.moteldon.utils.Utils

data class User(val login: String, val email: String, val password: String) {

    companion object {
        fun valueOf(login: String?): User? {
            return UserManager.getUser(login)
        }

        fun valueOf(login: String?, password: String?): User? {
            return UserManager.getUser(login, password)
        }
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other != null && other is User) {
            return other.hashCode() == hashCode()
        }
        return false
    }

    override fun toString(): String {
        return "login=$login\npassword=$password\nemail=$email"
    }

    fun checkPassword(password: String): Boolean {
        return Utils.bcryptCheck(password, this.password!!)
    }
}
