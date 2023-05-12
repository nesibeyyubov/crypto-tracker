package com.example.cryptotracker.ui.min_max

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.cryptotracker.base.BaseFragment
import com.example.cryptotracker.databinding.FragmentHomeBinding
import com.example.cryptotracker.databinding.FragmentMinMaxRateBinding

import android.view.WindowManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MinMaxFragment : BaseFragment<FragmentMinMaxRateBinding, MinMaxState, MinMaxViewmodel>() {

    override val viewModel: MinMaxViewmodel?
        get() = null

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMinMaxRateBinding {
        return FragmentMinMaxRateBinding.inflate(inflater, container, false)
    }

    override fun initViews() = with(binding) {
        ibBack.setOnClickListener { navigateBack() }
    }

    override fun render(state: MinMaxState) {

    }
}