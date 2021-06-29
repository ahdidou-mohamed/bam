package com.bam.repos.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bam.repos.ui.repos.ReposAdapter

/**
 * Sets an adapter to a RecyclerView
 * @param view the RecyclerView on which to set the adapter
 * @param adapter the adapter to set to the RecyclerView
 */
@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: ReposAdapter) {
    view.adapter = adapter
}

/**
 * Sets a LayoutManager to a RecyclerView
 * @param view the RecyclerView on which to set the LayoutManager
 * @param layoutManager the LayoutManager to set to the RecyclerView
 */
@BindingAdapter("layoutManager")
fun setLayoutManager(view: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
    view.layoutManager = layoutManager
}

/**
 * Adds a DividerItemDecoration to a RecyclerView
 * @param view the RecyclerView on which to set the DividerItemDecoration
 * @param dividerItemDecoration the DividerItemDecoration to set to the RecyclerView
 */
@BindingAdapter("dividerItemDecoration")
fun setDividerItemDecoration(view: RecyclerView, dividerItemDecoration: DividerItemDecoration) {
    view.addItemDecoration(dividerItemDecoration)
}