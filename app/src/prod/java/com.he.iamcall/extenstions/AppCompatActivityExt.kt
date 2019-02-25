package com.he.iamcall.extenstions


import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.app.Activity
import android.app.ActivityManager
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.ads.*
import com.he.iamcall.R
import com.he.iamcall.ViewModelFactory


/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(this.findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}


/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


fun AppCompatActivity.showBannerAd(adView: AdView) {
    adView.loadAd(AdRequest.Builder().build())
}

fun AppCompatActivity.showFullScreenAd() {
    val mInterstitialAd = InterstitialAd(this)

    // set the ad unit ID
    mInterstitialAd.adUnitId = getString(R.string.interstitial_full_screen)

    // Load ads into Interstitial Ads
    mInterstitialAd.loadAd(AdRequest.Builder().build())

    mInterstitialAd.adListener = object : AdListener() {
        override fun onAdLoaded() {
            if (mInterstitialAd.isLoaded) mInterstitialAd.show()
        }
    }
}

fun checkAppIsRunning(context: Context): Boolean {

    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val services = activityManager.getRunningTasks(Integer.MAX_VALUE)
    var isActivityFound = false

    if (services[0].topActivity.packageName.equals(context.packageName, ignoreCase = true)) {
        isActivityFound = true
    }

    return isActivityFound

}

fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
    var width = image.width
    var height = image.height

    val bitmapRatio = width.toFloat() / height.toFloat()
    if (bitmapRatio > 1) {
        width = maxSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maxSize
        width = (height * bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(image, width, height, true)
}


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelFactory: ViewModelFactory, viewModelClass: Class<T>) =
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
