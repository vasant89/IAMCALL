package com.he.iamcall.main.addeditcontact

import android.os.Bundle
import android.view.MenuItem
import com.he.iamcall.R
import com.he.iamcall.ViewModelFactory
import com.he.iamcall.data.Contact
import com.he.iamcall.extenstions.*
import com.he.iamcall.main.contactlist.ContactListView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_add_edit_contact.*
import javax.inject.Inject

class AddEditContactActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var addEditContactView: AddEditContactView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)
        val contact: Contact? = intent.extras?.getSerializable(ContactListView.KEY_CONTACT) as Contact?

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            title = if (contact == null) {
                getString(R.string.add_contact)
            } else {
                getString(R.string.edit_contact)
            }
        }

//        showBannerAd(adView)
//        showFullScreenAd()

        addEditContactView.arguments = intent.extras

        replaceFragmentInActivity(addEditContactView, R.id.container)
    }


//    override fun onResume() {
//        super.onResume()
//        adView?.resume()
//    }
//
//    override fun onPause() {
//        adView?.pause()
//        super.onPause()
//    }
//
//    override fun onDestroy() {
//        adView?.destroy()
//        super.onDestroy()
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun obtainViewModel() = obtainViewModel(viewModelFactory, AddEditContactViewModel::class.java)
}
