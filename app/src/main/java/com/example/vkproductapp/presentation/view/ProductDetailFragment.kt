package com.example.vkproductapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.vkproductapp.R

class ProductDetailFragment : Fragment() {
    private val navArgs by navArgs<ProductDetailFragmentArgs>()
    private var imageSlider: ImageSlider? = null
    private var productTitleTextView: TextView? = null
    private var productPriceTextView: TextView? = null
    private var productBrandTextView: TextView? = null
    private var productCategoryTextView: TextView? = null
    private var productDescriptionTextView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TAG", navArgs.product.title)
        initViews(view)
        setImagesToSlider()
        setProductData()
    }

    private fun initViews(view: View){
        imageSlider = view.findViewById(R.id.productImageSlider)
        productBrandTextView = view.findViewById(R.id.productBrand)
        productCategoryTextView = view.findViewById(R.id.productCategory)
        productPriceTextView = view.findViewById(R.id.productPrice)
        productTitleTextView = view.findViewById(R.id.productTitle)
        productDescriptionTextView = view.findViewById(R.id.description)
    }

    private fun setImagesToSlider(){
        val slideModels = mapProductImageToSlideModel(navArgs.product.images)
        imageSlider?.setImageList(slideModels, ScaleTypes.CENTER_CROP)
    }

    private fun mapProductImageToSlideModel(images: List<String>): List<SlideModel>{
        return images.map {image->
            SlideModel(image)
        }
    }

    private fun setProductData(){
        val product = navArgs.product
        productTitleTextView?.text = product.title
        productPriceTextView?.text = "${product.price} $"
        productCategoryTextView?.text = product.category
        productBrandTextView?.text = product.brand
        productDescriptionTextView?.text = product.description
    }
}