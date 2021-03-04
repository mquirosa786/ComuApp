package com.caminoneocatecumenal.comuapp.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.ItemAlertDialogAbbrevBinding
import com.caminoneocatecumenal.comuapp.databinding.SelectDufourCardBinding
import com.caminoneocatecumenal.comuapp.models.DufourData

/**
 * Created by Mario Q
 */
class AbbrevAlertDialogAdapter() :
    ListAdapter<String, AbbrevAlertDialogAdapter.ViewHolder?>(
        diffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_alert_dialog_abbrev,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val data = getItem(position)
        holder.binding.tvReading.setText(String.format("- %s",data))
    }



    inner class ViewHolder(var binding: ItemAlertDialogAbbrevBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String
                ): Boolean {
                    return oldItem.equals(newItem)
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String
                ): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }

}