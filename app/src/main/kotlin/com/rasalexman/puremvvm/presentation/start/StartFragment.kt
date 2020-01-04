package com.rasalexman.puremvvm.presentation.start

import androidx.fragment.app.viewModels
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.puremvvm.R

class StartFragment : BaseFragment<StartViewModel>() {

    override val viewModel: StartViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_start
}