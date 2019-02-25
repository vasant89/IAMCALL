package com.he.iamcall


import android.content.Intent
import android.os.Bundle
import com.he.iamcall.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity

class SplashActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}