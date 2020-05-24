package com.example.weather_app

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter (private val view: View, private val venueList: List<Venue>, fm : FragmentManager) : FragmentPagerAdapter(fm,  FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FragmentOne(view, venueList)
            1 -> FragmentTwo(view, venueList)
            else -> FragmentThree(view, venueList)
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "A-Z"
            1 -> "Temperature"
            else -> "Last Updated"
        }
    }

}