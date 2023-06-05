package ulkapulka.me.android.app.moteldon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.commit
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ulkapulka.me.android.app.moteldon.adapters.GuestAdapter
import ulkapulka.me.android.app.moteldon.databinding.FragmentGuestsBinding
import ulkapulka.me.android.app.moteldon.gestures.SwipeGesture
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuestsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuestsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_guests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val guestsView = view.findViewById<RecyclerView>(R.id.guestsView)
        val dataStorage = MainActivity.dataStorage

        val adapter = GuestAdapter()
        val list = dataStorage.guests
        list.reverse()
        adapter.data = list
        val swipe = object : SwipeGesture() {
            override fun onLeftSwipe(viewHolder: RecyclerView.ViewHolder) {
                adapter.removeGuest(viewHolder.layoutPosition)
            }

            override fun onRightSwipe(viewHolder: RecyclerView.ViewHolder) {
                MainActivity.instance?.supportFragmentManager?.commit {
                    setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                    replace(R.id.fragmentContainerView, EditGuestFragment(adapter.getGuest(viewHolder.layoutPosition)), "guest_edit")
                    addToBackStack(null)
                }
            }
        }

        ItemTouchHelper(swipe).attachToRecyclerView(guestsView)

        guestsView.layoutManager = LinearLayoutManager(context)
        guestsView.adapter = adapter

        view.findViewById<ImageButton>(R.id.guests_add_button).setOnClickListener {
            MainActivity.instance?.supportFragmentManager?.commit {
                setCustomAnimations(R.anim.open_animator, R.anim.close_animator)
                replace(R.id.fragmentContainerView, AddGuestFragment(), "guest_add")
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
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuestsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}