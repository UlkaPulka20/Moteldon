package ulkapulka.me.android.app.moteldon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.commit
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddGuestEnterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddGuestEnterFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_add_guest_enter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val guestName = view.findViewById<EditText>(R.id.guest_enter_name_edit)
        val isEntered = view.findViewById<RadioButton>(R.id.enter_button)
        val time = view.findViewById<EditText>(R.id.guest_enter_time)

        view.findViewById<Button>(R.id.add_guest_enter_add_button).setOnClickListener {
            try {
                val dataStorage = MainActivity.dataStorage
                val guest = dataStorage.getGuestByName(guestName.text.toString())

                if (guest == null) {
                    Utils.sendErrorDialog(MainActivity.context!!, "Гость не найден!")
                } else {
                    dataStorage.addEnter(GuestEnter(guest, when(isEntered.isChecked) {
                        true -> EnterType.JOIN
                        false -> EnterType.EXIT
                    }, time.text.toString()))

                    Utils.sendErrorDialog(MainActivity.context!!, "Добавлено!")
                    MainActivity.instance?.supportFragmentManager?.commit {
                        setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                        replace(R.id.fragmentContainerView, HomeFragment(), "home")
                        addToBackStack(null)
                    }
                }
            } catch (e: Exception) {
                Utils.sendErrorDialog(MainActivity.context!!, e.message)
            }
        }

        view.findViewById<Button>(R.id.add_guest_enter_back_button).setOnClickListener {
            MainActivity.instance?.supportFragmentManager?.commit {
                setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                replace(R.id.fragmentContainerView, HomeFragment(), "home")
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
         * @return A new instance of fragment AddGuestEnterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddGuestEnterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}