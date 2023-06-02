package ulkapulka.me.android.app.moteldon

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ulkapulka.me.android.app.moteldon.databinding.ActivityMainBinding
import ulkapulka.me.android.app.moteldon.storage.DataStorage
import ulkapulka.me.android.app.moteldon.storage.Settings

class MainActivity : AppCompatActivity() {

    companion object {
        var instance: MainActivity? = null
        var context: Context? = null
        val dataStorage = DataStorage()
        val settings = Settings()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
}