package com.example.calorietracker.WelcomePage.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.calorietracker.R
import com.example.calorietracker.WelcomePage.Fragment.WelcomingPageFragment

class OnboardingViewPageAdapter(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    // OnboardingViewPageAdapter
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WelcomingPageFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_1),
                context.resources.getString(R.string.description_onboarding_1),
                R.drawable.welcome1
            )
            1 -> WelcomingPageFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_2),
                context.resources.getString(R.string.description_onboarding_2),
                R.drawable.welcome2
            )
            else -> WelcomingPageFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_3),
                context.resources.getString(R.string.description_onboarding_3),
                R.drawable.welcome3
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}