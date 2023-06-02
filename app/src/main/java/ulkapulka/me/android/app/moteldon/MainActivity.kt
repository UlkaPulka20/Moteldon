package ulkapulka.me.android.app.moteldon

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import ulkapulka.me.android.app.moteldon.core.User
import ulkapulka.me.android.app.moteldon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        var user: User? = null
        var instance: MainActivity? = null
    }

    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val serviceFragment = ServiceFragment()
    private val infoFragment = InfoFragment ()
    private val settingsFragment = SettingsFragment()
    private val bottomMenu = findViewById<BottomNavigationView>(R.id.nav_view)
    private val authView = findViewById<FragmentContainerView>(R.id.loginView)
    private val fragmentView = findViewById<FragmentContainerView>(R.id.fragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateVisibility()

        bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    if (bottomMenu.selectedItemId != R.id.home) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, homeFragment, "home")
                            addToBackStack(null)
                        }
                    }
                    true
                }
                R.id.service -> {
                    if (bottomMenu.selectedItemId != R.id.service) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, serviceFragment, "service")
                            addToBackStack(null)
                        }
                    }
                    true
                }
                R.id.info -> {
                    if (bottomMenu.selectedItemId != R.id.info) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, infoFragment, "info")
                            addToBackStack(null)
                        }
                    }
                    true
                }
                R.id.settings -> {
                    if (bottomMenu.selectedItemId != R.id.settings) {
                        supportFragmentManager.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, settingsFragment, "settings")
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

    fun updateVisibility() {
        if (user == null) {
            authView.visibility = View.VISIBLE
            fragmentView.visibility = View.INVISIBLE
            bottomMenu.visibility = View.INVISIBLE
        } else {
            authView.visibility = View.INVISIBLE
            fragmentView.visibility = View.VISIBLE
            bottomMenu.visibility = View.VISIBLE
        }
    }
}