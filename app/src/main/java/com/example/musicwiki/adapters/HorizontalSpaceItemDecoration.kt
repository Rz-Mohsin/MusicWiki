package com.example.musicwiki.adapters

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceItemDecoration(private val context: Context, private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = horizontalSpaceWidth
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = horizontalSpaceWidth
        } else {
            outRect.left = 0
        }
    }
}