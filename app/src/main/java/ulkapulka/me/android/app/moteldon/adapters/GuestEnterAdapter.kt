package ulkapulka.me.android.app.moteldon.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ulkapulka.me.android.app.moteldon.MainActivity
import ulkapulka.me.android.app.moteldon.R
import ulkapulka.me.android.app.moteldon.databinding.FragmentItemGuestBinding
import ulkapulka.me.android.app.moteldon.databinding.FragmentItemGuestEnterBinding
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.time.format.DateTimeFormatter

class GuestEnterDiffUtil(
    private val oldList: List<GuestEnter>,
    private val newList: List<GuestEnter>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson.guestId == newPerson.guestId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson.guestId == newPerson.guestId
    }
}

class GuestEnterAdapter : RecyclerView.Adapter<GuestEnterAdapter.GuestEnterViewHolder>(), View.OnClickListener {

    var data: MutableList<GuestEnter> = mutableListOf()
        set(newValue) {
            val personDiffUtilResult = DiffUtil.calculateDiff(GuestEnterDiffUtil(field, newValue))
            field = newValue
            personDiffUtilResult.dispatchUpdatesTo(this@GuestEnterAdapter)
        }

    override fun getItemCount(): Int = MainActivity.dataStorage.enters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestEnterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemGuestEnterBinding.inflate(inflater, parent, false)

        return GuestEnterViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GuestEnterViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            val context = MainActivity.context!!
            val enter = data[position]
            val guest = MainActivity.dataStorage.getGuest(enter)

            with(holder.binding) {
                holder.itemView.tag = guest
                name.text = "  ${guest?.name}  "
                name.textSize = 10F
                time.text = "  ${enter.time.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))}  "
                if (enter.type == EnterType.JOIN) {
                    type.text = "  Вход  "
                    type.background = ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_enter)
                    type.setTextColor(ContextCompat.getColor(context, R.color.black))
                } else {
                    type.text = "  Выход  "
                    type.background = ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_exit)
                    type.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }
        }
    }

    override fun onClick(view: View) {}

    @SuppressLint("NotifyDataSetChanged")
    fun removeEnter(position: Int) {
        val storage = MainActivity.dataStorage
        val enter = data[position]
        storage.removeEnter(enter)
        data.remove(enter)
        notifyDataSetChanged()
        Utils.sendDialog(MainActivity.context!!, "Запись удалена!")
    }

    class GuestEnterViewHolder(val binding: FragmentItemGuestEnterBinding) : RecyclerView.ViewHolder(binding.root)
}