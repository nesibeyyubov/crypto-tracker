package com.example.cryptotracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotracker.R
import com.example.cryptotracker.base.BaseFragment
import com.example.cryptotracker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeState, HomeViewModel>() {

    override val viewModel: HomeViewModel
        get() = ViewModelProvider(this)[HomeViewModel::class.java]

    private val cryptoAdapter by lazy { CryptoAdapter() }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCoins()
    }

    override fun initViews() = with(binding) {
        rvCoins.adapter = cryptoAdapter
        ivProfile.setOnClickListener { navigate(R.id.minMaxFragment) }
    }

    override fun render(state: HomeState) {
        if (state.loading) {
            return
        }
        if (state.coins.isNullOrEmpty().not()) {
            cryptoAdapter.submitList(state.coins)
        }
    }
}