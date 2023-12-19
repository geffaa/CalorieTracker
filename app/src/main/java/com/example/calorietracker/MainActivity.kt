package com.example.calorietracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.calorietracker.HistoryFragment
import com.example.calorietracker.HomeFragment
import com.example.calorietracker.ProfileFragment
import com.example.calorietracker.R
import com.example.calorietracker.databinding.ActivityMainBinding
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomBar = findViewById<AnimatedBottomBar>(R.id.bottom_bar)
        bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                val fragment = when (newTab.id) {
                    R.id.navigation_home -> HomeFragment()
                    R.id.navigation_history -> HistoryFragment()
                    R.id.navigation_profile -> ProfileFragment()
                    else -> HomeFragment()
                }
                replaceFragment(fragment)
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                // Handle reselected tab if needed
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
