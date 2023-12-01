package com.example.calorietracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TabFragment())
                .commit()
        }
    }
}
