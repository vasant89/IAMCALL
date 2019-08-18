package com.he.iamcall.session

/**
 * Created by DOTh Solutions on 20-01-2018.
 */


import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.securepreferences.SecurePreferences
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(private val mContext: Context) {

    var currentProfileId: Int = 0
        get() {
            if (field >= 1) {
                return field
            }
            Log.w(TAG, "current profile id is not set!")
            return 0
        }

    private val mSecurePreferences = SecurePreferences(this.mContext)

    fun setUserId(userId: String) {
        val editor = this.mSecurePreferences.edit()
        editor.putString(Cst.KEY_ID, userId)
        editor.apply()
    }

    val userId: String
        get() = this.mSecurePreferences.getString(Cst.KEY_ID, "")

    val profileImage :String
        get() = this.mSecurePreferences.getString(Cst.KEY_USER_IMAGE, "")

    val token: String?
        get() = this.mSecurePreferences.getString("LiveCredentialsManager.Token", null)

    val isLoggedIn: Boolean
        get() = !(currentProfileId == 0 || TextUtils.isEmpty(token))

    val emailForResetPassword: String
        get() = this.mSecurePreferences.getString("LiveCredentialsManager.ResetPasswordEmail", "")

    fun storeCredentials(token: String, email: String) {
        val editor = this.mSecurePreferences.edit()
        editor.putString("LiveCredentialsManager.Token", token)
        editor.putString("LiveCredentialsManager.Email", email)
        editor.apply()
    }

    fun setData(key: String, value: String) {
        val editor = this.mSecurePreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }



    fun getData(key: String, defualtValue: String): String {
        return this.mSecurePreferences.getString(key, defualtValue)
    }

    fun getData(key: String): String {
        return this.mSecurePreferences.getString(key, "")
    }

    fun setUniqueNotificationId(id:Int){
        val editor = this.mSecurePreferences.edit()
        editor.putInt(Cst.UNI_NOTIFI, id)
        editor.apply()
    }

    fun getUniqueNotificationId():Int{
        return this.mSecurePreferences.getInt(Cst.UNI_NOTIFI, 123)
    }

    @Synchronized
    fun getUniqueId(): String {
        var uniqueID = this.mSecurePreferences.getString(Cst.KEY_UDID, "")
        if (uniqueID == "") {
            uniqueID = UUID.randomUUID().toString()
            val editor = this.mSecurePreferences.edit()
            editor.putString(Cst.KEY_UDID, uniqueID)
            editor.apply()
        }
        return uniqueID
    }


    companion object {
        private val TAG = SessionManager::class.java.simpleName
    }
}
