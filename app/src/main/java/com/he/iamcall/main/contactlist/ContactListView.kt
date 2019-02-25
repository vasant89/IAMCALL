package com.he.iamcall.main.contactlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.he.iamcall.adapters.ContactAdapter
import com.he.iamcall.databinding.ContactListViewBinding
import com.he.iamcall.di.scope.ActivityScoped
import com.he.iamcall.dialog.CallDialogFragment
import com.he.iamcall.extenstions.showCallDialog
import com.he.iamcall.extenstions.showSnackBarLong
import com.he.iamcall.main.MainView.Companion.KEY_ALPHABET
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class ContactListView
@Inject
constructor() : DaggerFragment() {

    lateinit var mBinding: ContactListViewBinding

    @Inject
    lateinit var kProgressHUD: KProgressHUD

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = ContactListViewBinding.inflate(inflater, container, false).apply {
            this.viewModel = (activity as ContactListActivity).obtainViewModel()
        }

        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = ContactAdapter { _, contact ->
                showCallDialog(contact)
            }
        }

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.apply {

            val alphabet = getString(KEY_ALPHABET) as String

            activity?.title = alphabet

            mBinding.viewModel?.apply {


                start(alphabet)

                repository.getContactsByAlpha(alphabet).observe(this@ContactListView, Observer {
                    it?.let { contacts ->
                        (mBinding.recyclerView.adapter as ContactAdapter).replaceContacts(contacts)
                    }
                })

                snackbarMessage.observe(this@ContactListView, Observer {
                    view?.showSnackBarLong("$it")
                })

                isDataLoading.observe(this@ContactListView, Observer {
                    when (it) {
                        true -> {
                            kProgressHUD.show()
                        }
                        false -> {
                            kProgressHUD.dismiss()
                        }
                    }
                })

            }
        }
    }
}