package com.he.iamcall.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.he.iamcall.R
import com.he.iamcall.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.os.Build
import android.view.View
import com.he.iamcall.extenstions.*
import com.he.iamcall.main.addeditcontact.AddEditContactActivity
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mainView: MainView

    lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar(R.id.toolbar) {
            title = getString(R.string.app_name)
        }

       // showBannerAd(adView)

        replaceFragmentInActivity(mainView, R.id.container)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                Log.e(TAG, "$query")
                obtainViewModel().showSearchResult(query!!)
                hideKeyboard(this@MainActivity)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // filter recycler view when text is changed
                Log.e(TAG, "$query")
                obtainViewModel().showSearchResult(query!!)
                return false
            }
        })

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                return true
            }
            R.id.action_add -> {
                Intent(this@MainActivity,AddEditContactActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun obtainViewModel() = obtainViewModel(viewModelFactory, MainViewModel::class.java)


    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }

    private fun whiteNotificationBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            window.statusBarColor = Color.WHITE
        }
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

    }
}
