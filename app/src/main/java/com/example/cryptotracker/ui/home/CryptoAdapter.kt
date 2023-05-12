package com.example.cryptotracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.cryptotracker.base.BaseListAdapter
import com.example.cryptotracker.data.models.Coin
import com.example.cryptotracker.databinding.ItemCoinBinding

class CryptoAdapter : BaseListAdapter<Coin, ItemCoinBinding>(CoinDiffCallback()) {
    override fun viewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Coin, ItemCoinBinding> {
        return CoinViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class CoinViewHolder(val binding: ItemCoinBinding) : ViewHolder<Coin, ItemCoinBinding>(binding) {
        override fun bindData(coin: Coin) = with(binding) {
            tvCoin.text = coin.name
            ivIcon.setImageResource(coin.type.drawable)
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