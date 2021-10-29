package com.dicoding.submission1_fundamentalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(mainLooper).postDelayed({
            val splash = Intent(this, MainActivity::class.java)
            startActivity(splash)
            finish()
        }, DELAY.toLong())
    }

    companion object {
        private const val DELAY = 2000
    }
}