package com.example.food01android.adapter.Favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class FavoriteAdapter(private val fragments : ArrayList<Fragment>, val parent : Fragment) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[0]
            else -> fragments[1]
        }
    }

}
