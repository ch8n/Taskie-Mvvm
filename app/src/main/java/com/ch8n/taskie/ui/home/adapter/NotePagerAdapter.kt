package com.ch8n.taskie.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ch8n.taskie.ui.notes.NotesFragment
import com.ch8n.taskie.ui.task.TaskFragment


class NotePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragments = listOf<Fragment>(TaskFragment(), NotesFragment())
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}