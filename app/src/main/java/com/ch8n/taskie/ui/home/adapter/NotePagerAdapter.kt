package com.ch8n.taskie.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ch8n.taskie.ui.home.HomeFragment
import com.ch8n.taskie.ui.notes.NotesFragment
import com.ch8n.taskie.ui.task.TaskFragment


class NotePagerAdapter(homeFragment: HomeFragment) : FragmentStateAdapter(homeFragment) {

    private val fragments = mapOf<String, Fragment>(
        "Task" to TaskFragment(),
        "Notes" to NotesFragment()
    )

    fun getTabName(position: Int): String {
        return fragments.keys.toList().get(position)
    }

    fun getFragment(position: Int): Fragment {
        return fragments.values.toList().get(position)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = getFragment(position)
}