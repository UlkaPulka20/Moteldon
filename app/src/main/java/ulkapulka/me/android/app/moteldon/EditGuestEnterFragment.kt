package ulkapulka.me.android.app.moteldon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.fragment.app.commit
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santalu.maskara.widget.MaskEditText
import ulkapulka.me.android.app.moteldon.adapters.GuestEnterAdapter
import ulkapulka.me.android.app.moteldon.gestures.SwipeGesture
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditGuestEnterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditGuestEnterFragment : Fragment {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var enter: GuestEnter? = null

    constructor() {}

    constructor(enter: GuestEnter) {
        this.enter = enter
    }

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
        return inflater.inflate(R.layout.fragment_edit_guest_enter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val guestName = view.findViewById<EditText>(R.id.edit_guest_enter_name_edit)
        val isEntered = view.findViewById<RadioButton>(R.id.edit_enter_button)
        val time = view.findViewById<MaskEditText>(R.id.edit_guest_enter_time)
        val dataStorage = MainActivity.dataStorage
        val guest = dataStorage.getGuest(enter!!)

        time.setText(enter!!.time.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")))
        guestName.setText(guest!!.name)
        isEntered.isChecked = enter!!.type == EnterType.JOIN
        view.findViewById<RadioButton>(R.id.edit_exit_button).isChecked = enter!!.type == EnterType.EXIT

        view.findViewById<Button>(R.id.edit_guest_enter_save_button).setOnClickListener {
            try {
                val name = guestName.text.toString()
                if (name.isEmpty()) {
                    Utils.sendDialog(MainActivity.context!!, "Имя не может быть пустым!")
                    return@setOnClickListener
                }
                val timeText = time.text.toString()
                if (timeText.isEmpty()) {
                    Utils.sendDialog(MainActivity.context!!, "Время не может быть пустым!")
                    return@setOnClickListener
                }
                val settedTime = try {
                    LocalDateTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))
                } catch (e: Exception) {
                    val text = timeText.replace(" ", "").split(":")
                    LocalDateTime.now().withHour(text[0].toInt()).withMinute(text[1].toInt())
                }
                dataStorage.addEnter(GuestEnter(guest.id, when(isEntered.isChecked) {
                    true -> EnterType.JOIN
                    false -> EnterType.EXIT
                }, settedTime))
                Utils.sendDialog(MainActivity.context!!, "Изменено!")
                MainActivity.instance?.supportFragmentManager?.commit {
                    setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                    replace(R.id.fragmentContainerView, HomeFragment(), "home")
                    addToBackStack(null)
                }
            } catch (e: Exception) {
                Utils.sendDialog(MainActivity.context!!, e.message)
            }
        }

        view.findViewById<Button>(R.id.edit_guest_enter_back_button).setOnClickListener {
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
         * @return A new instance of fragment EditGuestEnterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditGuestEnterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}