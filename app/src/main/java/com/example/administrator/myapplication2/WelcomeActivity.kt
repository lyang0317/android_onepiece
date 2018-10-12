package com.example.administrator.myapplication2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler

class WelcomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_main)
        Handler().postDelayed({
            val intent = Intent(this@WelcomeActivity, HomeActivity::class.java)
            this@WelcomeActivity.startActivity(intent)
            this@WelcomeActivity.finish()
        }, 3000)
    }

}
