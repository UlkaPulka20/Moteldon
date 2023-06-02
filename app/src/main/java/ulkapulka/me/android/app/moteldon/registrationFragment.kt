package ulkapulka.me.android.app.moteldon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.commit
import ulkapulka.me.android.app.moteldon.core.User
import ulkapulka.me.android.app.moteldon.core.UserManager
import ulkapulka.me.android.app.moteldon.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [registrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class registrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<AppCompatButton>(R.id.button_signup).setOnClickListener {
            val user = User.valueOf(view.findViewById<EditText>(R.id.editTextLoginLogin).text.toString(),
                view.findViewById<EditText>(R.id.editTextPasswordLogin).text.toString()
            )
            val login = view.findViewById<EditText>(R.id.editTextLoginRegister).text.toString()
            val password = view.findViewById<EditText>(R.id.editTextPasswordRegister).text.toString()
            val passwordSecond = view.findViewById<EditText>(R.id.editTextPasswordRegisterSecond).text.toString()
            val email = view.findViewById<EditText>(R.id.editTextEmailRegister).text.toString()

            if (User.valueOf(login) != null) {
                Utils.sendErrorDialog(view.context, "Логин занят!")
            }

            if (password != passwordSecond) {
                Utils.sendErrorDialog(view.context, "Пароли не совпадают!")
            }

            // TODO: Проверка, что логин больше 4 знаков
            // TODO: Проверка, что пароль больше 8 знаков

            UserManager.createUser(User(login, password, email))

            MainActivity.instance?.supportFragmentManager?.commit{
                setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                replace(R.id.loginView, AuthFragment(), "login")
                addToBackStack(null)
            }
        }

        view.findViewById<AppCompatButton>(R.id.button_back).setOnClickListener {
            MainActivity.instance?.supportFragmentManager?.commit{
                setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                replace(R.id.loginView, AuthFragment(), "login")
                addToBackStack(null)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment registrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            registrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}