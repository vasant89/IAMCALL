package com.he.iamcall.retrofit


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer


fun createPartFromString(str: String): RequestBody {
    return RequestBody.create(okhttp3.MultipartBody.FORM, str)
}

fun prepareFilePart(mContext: Context, partName: String, fileUri: Uri): MultipartBody.Part {

    val filePath: String? = FilePickUtils.getPath(mContext, fileUri)

    val file = File(filePath)

    val requestFile = RequestBody.create(MediaType.parse(mContext.contentResolver.getType(fileUri)), file)

    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}

fun prepareFilePart(partName: String, fileName: String, imageByteArray: ByteArray): MultipartBody.Part {

    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageByteArray)

    return MultipartBody.Part.createFormData(partName, fileName, requestFile)
}

fun getByteArrayFromImageView(imageView: ImageView): ByteArray {
    var bitmapDrawable: BitmapDrawable? = null

    try {
        bitmapDrawable = imageView.drawable as BitmapDrawable
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val bitmap: Bitmap

    if (bitmapDrawable == null) {
        imageView.buildDrawingCache()
        bitmap = imageView.drawingCache
        imageView.buildDrawingCache(false)
    } else {
        bitmap = bitmapDrawable.bitmap
    }

//    //error/
//    var byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
//    bitmap.copyPixelsToBuffer(byteBuffer)
//    var bytes = byteBuffer.array() as ByteArray
//    //error/

    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}
fun getByteArrayFromDrawable(drawable: Drawable): ByteArray {
    val stream = ByteArrayOutputStream()
    (drawable as BitmapDrawable).bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

