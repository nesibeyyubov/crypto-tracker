package com.example.cryptotracker.ui.min_max

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptotracker.base.BaseFragment
import com.example.cryptotracker.databinding.FragmentHomeBinding
import com.example.cryptotracker.databinding.FragmentMinMaxRateBinding

import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.cryptotracker.R
import com.example.cryptotracker.data.models.Coin
import com.example.cryptotracker.data.models.percentageColor
import com.example.cryptotracker.utils.Constants.KEY_COIN
import com.example.cryptotracker.utils.enableFloatingWithKeyboard
import com.example.cryptotracker.utils.hideKeyboard
import com.example.cryptotracker.utils.inMoneyFormat
import com.google.android.material.internal.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class MinMaxFragment : BaseFragment<FragmentMinMaxRateBinding, MinMaxState, MinMaxViewmodel>() {

    override val viewModel: MinMaxViewmodel
        get() = ViewModelProvider(this)[MinMaxViewmodel::class.java]

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMinMaxRateBinding {
        return FragmentMinMaxRateBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMinMax(arguments?.getSerializable(KEY_COIN) as Coin)
    }

    override fun initViews() = with(binding) {
        ibBack.setOnClickListener { navigateBack() }

        val coin = arguments?.getSerializable(KEY_COIN) as Coin

        tvHeader.text = coin.name
        ivCoin.setImageResource(coin.type.drawable)

        verticalView.setBackgroundColor(getColor(coin.percentageColor()))
        tvRate.text = "${coin.usd.inMoneyFormat()}$"
        tvPercentage.text = "${DecimalFormat("#.##").format(coin.usd24hChange)}%"
        tvPercentage.setTextColor(getColor(coin.percentageColor()))

        btnSave.enableFloatingWithKeyboard(32)
        btnSave.setOnClickListener {
            val min = etMin.text.toString().trim()
            val max = etMax.text.toString().trim()
            if (min.isEmpty() || max.isEmpty()) {
                showToast("Fill out other field too.")
            } else if (min.toFloat() > max.toFloat()) {
                showToast("Min rate should be smaller than max rate")
            } else {
                viewModel.saveMinMax(coin, min.toFloat(), max.toFloat())
                root.hideKeyboard()
                showToast("Min max rate saved successfully!")
                etMax.clearFocus()
                etMin.clearFocus()
            }
        }

        ivHistory.setOnClickListener { navigate(R.id.historyFragment, bundleOf(KEY_COIN to coin)) }

    }

    override fun render(state: MinMaxState) = with(binding) {
        if (state.minMax != null) {
            etMin.setText(state.minMax.min.toString())
            etMax.setText(state.minMax.max.toString())
        }
    }
}