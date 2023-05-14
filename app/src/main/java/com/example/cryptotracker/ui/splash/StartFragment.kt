package com.example.cryptotracker.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotracker.R
import com.example.cryptotracker.base.BaseFragment
import com.example.cryptotracker.databinding.FragmentHomeBinding
import com.example.cryptotracker.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding, StartState, StartViewModel>() {

    override val viewModel: StartViewModel
        get() = ViewModelProvider(this)[StartViewModel::class.java]

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentStartBinding {
        return FragmentStartBinding.inflate(inflater, container, false)
    }

    override fun initViews() = with(binding) {
        btnStart.setOnClickListener {
            viewModel.setSplashScreenSeen()
            navigate(R.id.homeFragment)
        }

        tvHeader.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(700)
            .start()

        tvDescription.animate()
            .alpha(1f)
            .translationX(0f)
            .setDuration(800)
            .start()
    }

    override fun render(state: StartState) {

    }
}