package com.ch8n.taskie.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentHomeBinding
import com.ch8n.taskie.ui.home.adapter.NotePagerAdapter
import com.ch8n.taskie.ui.home.adapter.ZoomOutPageTransformer


class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private var notePagerAdapter: NotePagerAdapter? = null

    override fun setup() = with(binding) {
        pagerNotes.adapter = NotePagerAdapter(this@HomeFragment.requireActivity())
            .also { notePagerAdapter = it }
        pagerNotes.setPageTransformer(ZoomOutPageTransformer())
        applyBackPressBehaviour()
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