package com.example.calorietracker.GetStarted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calorietracker.R

import android.content.Intent
import android.widget.Button
import com.example.calorietracker.HomePage.HomePageActivity


class GS4_CongratsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gs4_congrats)

        val btnFinish: Button = findViewById(R.id.gs4_btn_finish)

        btnFinish.setOnClickListener {
            // Intent untuk berpindah ke HomepageActivity
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish() // Optional, menutup aktivitas saat ini agar tidak dapat kembali ke GS4_CongratsActivity dari HomepageActivity
        }
    }
}
