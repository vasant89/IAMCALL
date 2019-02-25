package com.he.iamcall.adapters

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.he.iamcall.data.Contact
import com.he.iamcall.databinding.RowContactBinding
import com.he.iamcall.extenstions.varunTypeFace

class ContactAdapter(val action: (index: Int, contact: Contact) -> Unit) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    val contacts: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = RowContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(parent.context, binding)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(position, contacts[position])
    }


    inner class ContactViewHolder(private val ctx: Context, private val rowContactBinding: RowContactBinding) : RecyclerView.ViewHolder(rowContactBinding.root) {

        fun bind(index: Int, item: Contact) {
            rowContactBinding.apply {
                contact = item
                tvName.typeface = ctx.varunTypeFace()
                root.setOnClickListener {
                    action(index, item)
                }
                executePendingBindings()
            }
        }
    }

    fun replaceContacts(contacts: List<Contact>) {
        this.contacts.clear()
        this.contacts.addAll(contacts)
        this.notifyDataSetChanged()
    }

    companion object {
        private val TAG = ContactAdapter::class.java.simpleName
    }
}