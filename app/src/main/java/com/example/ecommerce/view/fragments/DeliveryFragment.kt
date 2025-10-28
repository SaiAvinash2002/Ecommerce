package com.example.ecommerce.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentDeliveryBinding
import com.example.ecommerce.model.local.AddressEntity
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.model.remote.ApiService
import com.example.ecommerce.model.remote.dto.Addresse
import com.example.ecommerce.view.AddressAdapter
import com.example.ecommerce.viewmodel.DeliveryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeliveryFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var viewModel: DeliveryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setupRecyclerView()
        observeData()
        setupFab()
        handleNavigation()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[DeliveryViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding.rvContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeData() {
        viewModel.localAddresses.observe(viewLifecycleOwner) { addresses ->
            binding.rvContainer.adapter = AddressAdapter(
                addresses.map {
                    Addresse(it.address, it.address_id.toString(), it.title)
                }
            )
        }
    }

//    Action for floating action button
    private fun setupFab() {
        binding.fcbAddAddress.setOnClickListener {
            showAddAddressDialog()
        }
    }

    private fun showAddAddressDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.address_alert_dialogue, null)
        val etAddress = view.findViewById<EditText>(R.id.etAlertDialoge)
        builder.setView(view)
        builder.setTitle("Add New Address")
        builder.setPositiveButton("Add") { dialog, _ ->
            val address = etAddress.text.toString()
            if (address.isNotEmpty()) {
                viewModel.addAddress(address)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }.show()
    }

    private fun handleNavigation() {
        binding.btnNext.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.vpContainer)
            viewPager.currentItem = viewPager.currentItem + 1
        }
    }
}
