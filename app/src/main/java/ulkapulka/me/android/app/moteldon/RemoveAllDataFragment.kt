package ulkapulka.me.android.app.moteldon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.commit
import ulkapulka.me.android.app.moteldon.storage.DataStorage
import ulkapulka.me.android.app.moteldon.storage.Settings
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RemoveAllDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RemoveAllDataFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_remove_all_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.delete_yes_button).setOnClickListener {
            try {
                Utils.writeToFile(DataStorage(), MainActivity.dataFile!!)
                Utils.writeToFile(Settings(), MainActivity.settingsFile!!)
                MainActivity.instance?.loadFiles()
                MainActivity.instance?.supportFragmentManager?.commit {
                    setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                    replace(R.id.fragmentContainerView, SettingsFragment(), "settings")
                    addToBackStack(null)
                }
                Utils.sendDialog(MainActivity.context!!, "Все данные удалены!")
            } catch (e: Exception) {
                Utils.sendDialog(MainActivity.context!!, e.message)
            }
        }
        view.findViewById<Button>(R.id.delete_no_button).setOnClickListener {
            try {
                MainActivity.instance?.supportFragmentManager?.commit {
                    setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                    replace(R.id.fragmentContainerView, SettingsFragment(), "settings")
                    addToBackStack(null)
                }
                Utils.sendDialog(MainActivity.context!!, "Ничего не удалено :)")
            } catch (e: Exception) {
                Utils.sendDialog(MainActivity.context!!, e.message)
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
         * @return A new instance of fragment RemoveAllDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RemoveAllDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}