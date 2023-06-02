package ulkapulka.me.android.app.moteldon.database.abstracts

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.MongoException
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.connection.ClusterSettings
import ulkapulka.me.android.app.moteldon.database.DataBaseSettings

abstract class MongoConnection {

    private var client: MongoClient? = null
    private var dataBase: MongoDatabase? = null

    fun getDataBase(): MongoDatabase {
        return dataBase!!
    }

    fun getClient(): MongoClient {
        return client!!
    }

    @Throws(MongoException::class)
    fun connect() {
        val settings = getSettings()
        val credential = MongoCredential.createCredential(settings.login, settings.authDb, settings.password.toCharArray())
        try {
            client = MongoClients.create(
                MongoClientSettings.builder()
                    .applyToClusterSettings { builder: ClusterSettings.Builder ->
                        builder.hosts(
                            listOf(
                                when (settings.port.isBlank()) {
                                    true -> ServerAddress(settings.host)
                                    else -> ServerAddress(settings.host, settings.port.toInt())
                                }
                            )
                        )
                    }
                    .credential(credential)
                    .build()
            )
            if (client != null) {
                dataBase = client!!.getDatabase(settings.dbName)
                createTables()
            } else {
                throw MongoException("Client is null!")
            }
        } catch (e: Exception) {
            val message = e.message
            if (message != null) throw MongoException(message)
        }
    }

    abstract fun getSettings(): DataBaseSettings
    abstract fun createTables()

    fun createCollectionIfNotExists(collection: String) {
        if (dataBase!!.listCollectionNames().contains(collection)) return
        dataBase!!.createCollection(collection)
    }
}