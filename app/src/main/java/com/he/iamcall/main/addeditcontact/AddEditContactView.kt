package com.he.iamcall.main.addeditcontact

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.he.iamcall.R
import com.he.iamcall.adapters.ContactAdapter
import com.he.iamcall.data.Contact
import com.he.iamcall.databinding.AddEditContactViewBinding
import com.he.iamcall.databinding.ContactListViewBinding
import com.he.iamcall.di.scope.ActivityScoped
import com.he.iamcall.extenstions.showCallDialog
import com.he.iamcall.extenstions.showSnackBarLong
import com.he.iamcall.extenstions.varunTypeFace
import com.he.iamcall.main.MainActivity
import com.he.iamcall.main.MainView.Companion.KEY_ALPHABET
import com.he.iamcall.main.contactlist.ContactListActivity
import com.he.iamcall.main.contactlist.ContactListView.Companion.KEY_CONTACT
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class AddEditContactView
@Inject
constructor() : DaggerFragment() {

    lateinit var mBinding: AddEditContactViewBinding

    @Inject
    lateinit var kProgressHUD: KProgressHUD

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = AddEditContactViewBinding.inflate(inflater, container, false).apply {
            this.viewModel = (activity as AddEditContactActivity).obtainViewModel()
        }

        context?.apply {
            mBinding.tvName.typeface = varunTypeFace()
        }

        setHasOptionsMenu(true)
        return mBinding.root
    }

    private var menuItemDelete: MenuItem? = null
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_edit_contact, menu)
        mBinding.viewModel?.apply {

            contact.get()?.fId.let {
                if(!it.isNullOrEmpty()){
                   val menuItemDelete = menu.findItem(R.id.action_delete)
                    menuItemDelete.isVisible = true
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_done -> {
                mBinding.viewModel?.let {
                    it.addUpdateContact()
                }
            }
            R.id.action_delete->{
                mBinding.viewModel?.let {
                    it.deleteContact()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.viewModel?.apply {



            arguments?.apply {

                val contact: Contact? = getSerializable(KEY_CONTACT) as Contact
                start(contact)

            }

            snackbarMessage.observe(this@AddEditContactView, Observer {
                Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
            })

            isDataLoading.observe(this@AddEditContactView, Observer {
                when (it) {
                    true -> {
                        kProgressHUD.show()
                    }
                    false -> {
                        kProgressHUD.dismiss()
                    }
                }
            })

            addUpdateContactEvent.observe(this@AddEditContactView, Observer {
                Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
                Intent(context, MainActivity::class.java).run {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(this)
                }
            })



        }

    }
}