package com.he.iamcall.utils


import android.content.Context
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import com.he.iamcall.R
import com.he.iamcall.retrofit.FilePickUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File

class ImageHelper(val picasso: Picasso, private val context: Context) {

    fun setProfileImage(pictureUri: String, target: Target) {
        picasso.setIndicatorsEnabled(true)
        picasso.load(pictureUri)
                .resize(600, 200)
                .centerInside()
                .placeholder(R.drawable.progress_animation)
                .into(target)
    }

    fun setProfileImage(pictureUri: Uri, target: Target) {
        picasso.isLoggingEnabled = true
        picasso.load(File(FilePickUtils.getPath(context, pictureUri)))
                .resize(600, 200)
                .centerInside()
                .placeholder(R.drawable.progress_animation)
                .into(target)
    }

    fun showImage(pictureUri: Uri, drawable: ObservableField<Drawable>) {
        picasso.isLoggingEnabled = true
        picasso.load(File(FilePickUtils.getPath(context, pictureUri)))
                .resize(600, 200)
                .centerInside()
                .placeholder(R.drawable.progress_animation)
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {

                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        drawable.set(BitmapDrawable(context.resources, bitmap))
                    }

                })
    }

    fun showImage(pictureUri: String, drawable: ObservableField<Drawable>) {
        picasso.setIndicatorsEnabled(true)
        picasso.load(pictureUri)
                .resize(600, 200)
                .centerInside()
                .placeholder(R.drawable.progress_animation)
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {

                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        drawable.set(BitmapDrawable(context.resources, bitmap))
                    }

                })
    }

}