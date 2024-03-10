package com.example.vkproductapp.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vkproductapp.R
import com.example.vkproductapp.data.model.Product
import com.example.vkproductapp.presentation.common.Result
import com.example.vkproductapp.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : Fragment() {
    private val productsViewModel by viewModel<ProductsViewModel>()
    private var recyclerView: RecyclerView? = null
    private val productRecyclerAdapter = ProductRecyclerAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setUpRecyclerView()
        observeOnProducts()
        productsViewModel.getProducts()
    }

    private fun initViews(view: View){
        recyclerView = view.findViewById(R.id.productsRecyclerView)
    }

    private fun setUpRecyclerView(){
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.adapter = productRecyclerAdapter
        productRecyclerAdapter.setOnItemClickListener { product: Product ->
            val bundle = Bundle()
            bundle.putSerializable("product", product)
            findNavController().navigate(R.id.action_productsFragment_to_productDetailFragment, bundle)
        }
    }
    private fun observeOnProducts(){
        productsViewModel.productsLiveData.observe(viewLifecycleOwner) {responseResult->
            when(responseResult){
                is Result.Loading ->{

                }

                is Result.NoInternetConnection ->{

                }

                is Result.Success ->{
                    responseResult.data?.let {data->
                        productRecyclerAdapter.products = data.products
                        productRecyclerAdapter.notifyDataSetChanged()
                    }
                }

                is Result.Error ->{

                }
            }
        }
    }
}