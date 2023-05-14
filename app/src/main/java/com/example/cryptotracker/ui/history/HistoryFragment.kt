package com.example.cryptotracker.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotracker.R
import com.example.cryptotracker.base.BaseFragment
import com.example.cryptotracker.data.models.Coin
import com.example.cryptotracker.databinding.FragmentHistoryBinding
import com.example.cryptotracker.databinding.FragmentHomeBinding
import com.example.cryptotracker.utils.Constants.KEY_COIN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryState, HistoryViewModel>() {

    override val viewModel: HistoryViewModel
        get() = ViewModelProvider(this)[HistoryViewModel::class.java]

    private val cryptoAdapter by lazy { CryptoAdapter() }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(inflater, container, false)
    }

    private val params by lazy { arguments?.getSerializable(KEY_COIN) as? Coin }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        params?.type?.ordinal?.run { viewModel.getCoins(this) }

    }

    override fun initViews() = with(binding) {
        rvCoins.adapter = cryptoAdapter
        params?.let {
            tvHeader.text = "${it.name} history"
        }

        ibBack.setOnClickListener { navigateBack() }



        Unit
    }

    override fun render(state: HistoryState) = with(binding) {
        shimmer.isVisible = state.loading
        rvCoins.isVisible = !state.loading

        tvEmpty.isVisible = !state.loading && state.coins.isNullOrEmpty()

        if (!state.coins.isNullOrEmpty()) {
            cryptoAdapter.submitList(state.coins)
        }
    }
}