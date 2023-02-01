package com.example.houseapp.ui.adapters

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Класс устанавливает индивидуальные правила отрисовки элементов RecycleView
 */
class SpaceItemDecorator : RecyclerView.ItemDecoration() {

    /**
     * Устанавливает отдельный отступ для первого элемента в списке
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val margin = 10
        val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin.toFloat(),
            view.resources.displayMetrics
        )

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space.toInt()
            outRect.bottom = 0
        }
    }
}