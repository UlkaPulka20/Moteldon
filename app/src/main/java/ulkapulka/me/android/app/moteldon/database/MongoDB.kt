package ulkapulka.me.android.app.moteldon.database

import ulkapulka.me.android.app.moteldon.database.abstracts.MongoConnection

object MongoDb : MongoConnection() {

    init {
        connect()
    }

    override fun getSettings(): DataBaseSettings {
        return DataBaseSettings()
            .setHost("dev.kiinse.me")
            .setPort("27017")
            .setLogin("admin")
            .setPassword("admin")
            .setDbName("moteldon")
            .setAuthDb("admin")
    }

    override fun createTables() {
        createCollectionIfNotExists("accounts")
    }
}