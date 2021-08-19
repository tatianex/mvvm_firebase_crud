package com.proway.mvvm_solo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.proway.mvvm_solo.databinding.DetailActivityBinding
import com.proway.mvvm_solo.model.Bill
import com.proway.mvvm_solo.view_model.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailActivityBinding
    private lateinit var viewModel: DetailViewModel

    private val observerBill = Observer<Bill> { bill ->
        binding.uidTextView.text = bill.uid
        binding.nameTextView.text = bill.name
        binding.priceTextView.text = bill.price.toString()
    }

    private val observerError = Observer<String?> {
        println("Sorry we couldn't find any bill!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.bill.observe(this, observerBill)
        viewModel.error.observe(this, observerError)

        val id = intent.getStringExtra("bill_id")
        id?.let { uid ->
            viewModel.GetBill(uid)
        }
    }
}