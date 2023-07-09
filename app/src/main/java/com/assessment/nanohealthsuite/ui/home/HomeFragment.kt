package com.assessment.nanohealthsuite.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.nanohealthsuite.databinding.FragmentHomeBinding
import com.assessment.nanohealthsuite.service.model.Product
import com.assessment.nanohealthsuite.service.network.ApiHelper
import com.assessment.nanohealthsuite.service.network.RetrofitBuilder
import com.assessment.nanohealthsuite.ui.adapter.ProductListAdapter
import com.assessment.nanohealthsuite.ui.dialog.MessageAlertDialog
import com.assessment.nanohealthsuite.utility.Status

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel =
            ViewModelProvider(
                this,
                HomeViewModelFactory(
                    ApiHelper(RetrofitBuilder.apiService)
                )
            )[HomeViewModel::class.java]
        getProductList()

        return root
    }

    private fun getProductList() {

        viewModel.getProductList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        when (resource.data) {
                            null -> {
                                MessageAlertDialog("Alert!", resource.message.toString()).show(
                                    requireActivity().supportFragmentManager,
                                    "DialogFragment"
                                )
                            }
                            else ->
                                try {
                                    val productList =
                                        resource.data as ArrayList<Product>

                                    binding.recyclerViewProduct.layoutManager =
                                        LinearLayoutManager(activity)
                                    binding.recyclerViewProduct.adapter = ProductListAdapter(
                                        productList
                                    )
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
                            requireActivity().supportFragmentManager,
                            "DialogFragment"
                        )
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}