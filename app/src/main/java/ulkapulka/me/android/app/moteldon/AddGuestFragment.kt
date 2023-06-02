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
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.storage.data.Room
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddGuestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddGuestFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_add_guest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val guestName = view.findViewById<EditText>(R.id.guest_name_edit)
        val guestBirthday = view.findViewById<EditText>(R.id.guest_birthday_edit)
        val guestRoom = view.findViewById<EditText>(R.id.guest_room_edit)

        view.findViewById<Button>(R.id.add_guest_add_button).setOnClickListener {
            try {
                val dataStorage = MainActivity.dataStorage
                val guest = dataStorage.getGuestByName(guestName.text.toString())

                if (guest != null) {
                    Utils.sendErrorDialog(MainActivity.context!!, "Гость уже добавлен!")
                } else {
                    val roomNumber = guestRoom.text.toString().toInt()
                    if (roomNumber > MainActivity.settings.maxRooms) {
                        Utils.sendErrorDialog(MainActivity.context!!, "Такой комнаты не существует!")
                    } else {
                        dataStorage.addGuest(Guest(guestName.text.toString(),
                            guestBirthday.text.toString(),
                            Room(roomNumber)
                        ))

                        Utils.sendErrorDialog(MainActivity.context!!, "Добавлено!")
                        MainActivity.instance?.supportFragmentManager?.commit {
                            setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                            replace(R.id.fragmentContainerView, GuestsFragment(), "guests")
                            addToBackStack(null)
                        }
                    }
                }
            } catch (e: Exception) {
                Utils.sendErrorDialog(MainActivity.context!!, e.message)
            }
        }

        view.findViewById<Button>(R.id.add_guest_back_button).setOnClickListener {
            MainActivity.instance?.supportFragmentManager?.commit {
                setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                replace(R.id.fragmentContainerView, GuestsFragment(), "guests")
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
         * @return A new instance of fragment AddGuestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddGuestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}