package com.he.iamcall.adapters

import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.databinding.ViewDataBinding
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.he.iamcall.customviews.AutofitRecyclerView


abstract class MyRecyclerViewAdapter<T> : RecyclerView.Adapter<MyRecyclerViewHolder<T>>() {

    var list = ObservableArrayList<T>()

    fun addItems(list: ArrayList<T>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    fun addItem(item: T) {
        this.list.add(item)
        this.notifyDataSetChanged()
    }

    fun replaceData(list: ArrayList<T>) {
        this.list.clear()
        this.list.addAll(list)
        this.notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        this.list.removeAt(position)
        this.notifyDataSetChanged()
    }

    fun removeItem(item: T) {
        this.list.remove(item)
        this.notifyDataSetChanged()
    }

    open fun showInOrders(list: ArrayList<T>) {

    }

    open fun reverseAnimation() {

    }

    open fun cancelAnimation(progress: Float) {

    }

    open fun removeCurrentProduct() {

    }

    open fun playAnimation() {

    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: MyRecyclerViewHolder<T>, position: Int) {
        (holder as Binder<T>).bind(position, list[position])
    }


    companion object {

        @BindingAdapter("myRecyclerViewAdapter")
        @JvmStatic
        fun setMyRecyclerViewAdapter(recyclerView: RecyclerView, myRecyclerViewAdapter: MyRecyclerViewAdapter<*>) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                itemAnimator = DefaultItemAnimator()
                adapter = myRecyclerViewAdapter
            }
        }

        @BindingAdapter("myAutoFitRecyclerViewAdapter")
        @JvmStatic
        fun setMyRecyclerViewAdapter(recyclerView: AutofitRecyclerView, myRecyclerViewAdapter: MyRecyclerViewAdapter<*>) {
            recyclerView.apply {
                itemAnimator = DefaultItemAnimator()
                setHasFixedSize(true)
                adapter = myRecyclerViewAdapter
            }
        }
    }
}

interface Binder<T> {
    fun bind(index: Int, item: T)
}

abstract class MyRecyclerViewHolder<T>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root), Binder<T>