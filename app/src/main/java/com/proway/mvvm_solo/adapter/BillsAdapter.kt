package com.proway.mvvm_solo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.proway.mvvm_solo.R
import com.proway.mvvm_solo.model.Bill

class BillsAdapter(val onItemClick: (Bill) -> Unit): RecyclerView.Adapter<BillViewHolder>() {

    private var listOfBills: MutableList<Bill> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return BillViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        listOfBills[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { onItemClick(this) }
        }
    }

    override fun getItemCount(): Int = listOfBills.size

    fun refresh(newList: List<Bill>) {
        listOfBills = arrayListOf()
        listOfBills.addAll(newList)
        notifyDataSetChanged()
    }
}

class BillViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(bill: Bill) {
        setData(bill.uid, R.id.uidTextView)
        setData(bill.name, R.id.nameTextView)
        setData(bill.price.toString(), R.id.priceTextView)
    }

    private fun setData(value: String?, @IdRes componentId: Int) {
        itemView.findViewById<TextView>(componentId).apply {
            text = value
        }
    }
}