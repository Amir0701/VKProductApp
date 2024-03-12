package com.example.vkproductapp.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vkproductapp.R
import com.example.vkproductapp.data.model.Product
import com.example.vkproductapp.presentation.common.Result
import com.example.vkproductapp.presentation.viewmodel.ProductsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : Fragment(), MenuProvider {
    private val productsViewModel by viewModel<ProductsViewModel>()
    private var recyclerView: RecyclerView? = null
    private val productRecyclerAdapter = ProductRecyclerAdapter()
    private var progressBar: ProgressBar? = null
    private var searchJob: Job? = null
    private var isLoading = false

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
    }

    override fun onStart() {
        super.onStart()
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.title = "Products"
        requireActivity().addMenuProvider(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().removeMenuProvider(this)
    }

    override fun onDetach() {
        super.onDetach()
        searchJob?.cancel()
    }
    private fun initViews(view: View){
        recyclerView = view.findViewById(R.id.productsRecyclerView)
        progressBar = view.findViewById(R.id.productProgressBar)
    }

    private fun setUpRecyclerView(){
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        recyclerView?.adapter = productRecyclerAdapter
        productRecyclerAdapter.setOnItemClickListener { product: Product ->
            val bundle = Bundle()
            bundle.putSerializable("product", product)
            findNavController().navigate(R.id.action_productsFragment_to_productDetailFragment, bundle)
        }


        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItems = gridLayoutManager.itemCount
                val visibleItems = gridLayoutManager.childCount
                val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()
                val totalItemsWhenLoadData = totalItems * 75 / 100
                if(!isLoading && (searchJob?.isCompleted == false || searchJob == null)){
                   if((visibleItems + firstVisibleItemPosition) >= totalItemsWhenLoadData){
                       isLoading = true
                       productsViewModel.getProducts()
                   }
                }
            }
        })
    }
    private fun observeOnProducts(){
        productsViewModel.productsLiveData.observe(viewLifecycleOwner) {responseResult->
            when(responseResult){
                is Result.Loading ->{
                    progressBar?.visibility = View.VISIBLE
                }

                is Result.NoInternetConnection ->{
                    isLoading = false
                    progressBar?.visibility = View.GONE
                    Snackbar.make(requireView(), "No Internet connection", Snackbar.LENGTH_LONG).show()
                }

                is Result.Success ->{
                    progressBar?.visibility = View.GONE
                    responseResult.data?.let {data->
                        productRecyclerAdapter.setData(data)
//                        if(searchJob?.isCompleted == true){
//                            productRecyclerAdapter.setData(data.products)
//                        }else{
//                            productRecyclerAdapter.appendData(data.products)
//                        }
                    }
                    isLoading = false
                }

                is Result.Error ->{
                    isLoading = false
                    progressBar?.visibility = View.GONE
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage(responseResult.message)
                        .create()
                        .show()
                }
            }
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.search){
            val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView?

            searchView?.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchJob?.cancel()
                    p0?.let {query ->
                        if(query.isNotEmpty()){
                            searchJob = productsViewModel.searchProduct(query)
                            return true
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }

        return false
    }
}