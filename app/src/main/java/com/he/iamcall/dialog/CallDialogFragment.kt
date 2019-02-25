package com.he.iamcall.dialog

import android.Manifest
import android.graphics.Typeface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.he.iamcall.R
import com.he.iamcall.data.Contact
import android.content.Intent
import android.net.Uri
import com.he.iamcall.extenstions.CONTACT
import com.he.iamcall.extenstions.varunTypeFace
import com.he.iamcall.permission.PermissionHelper


class CallDialogFragment : DialogFragment() {

    private var mView: View? = null
    private var contact: Contact? = null

    private var ivProfile: ImageView? = null
    private var tvName: TextView? = null
    private var tvMobileNo: TextView? = null
    private var tvClose: TextView? = null

    private var btnCallMe: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.call_dialog_fragment, container, false)

        mView?.apply {
            ivProfile = findViewById(R.id.iv_profile)
            tvName = findViewById(R.id.tv_name)
            tvMobileNo = findViewById(R.id.tv_mobile_no)
            tvClose = findViewById(R.id.tv_close)

            btnCallMe = findViewById(R.id.btn_call_me)
        }

        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.also {
            contact = it.getSerializable(CONTACT) as Contact
        }

        tvName?.let {
            it.typeface = context?.varunTypeFace()
            it.text = contact?.name
        }

        tvMobileNo?.let {
            it.text = contact?.mobile
        }

        tvClose?.setOnClickListener {
            dismiss()
        }

        btnCallMe?.setOnClickListener {
            PermissionHelper()
                    .checkPermission(
                            activity!!,
                            Manifest.permission.CALL_PHONE,
                            "Allow phone call permission.",
                            object : PermissionHelper.PermissionInterface {
                                override fun granted(granted: Boolean) {
                                    if (granted) {
                                        callMe()
                                    }
                                }
                            })
        }
    }


    private fun callMe() {
        contact?.mobile?.let { mobile ->
            Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile.replace("\\s".toRegex(), ""))).apply {
                startActivity(this)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }
}