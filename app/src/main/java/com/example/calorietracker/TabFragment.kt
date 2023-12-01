package com.example.calorietracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabFragment : Fragment() {

    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)

        // Tambahkan fragment SignUpFragment dan LoginFragment ke dalam tabLayout
        val adapter = TabAdapter(childFragmentManager)
        adapter.addFragment(SignUpFragment(), "Sign Up")
        adapter.addFragment(LoginFragment(), "Log In")

        // Set adapter ke dalam ViewPager
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter

        // Hubungkan ViewPager dengan TabLayout
        tabLayout.setupWithViewPager(viewPager)

        return view
    }
}
