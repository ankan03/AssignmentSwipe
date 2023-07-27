package com.example.assignment_ankan.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_ankan.R
import com.example.assignment_ankan.model.GetProductResponse
import com.example.assignment_ankan.ui.recyclerView.RecyclerViewAdapter
import com.example.assignment_ankan.utill.checkNetworkState
import com.example.assignment_ankan.viewModel.GetProductViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class GetProductFragment : Fragment() {
    private lateinit var adapter:RecyclerViewAdapter
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_get_product, container, false)
//        val buttonAddProduct:Button = view.findViewById<Button>(R.id.button_add_product)
        val editTextSearch:EditText = view.findViewById<EditText>(R.id.edit_text_search)
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        progressBar = view.findViewById(R.id.progress_bar_get_product)

        progressBar.visibility = View.VISIBLE
        initViewModel(view);
        initViewModel();

        floatingActionButton.setOnClickListener{
            val addProductFragment = AddProductFragment.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, addProductFragment)
                .addToBackStack(null)
                .commit()
        }

        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filterByName(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun initViewModel(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
    }

    private fun initViewModel(){
        if(checkNetworkState(requireContext())){
            val viewModel = ViewModelProvider(this).get(GetProductViewModel::class.java)
            viewModel.getProductObserver().observe(this,Observer<ArrayList<GetProductResponse>>{
                if(it!=null){
                    progressBar.visibility = View.GONE
                    adapter.setUpdateData(it)
                }else{
                    Toast.makeText(activity,"Error in loading data",Toast.LENGTH_SHORT).show()
                }
            })
            viewModel.makeApiCall()
        }else{
            Toast.makeText(activity,"Check Internet Connectivity", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = GetProductFragment()
    }
}