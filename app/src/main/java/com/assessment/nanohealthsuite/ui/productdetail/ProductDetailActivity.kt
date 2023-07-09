package com.assessment.nanohealthsuite.ui.productdetail

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.assessment.nanohealthsuite.R
import com.assessment.nanohealthsuite.databinding.ActivityProductDetailBinding
import com.assessment.nanohealthsuite.service.model.Product
import com.assessment.nanohealthsuite.service.network.ApiHelper
import com.assessment.nanohealthsuite.service.network.RetrofitBuilder
import com.assessment.nanohealthsuite.ui.dialog.MessageAlertDialog
import com.assessment.nanohealthsuite.utility.Status
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {

    private var _binding: ActivityProductDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        viewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModelFactory(
                    ApiHelper(RetrofitBuilder.apiService)
                )
            )[ProductDetailViewModel::class.java]
        getProductDetail()

        binding.iconUpDown.setOnClickListener {
            if (binding.layoutRating.isVisible) {
                binding.layoutRating.visibility = View.GONE
                binding.iconUpDown.background = resources.getDrawable(R.drawable.icon_down)

            } else {

                binding.layoutRating.visibility = View.VISIBLE
                binding.iconUpDown.background = resources.getDrawable(R.drawable.icon_up)
            }
        }
        binding.imageViewBack.setOnClickListener {
            finish()
        }
    }

    private fun getProductDetail() {

        viewModel.getProductDetail().observe(this, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        when (resource.data) {
                            null -> {
                                MessageAlertDialog("Alert!", resource.message.toString()).show(
                                    supportFragmentManager,
                                    "DialogFragment"
                                )
                            }
                            else ->
                                try {
                                    val product =
                                        resource.data as Product
                                    binding.textProductName.text = product.title
                                    binding.textProuctDetail.text =
                                        product.description
                                    binding.productRatingBar.rating =
                                        product.rating!!.rate!!
                                    binding.textReviews.text =
                                        product.rating!!.count.toString() + " Reviews"
                                    binding.textRating.text = product.rating!!.rate.toString()
                                    Glide.with(this)
                                        .load(product.image)
                                        .into(binding.imageViewProduct)

                                    binding.preogressBar.visibility = View.GONE
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                } finally {
                                }
                        }
                    }
                    Status.ERROR -> {
                        binding.preogressBar.visibility = View.GONE
                        MessageAlertDialog("Alert!", resource.message.toString()).show(
                            supportFragmentManager,
                            "DialogFragment"
                        )
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }
}