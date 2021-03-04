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
import com.caminoneocatecumenal.comuapp.databinding.SelectDufourCardBinding
import com.caminoneocatecumenal.comuapp.models.DufourData

/**
 * Created by Mario Q
 */
class SelectFromSearchDufourAdapter(private val mOnClick: View.OnClickListener) :
    ListAdapter<DufourData, SelectFromSearchDufourAdapter.ViewHolder?>(
        diffCallback
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.select_dufour_card,
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
        holder.binding.tvType.setVisibility(View.GONE)
        holder.binding.clCardLayout.setTag(data)
        holder.binding.clCardLayout.setOnClickListener(mOnClick)
        holder.binding.tvName.setText(data.name)
    }



    inner class ViewHolder(var binding: SelectDufourCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<DufourData> =
            object : DiffUtil.ItemCallback<DufourData>() {
                override fun areItemsTheSame(
                    oldItem: DufourData,
                    newItem: DufourData
                ): Boolean {
                    return oldItem.equals(newItem)
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: DufourData,
                    newItem: DufourData
                ): Boolean {
                    return oldItem.name.equals(newItem.name)
                }
            }
    }

}