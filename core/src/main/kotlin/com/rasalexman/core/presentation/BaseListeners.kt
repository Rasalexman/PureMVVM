package com.rasalexman.core.presentation

import com.google.android.material.tabs.TabLayout

interface TabSelectListener : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab) = Unit
    override fun onTabUnselected(tab: TabLayout.Tab) = Unit
}