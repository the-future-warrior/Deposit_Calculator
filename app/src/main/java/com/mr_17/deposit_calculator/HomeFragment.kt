package com.mr_17.deposit_calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mr_17.deposit_calculator.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.apply {
            btnFixedDeposit.apply {
                txtTitle.text = getString(R.string.fixed_deposit)
                root.setOnClickListener {
                    findNavController().navigate(R.id.action_homeFragment_to_fixedDepositFragment)
                }
            }
                btnRecurringDeposit.apply {
                    txtTitle.text = getString(R.string.recurring_deposit)
                    root.setOnClickListener{
                    findNavController().navigate(R.id.action_homeFragment_to_recurringDepositFragment)
                }
            }
        }
    }
}