package com.mr_17.deposit_calculator

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mr_17.deposit_calculator.databinding.FragmentRecurringDepositBinding

class RecurringDepositFragment : Fragment(R.layout.fragment_recurring_deposit) {
    private lateinit var binding: FragmentRecurringDepositBinding
    private val database = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecurringDepositBinding.bind(view)

        (activity as MainActivity).setToolbarTitle(getString(R.string.recurring_deposit))

        initializeUI()

        binding.apply {
            btnCalculate.button.setOnClickListener{
                calculate(
                    getDays(
                        etMonth.editText.text.toString().toInt(),
                        etYear.editText.text.toString().toInt()
                    )
                )
            }
        }
    }

    private fun initializeUI() {
        binding.apply {
            dropdownDepositScheme.apply{
                tvLabel.heading.text = "Deposit Scheme"

                val items = resources.getStringArray(R.array.list_items_deposit_scheme)
                val adapter =
                    ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, items)
                (dropdown.editText as? AutoCompleteTextView)?.apply {
                    setText(items[0])
                    setAdapter(adapter)
                }
            }
            etAmount.apply {
                tvLabel.heading.text = "Amount"
                inputTextField.editText?.apply {
                    text = Editable.Factory.getInstance().newEditable("0")
                    inputType = InputType.TYPE_CLASS_NUMBER
                }
            }
            etYear.apply {
                tvLabel.heading.text = "Year"
                inputTextField.editText?.apply {
                    text = Editable.Factory.getInstance().newEditable("0")
                    inputType = InputType.TYPE_CLASS_NUMBER
                }
            }
            etMonth.apply {
                tvLabel.heading.text = "Month"
                inputTextField.editText?.apply {
                    text = Editable.Factory.getInstance().newEditable("0")
                    inputType = InputType.TYPE_CLASS_NUMBER
                }
            }
            btnCalculate.apply {
                button.text = "Calculate"
            }
        }
    }

    private fun calculate(days: Int) {
        /*Toast.makeText(context, days.toString(), Toast.LENGTH_SHORT).show()*/
        database.collection("Interest")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val interests = document.data.toSortedMap(compareBy<String> { it.length }.thenBy { it })
                    var match = interests[interests.firstKey()]
                    for (data in interests) {
                        if(days < data.key.toInt()) {
                            Toast.makeText(context, match.toString(), Toast.LENGTH_SHORT).show()
                            break
                        }
                        match = data
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("data", "Error getting documents.", exception)
            }
    }

    private fun getDays(month: Int, year: Int): Int {
        return (month * 30) + (year * 365)
    }
}