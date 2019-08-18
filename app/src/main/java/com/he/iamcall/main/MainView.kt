package com.he.iamcall.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.he.iamcall.adapters.AlphabetAdapter
import com.he.iamcall.adapters.ContactAdapter
import com.he.iamcall.data.Contact
import com.he.iamcall.databinding.MainViewBinding
import com.he.iamcall.di.scope.ActivityScoped
import com.he.iamcall.extenstions.showCallDialog
import com.he.iamcall.extenstions.showSnackBarLong
import com.he.iamcall.main.addeditcontact.AddEditContactActivity
import com.he.iamcall.main.contactlist.ContactListActivity
import com.he.iamcall.main.contactlist.ContactListView.Companion.KEY_CONTACT
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.android.support.DaggerFragment
import javax.inject.Inject


@ActivityScoped
class MainView
@Inject
constructor() : DaggerFragment() {

    lateinit var mBinding: MainViewBinding

    @Inject
    lateinit var kProgressHUD: KProgressHUD

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = MainViewBinding.inflate(inflater, container, false).apply {
            this.viewModel = (activity as MainActivity).obtainViewModel()
        }

        mBinding.rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = ContactAdapter({ _, contact ->
                showCallDialog(contact)
            }, { _, contact ->
                Intent(context, AddEditContactActivity::class.java).run {
                    putExtra(KEY_CONTACT, contact)
                    startActivity(this)
                }
            })
        }

        mBinding.recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = AlphabetAdapter { _, alphabet ->
                Intent(activity, ContactListActivity::class.java).apply {
                    putExtra(KEY_ALPHABET, alphabet)
                    startActivity(this)
                }
            }
        }
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.viewModel?.apply {
            start()

            snackbarMessage.observe(this@MainView, Observer {
                view?.showSnackBarLong("$it")
            })

            isDataLoading.observe(this@MainView, Observer {
                when (it) {
                    true -> {
                        kProgressHUD.show()
                    }
                    false -> {
                        kProgressHUD.dismiss()
                    }
                }
            })

            repository.getContacts().observe(this@MainView, Observer { list ->
                list?.let {
                    isDataLoading.value = list.isEmpty()
                    (mBinding.recyclerView.adapter as AlphabetAdapter).apply {

                        contacts.clear()
                        contacts.addAll(list)

                        replaceAlphabets(list.map {
                            it.alpha ?: ""
                        }.distinct().sortedWith(compareBy { it }).filter { it.isNotEmpty() } as ArrayList<String>)
                    }
                }
            })

            searchResultEvent.observe(this@MainView, Observer {
                (mBinding.rvSearch.adapter as ContactAdapter).apply {
                    replaceContacts(it!!)
                }
            })
        }
    }


    companion object {
        private val TAG = MainView::class.java.simpleName

        const val KEY_ALPHABET = "alphabet"
    }
}