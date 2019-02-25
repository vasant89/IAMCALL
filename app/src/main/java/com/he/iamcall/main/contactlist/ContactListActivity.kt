package com.he.iamcall.main.contactlist

import android.os.Bundle
import android.view.MenuItem
import com.he.iamcall.R
import com.he.iamcall.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import com.he.iamcall.extenstions.*
import kotlinx.android.synthetic.main.content_main.*


class ContactListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var contactListView: ContactListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        showBannerAd(adView)
        showFullScreenAd()

        contactListView.arguments = intent.extras

        replaceFragmentInActivity(contactListView, R.id.container)
    }


    override fun onResume() {
        super.onResume()
        adView?.resume()
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun obtainViewModel() = obtainViewModel(viewModelFactory, ContactListViewModel::class.java)
}
