package com.example.cryptotracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.data.models.Coin
import com.example.cryptotracker.data.models.percentageColor
import com.example.cryptotracker.databinding.ItemCoinBinding
import com.example.cryptotracker.utils.inMoneyFormat
import java.text.DecimalFormat

class CryptoAdapter : ListAdapter<Coin, CryptoAdapter.CoinViewHolder>(CoinDiffCallback()) {

    var onItemClickListener: ((Coin) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class CoinViewHolder(private val binding: ItemCoinBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(getItem(adapterPosition))
            }
        }

        fun bindData(coin: Coin) = with(binding) {
            tvCoin.text = coin.name
            ivIcon.setImageResource(coin.type.drawable)

            tvCoinCurrency.text = "${coin.usd.inMoneyFormat()}$"
            tvCoinIncDec.text = "${DecimalFormat("#.##").format(coin.usd24hChange)}%"
            tvCoinIncDec.setTextColor(ContextCompat.getColor(binding.root.context, coin.percentageColor()))

            tvCoinShort.text = coin.type.shortName
        }

    }
}


class CoinDiffCallback : DiffUtil.ItemCallback<Coin>() {
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }

}