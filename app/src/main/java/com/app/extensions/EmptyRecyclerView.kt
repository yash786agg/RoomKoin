package com.app.extensions

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyRecyclerView @JvmOverloads constructor(context: Context,
                                                  attrs: AttributeSet? = null,
                                                  defStyle: Int = 0)
    : RecyclerView(context, attrs, defStyle)
{

    private var emptyView: View? = null

    private val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    fun checkIfEmpty() {
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter?.itemCount == 0
            emptyView?.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
        checkIfEmpty()
    }
}