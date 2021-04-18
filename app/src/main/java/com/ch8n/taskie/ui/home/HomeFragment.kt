package com.ch8n.taskie.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.ch8n.taskie.R
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentHomeBinding
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.home.adapter.NotePagerAdapter
import com.ch8n.taskie.ui.home.adapter.ZoomOutPageTransformer
import com.ch8n.taskie.ui.notes.NotesFragment
import com.ch8n.taskie.ui.task.TaskFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private var notePagerAdapter: NotePagerAdapter? = null
    private val homeViewModel by lazy { Injector.homeVM }

    override fun setup() = with(binding) {
        pagerNotes.adapter = NotePagerAdapter(this@HomeFragment.requireActivity())
            .also { notePagerAdapter = it }
        pagerNotes.setPageTransformer(ZoomOutPageTransformer())
        TabLayoutMediator(tabs, pagerNotes) { tab, position ->
            tab.text = notePagerAdapter?.getTabName(position)
        }.attach()
        applyFabClickBehaviour()
        applyBackPressBehaviour()
        applyTabChangeBehaviour()
        applyAppFirstTimeBehaviour()
    }

    private fun applyAppFirstTimeBehaviour() {
        homeViewModel.oneTimeSetup()
    }


    private fun applyTabChangeBehaviour() {
        val images = listOf<Int>(
            R.drawable.laying,
            R.drawable.meditating,
            R.drawable.plant,
            R.drawable.reading,
            R.drawable.sitting_reading,
            R.drawable.unboxing,
            R.drawable.clumsy,
            R.drawable.dancing,
            R.drawable.fly,
        )
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Picasso.get()
                    .load(images.random())
                    .into(binding.toolbarImage)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }


    private fun applyFabClickBehaviour(): Unit = with(binding) {

        btnAdd.setOnClickListener {
            val visiblePosition = pagerNotes.currentItem
            val fragment = notePagerAdapter?.getFragment(visiblePosition)
            when (fragment) {
                is NotesFragment -> fragment.openCreateNoteDialog()
                is TaskFragment -> fragment.openCreateTaskDialog()
            }
        }
    }

    private fun applyBackPressBehaviour() = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(
            /* LifecycleOwner*/this@HomeFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = pagerNotes.currentItem != 0
                    if (isEnabled) {
                        pagerNotes.currentItem = pagerNotes.currentItem - 1
                    } else {
                        requireActivity().onBackPressed()
                    }
                }
            }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        notePagerAdapter = null
    }
}