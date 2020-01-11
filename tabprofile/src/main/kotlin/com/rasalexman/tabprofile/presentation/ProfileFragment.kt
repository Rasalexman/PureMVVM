package com.rasalexman.tabprofile.presentation

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.rasalexman.core.common.extensions.fragmentBinding
import com.rasalexman.core.presentation.BaseFragment
import com.rasalexman.tabprofile.R
import com.rasalexman.tabprofile.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_profile

    override val toolbar: Toolbar?
        get() = toolbarProfileLayout

    override val binding by fragmentBinding<FragmentProfileBinding>()

    override fun initLayout() {
        binding.viewModel = viewModel
    }
}