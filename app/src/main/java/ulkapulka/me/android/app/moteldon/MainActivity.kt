package ulkapulka.me.android.app.moteldon

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ulkapulka.me.android.app.moteldon.databinding.ActivityMainBinding
import ulkapulka.me.android.app.moteldon.storage.DataStorage
import ulkapulka.me.android.app.moteldon.storage.Settings
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        var instance: MainActivity? = null
        var context: Context? = null
        var dataStorage: DataStorage = DataStorage()
        var settings: Settings = Settings()
        var dataFile: File? = null
        var settingsFile: File? = null
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        loadFiles()
        super.onCreate(savedInstanceState)
        instance = this
        context = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomMenu = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    if (bottomMenu.selectedItemId != R.id.home) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, HomeFragment(), "home")
                            addToBackStack(null)
                        }
                    }
                    true
                }
                R.id.rooms -> {
                    if (bottomMenu.selectedItemId != R.id.rooms) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, RoomsFragment(), "rooms")
                            addToBackStack(null)
                        }
                    }
                    true
                }
                R.id.guests -> {
                    if (bottomMenu.selectedItemId != R.id.guests) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, GuestsFragment (), "guests")
                            addToBackStack(null)
                        }
                    }
                    true
                }
                R.id.settings -> {
                    if (bottomMenu.selectedItemId != R.id.settings) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, SettingsFragment(), "settings")
                            addToBackStack(null)
                        }
                    }
                    true
                }
                else -> false
            }
        }
        bottomMenu.selectedItemId = R.id.home
    }

    fun loadFiles() {
        dataFile = File(filesDir, "data.mtd")
        settingsFile = File(filesDir, "settings.mtd")
        if (dataFile!!.exists()) {
            dataStorage = try {
                Utils.readFromFile(dataFile!!)
            } catch (e: Exception) {
                Utils.sendErrorDialog(this, "Ошибка при загрузке данных с памяти!")
                DataStorage()
            }
        }
        if (settingsFile!!.exists()) {
            settings = try {
                Utils.readFromFile(settingsFile!!)
            } catch (e: Exception) {
                Utils.sendErrorDialog(this, "Ошибка при загрузке настроек с памяти!")
                Settings()
            }
        }
    }
}