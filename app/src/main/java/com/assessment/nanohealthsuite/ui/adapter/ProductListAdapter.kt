package com.assessment.nanohealthsuite.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.nanohealthsuite.databinding.PrototypeProductItemRowBinding
import com.assessment.nanohealthsuite.service.model.Product
import com.assessment.nanohealthsuite.ui.productdetail.ProductDetailActivity
import com.bumptech.glide.Glide

class ProductListAdapter(private var datList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductListAdapter.CustomerViewHolder>() {
    lateinit var binding: PrototypeProductItemRowBinding

    private lateinit var mContext: Context

    inner class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        binding = PrototypeProductItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val v = binding.root
        val sch = CustomerViewHolder(v)
        mContext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return datList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        try {

            binding.textProductName.text = datList[position].title
            binding.textPriceTag.text = datList[position].price.toString() + " AED"
            binding.productRatingBar.rating = datList[position].rating!!.rate!!
            binding.textItemDetail.text = datList[position].description

            binding.layoutParent.setOnClickListener()
            {
                moveToDetail(datList[position].id.toString())
            }

            Glide.with(mContext)
                .load(datList[position].image)
                .into(binding.imageViewProduct)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun moveToDetail(id: String) {
        val intent = Intent(mContext, ProductDetailActivity::class.java)
        intent.putExtra("product_id", id)
        mContext.startActivity(intent)

    }
}