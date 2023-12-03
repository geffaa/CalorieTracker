package com.example.calorietracker.SplashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.calorietracker.R
import com.example.calorietracker.WelcomePage.WelcomePageActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this@SplashScreenActivity, WelcomePageActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, splashTimeOut)
    }
}