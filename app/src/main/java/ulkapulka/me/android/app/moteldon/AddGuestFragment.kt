package ulkapulka.me.android.app.moteldon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.commit
import com.santalu.maskara.widget.MaskEditText
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.storage.data.Room
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

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
        val guestBirthday = view.findViewById<MaskEditText>(R.id.guest_birthday_edit)
        val guestRoom = view.findViewById<EditText>(R.id.guest_room_edit)

        guestBirthday.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        guestRoom.setText("1")

        view.findViewById<Button>(R.id.add_guest_add_button).setOnClickListener {
            try {
                val dataStorage = MainActivity.dataStorage
                val name = guestName.text.toString()
                if (name.isEmpty()) {
                    Utils.sendDialog(MainActivity.context!!, "Имя не может быть пустым!")
                    return@setOnClickListener
                }

                val birthdayText = guestBirthday.text.toString()
                if (birthdayText.isEmpty()) {
                    Utils.sendDialog(MainActivity.context!!, "День рождения не может быть пустым!")
                    return@setOnClickListener
                }
                val guest = dataStorage.getGuestByName(name)
                if (guest != null) {
                    Utils.sendDialog(MainActivity.context!!, "Гость уже добавлен!")
                    return@setOnClickListener
                }
                val roomNumber = guestRoom.text.toString().toInt()
                if (roomNumber > MainActivity.settings.maxRooms || roomNumber <= 0) {
                    Utils.sendDialog(MainActivity.context!!, "Такой комнаты не существует!")
                    return@setOnClickListener
                }
                val settedDate = LocalDate.parse(birthdayText, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                dataStorage.addGuest(Guest(UUID.randomUUID().toString(),
                    name,
                    settedDate,
                    Room(roomNumber)
                ))
                Utils.sendDialog(MainActivity.context!!, "Добавлено!")
                MainActivity.instance?.supportFragmentManager?.commit {
                    setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                    replace(R.id.fragmentContainerView, GuestsFragment(), "guests")
                    addToBackStack(null)
                }
            } catch (e: Exception) {
                Utils.sendDialog(MainActivity.context!!, e.message)
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