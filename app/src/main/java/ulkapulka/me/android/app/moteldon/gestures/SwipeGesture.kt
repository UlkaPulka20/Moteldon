package ulkapulka.me.android.app.moteldon.gestures

import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import ulkapulka.me.android.app.moteldon.MainActivity
import ulkapulka.me.android.app.moteldon.R


abstract class SwipeGesture : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> {
                onLeftSwipe(viewHolder)
            }
            ItemTouchHelper.RIGHT -> {
                onRightSwipe(viewHolder)
            }
        }
    }

    abstract fun onLeftSwipe(viewHolder: RecyclerView.ViewHolder)
    abstract fun onRightSwipe(viewHolder: RecyclerView.ViewHolder)

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.context!!, R.color.red))
            .addSwipeLeftActionIcon(R.drawable.baseline_delete_forever_24)
            .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.context!!, R.color.orange))
            .addSwipeRightActionIcon(R.drawable.baseline_edit_24)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}