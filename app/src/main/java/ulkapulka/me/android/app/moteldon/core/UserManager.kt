package ulkapulka.me.android.app.moteldon.core

import com.mongodb.client.MongoCollection
import org.bson.Document
import ulkapulka.me.android.app.moteldon.database.MongoDb

@Suppress("UNUSED")
object UserManager {

    private var collection: MongoCollection<Document>? = null

    init {
        reload()
    }

    fun reload() {
        collection = MongoDb.getDataBase().getCollection("accounts")
    }

    private fun userToDocument(user: User): Document {
        val query = Document()
        query["_id"] = user.login
        query["password"] = user.password
        query["email"] = user.email
        return query
    }

    fun createUser(user: User) {
        collection!!.insertOne(userToDocument(user))
    }

    fun hasUser(login: String?): Boolean {
        if (login == null) return false
        val query = Document()
        query["_id"] = login
        val result = collection!!.find(query).first()
        return result != null && !result.isEmpty()
    }

    fun hasUser(login: String, password: String): Boolean {
        val query = Document()
        query["_id"] = login
        val user = parseUserResult(collection!!.find(query).first()) ?: return false
        if (user.checkPassword(password)) return true
        return false
    }

    fun getUser(login: String?): User? {
        if (login == null) return null
        val query = Document()
        query["_id"] = login
        return parseUserResult(collection!!.find(query).first())
    }

    fun getUser(login: String?, password: String?): User? {
        if (login == null || password == null) return null
        val query = Document()
        query["_id"] = login
        val user = parseUserResult(collection!!.find(query).first()) ?: return null
        if (user.checkPassword(password)) return user
        return null
    }

    private fun parseUserResult(result: Document?): User? {
        if (result == null || result.keys.size < 3) return null
        return User(
            result["_id"].toString(),
            result["email"].toString(),
            result["password"].toString(),
        )
    }
}