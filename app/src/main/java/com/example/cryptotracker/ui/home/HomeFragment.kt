package com.example.cryptotracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotracker.R
import com.example.cryptotracker.base.BaseFragment
import com.example.cryptotracker.databinding.FragmentHomeBinding
import com.example.cryptotracker.utils.Constants.KEY_COIN
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
        cryptoAdapter.onItemClickListener = { coin ->
            navigate(R.id.minMaxFragment, bundleOf(KEY_COIN to coin))
        }


        etSearch.doAfterTextChanged { viewModel.search(it.toString().trim()) }

        Unit
    }

    override fun render(state: HomeState) = with(binding) {
        shimmer.isVisible = state.loading
        rvCoins.isVisible = !state.loading

        if (state.coins.isNullOrEmpty().not()) {
            cryptoAdapter.submitList(state.coins?.filter { it.name.contains(state.query, ignoreCase = true) })
        }
    }
}