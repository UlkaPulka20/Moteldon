package ulkapulka.me.android.app.moteldon.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ulkapulka.me.android.app.moteldon.MainActivity
import ulkapulka.me.android.app.moteldon.databinding.FragmentItemGuestBinding
import ulkapulka.me.android.app.moteldon.storage.data.Guest
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.time.format.DateTimeFormatter

class GuestDiffUtil(
    private val oldList: List<Guest>,
    private val newList: List<Guest>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson.id == newPerson.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPerson = oldList[oldItemPosition]
        val newPerson = newList[newItemPosition]
        return oldPerson.id == newPerson.id
    }
}

class GuestAdapter : RecyclerView.Adapter<GuestAdapter.GuestViewHolder>(), View.OnClickListener {

    var data: MutableList<Guest> = mutableListOf()
        set(newValue) {
            val personDiffUtilResult = DiffUtil.calculateDiff(GuestDiffUtil(field, newValue))
            field = newValue
            personDiffUtilResult.dispatchUpdatesTo(this@GuestAdapter)
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemGuestBinding.inflate(inflater, parent, false)

        return GuestViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            val guest = data[position]

            with(holder.binding) {
                holder.itemView.tag = guest
                name.text = "  ${guest.name}  "
                name.textSize = 10F
                room.text = "  Комната: ${guest.room.number}  "
                birthday.text = "  ${guest.birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}  "
            }
        }
    }

    override fun onClick(view: View) {}

    @SuppressLint("NotifyDataSetChanged")
    fun removeGuest(position: Int) {
        val storage = MainActivity.dataStorage
        val guest = data[position]
        storage.removeGuest(guest)
        data.remove(guest)
        notifyDataSetChanged()
        Utils.sendDialog(MainActivity.context!!, "Гость '${guest.name}' удалён")
    }

    fun getGuest(position: Int): Guest {
        return data[position]
    }

    class GuestViewHolder(val binding: FragmentItemGuestBinding) : RecyclerView.ViewHolder(binding.root)
}