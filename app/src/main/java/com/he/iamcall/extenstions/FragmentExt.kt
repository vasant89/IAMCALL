package com.he.iamcall.extenstions

import android.os.Bundle
import android.support.v4.app.Fragment
import com.he.iamcall.data.Contact
import com.he.iamcall.dialog.CallDialogFragment


const val CALL_DIALOG = "callDialog"
const val CONTACT = "contact"
fun Fragment.showCallDialog(contact: Contact) {
    fragmentManager?.apply {
        val ft = beginTransaction()
        val prev = findFragmentByTag(CALL_DIALOG)

        prev?.let {
            ft.remove(it)
        }

        ft.addToBackStack(null)

        CallDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(CONTACT, contact)
            }
            show(ft, CALL_DIALOG)
        }
    }
}