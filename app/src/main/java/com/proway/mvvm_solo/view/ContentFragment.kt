package com.proway.mvvm_solo.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseUser
import com.proway.mvvm_solo.DetailActivity
import com.proway.mvvm_solo.R
import com.proway.mvvm_solo.adapter.BillsAdapter
import com.proway.mvvm_solo.model.Bill
import com.proway.mvvm_solo.utils.replaceView
import com.proway.mvvm_solo.view_model.ContentViewModel

class ContentFragment : Fragment(R.layout.content_fragment) {

    companion object {
        fun newInstance() = ContentFragment()
    }

    private lateinit var viewModel: ContentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userEmailTextView: TextView
    private val adapter = BillsAdapter() { bill ->
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra("bill_id", bill.uid)
            startActivity(this)
        }
    }

    val observerAccounts = Observer<List<Bill>> {
        adapter.refresh(it)
        swipeRefreshLayout.isRefreshing = false
    }

    val observerError = Observer<String> {
        swipeRefreshLayout.isRefreshing = false
    }

    val observerSignOut = Observer<Boolean> { isSignedIn ->
        if (!isSignedIn) {
            requireActivity().replaceView(SignInFragment.newInstance())
        }
    }

    val observerSigneduser = Observer<FirebaseUser> { user ->
        userEmailTextView.apply {
            text = user.email
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContentViewModel::class.java)

        swipeRefreshLayout = view.findViewById(R.id.swipeContainer)
        userEmailTextView = view.findViewById(R.id.userEmailTextView)
        recyclerView = view.findViewById<RecyclerView>(R.id.billsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.error.observe(viewLifecycleOwner, observerError)
        viewModel.bills.observe(viewLifecycleOwner, observerAccounts)
        viewModel.isSignedIn.observe(viewLifecycleOwner, observerSignOut)
        viewModel.user.observe(viewLifecycleOwner, observerSigneduser)

        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            val inputName = view.findViewById<EditText>(R.id.inputNameEditText)
            val inputPrice = view.findViewById<EditText>(R.id.inputPriceEditText)
            if (inputName.text.toString().isNotEmpty() && inputPrice.text.toString().isNotEmpty()) {
                viewModel.addBill(
                    inputName.text.toString(),
                    inputPrice.text.toString().toDoubleOrNull()
                )
            }
        }
        view.findViewById<View>(R.id.signOutButton).setOnClickListener {
            viewModel.signOut()
        }

        swipeRefreshLayout.setOnRefreshListener {
            loadBillsData()
        }

        loadBillsData()
        loadUserData()
    }

    fun loadBillsData() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.fetchBills()
    }

    fun loadUserData() {
        viewModel.fetchCurrentUser()
    }
}