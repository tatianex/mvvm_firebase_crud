package com.proway.mvvm_solo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.proway.mvvm_solo.R
import com.proway.mvvm_solo.model.Bill

class AccountsAdapter: RecyclerView.Adapter<AccountViewHolder>() {

    private var listOfBills: MutableList<Bill> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        listOfBills[position].apply {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = listOfBills.size

    fun refresh(newList: List<Bill>) {
        listOfBills = arrayListOf()
        listOfBills.addAll(newList)
        notifyDataSetChanged()
    }
}

class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(account: Bill) {
        setData(account.id, R.id.uidTextView)
        setData(account.name, R.id.nameTextView)
        setData(account.price.toString(), R.id.priceTextView)
    }

    private fun setData(value: String?, @IdRes componentId: Int) {
        itemView.findViewById<TextView>(componentId).apply {
            text = value
        }
    }
}