package com.github.ahmadriza.stockbit.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.github.ahmadriza.stockbit.R
import com.github.ahmadriza.stockbit.databinding.ItemCryptoBinding
import com.github.ahmadriza.stockbit.models.CryptoEntity
import com.github.ahmadriza.stockbit.utils.common.DataBoundListAdapter
import com.github.ahmadriza.stockbit.utils.formatCurrency
import com.github.ahmadriza.stockbit.utils.getBindingOf
import com.github.ahmadriza.stockbit.utils.getCompatColor

class CryptoAdapter: DataBoundListAdapter<CryptoEntity, ItemCryptoBinding>(object : DiffUtil.ItemCallback<CryptoEntity>() {

    override fun areItemsTheSame(oldItem: CryptoEntity, newItem: CryptoEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CryptoEntity, newItem: CryptoEntity): Boolean {
        return oldItem == newItem
    }

}) {

    override fun createBinding(parent: ViewGroup): ItemCryptoBinding {
       return parent.getBindingOf(R.layout.item_crypto)
    }

    override fun bind(binding: ItemCryptoBinding, item: CryptoEntity) {
        binding.tvCode.text = item.name
        binding.tvName.text = item.fullName
        binding.tvPrice.text = item.price.toString().formatCurrency()

        val percent = String.format("%.2f", item.changePercent)

        binding.tvProgress.text = "${if(item.change>0) "+" else ""}${item.change.toString().formatCurrency()}($percent%)"

        if(item.change>0){
            binding.tvProgress.setTextColor(binding.root.context.getCompatColor(R.color.colorAccent))
        }else if(item.change<0){
            binding.tvProgress.setTextColor(binding.root.context.getCompatColor(R.color.red))
        }else{
            binding.tvProgress.setTextColor(binding.root.context.getCompatColor(R.color.textSecondary))
        }
    }


}