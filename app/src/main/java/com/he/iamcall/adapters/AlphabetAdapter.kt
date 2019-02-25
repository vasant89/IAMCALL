package com.he.iamcall.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.he.iamcall.data.Contact
import com.he.iamcall.databinding.RowAlphabetBinding

class AlphabetAdapter(val action: (index:Int,alphabet: String) -> Unit) : RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder>() {

    val alphabets: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val binding = RowAlphabetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlphabetViewHolder(binding)
    }

    override fun getItemCount() = alphabets.size

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        holder.bind(position, alphabets[position])
    }

    fun replaceAlphabets(alphabets: List<String>){
        this.alphabets.clear()
        this.alphabets.addAll(alphabets)
        this.notifyDataSetChanged()
    }
    inner class AlphabetViewHolder(private val rowAlphabetBinding: RowAlphabetBinding) : RecyclerView.ViewHolder(rowAlphabetBinding.root) {
        fun bind(index: Int, item: String) {
            rowAlphabetBinding.apply {
                alphabet = item
                root.setOnClickListener {
                    action(index,item)
                }
                executePendingBindings()
            }
        }
    }
}