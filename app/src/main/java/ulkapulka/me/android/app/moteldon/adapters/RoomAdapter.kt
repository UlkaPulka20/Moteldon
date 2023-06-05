package ulkapulka.me.android.app.moteldon.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ulkapulka.me.android.app.moteldon.MainActivity
import ulkapulka.me.android.app.moteldon.R
import ulkapulka.me.android.app.moteldon.databinding.FragmentItemGuestBinding
import ulkapulka.me.android.app.moteldon.databinding.FragmentItemGuestEnterBinding
import ulkapulka.me.android.app.moteldon.databinding.FragmentItemRoomBinding
import ulkapulka.me.android.app.moteldon.databinding.FragmentRoomsBinding
import ulkapulka.me.android.app.moteldon.storage.data.EnterType
import ulkapulka.me.android.app.moteldon.storage.data.GuestEnter
import ulkapulka.me.android.app.moteldon.storage.data.Room
import ulkapulka.me.android.app.moteldon.utils.Utils
import java.time.format.DateTimeFormatter

class RoomDiffUtil(
    private val oldList: List<Room>,
    private val newList: List<Room>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return false
    }
}

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>(), View.OnClickListener {

    var data: MutableList<Room> = mutableListOf()
        set(newValue) {
            val personDiffUtilResult = DiffUtil.calculateDiff(RoomDiffUtil(field, newValue))
            field = newValue
            personDiffUtilResult.dispatchUpdatesTo(this@RoomAdapter)
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentItemRoomBinding.inflate(inflater, parent, false)

        return RoomViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        if (data.isNotEmpty()) {
            val context = MainActivity.context!!
            val roomItem = data[position]
            val guests = MainActivity.dataStorage.getGuestsByRoom(roomItem)

            with(holder.binding) {
                holder.itemView.tag = room.toString()
                room.text = roomItem.number.toString()
                room.textSize = 14F
                guests.forEach {
                    createButton(
                        context, it.name.replace(" ", "\n"), nameLayout,
                        ContextCompat.getDrawable(context, R.drawable.rounded_all_corners_name),
                        ContextCompat.getColor(context, R.color.black)
                    )
                }
            }
        }
    }

    override fun onClick(view: View) {}

    @SuppressLint("SetTextI18n")
    private fun createButton(context: Context, text: String, layout: LinearLayout, background: Drawable?, color: Int) {
        val btnTag = Button(context)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        params.height = 150
        params.setMargins(10, 10, 15, 0)
        btnTag.layoutParams = params
        btnTag.background = background
        btnTag.textAlignment = View.TEXT_ALIGNMENT_CENTER
        btnTag.setTextColor(color)
        btnTag.text = text
        btnTag.textSize = 12F
        layout.addView(btnTag)
    }

    class RoomViewHolder(val binding: FragmentItemRoomBinding) : RecyclerView.ViewHolder(binding.root)
}