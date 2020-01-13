package com.example.gnb.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gnb.R
import com.example.gnb.api.model.ApiSuccessResponse
import com.example.gnb.api.model.Transaction
import com.example.gnb.databinding.ProductFragmentBinding
import com.example.gnb.ui.main.MainViewModel
import com.example.gnb.ui.main.adapter.ProductAdapter
import com.example.gnb.utils.platform.extension.TwoButtonsDialog
import com.example.gnb.utils.platform.extension.showErrorDialog
import com.example.gnb.utils.platform.network.NetworkUtils
import com.example.gnb.utils.platform.ui.BaseFragment
import java.text.NumberFormat

/**
 * Fragment representing the transaction list
 */
class ProductFragment : BaseFragment(), TwoButtonsDialog.TwoButtonDialogListener {

    private val args: ProductFragmentArgs by navArgs()
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ProductFragmentBinding
    private lateinit var transactions: List<Transaction>

    override fun layoutId() = R.layout.product_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getBinding() as ProductFragmentBinding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)
        transactions = viewModel.getTransactionList(args.item)
        if (viewModel.getConversionList().isNullOrEmpty()) {
            getConversions()
        } else {
            binding.total.text = getString(
                R.string.label_total,
                NumberFormat.getCurrencyInstance().format(viewModel.getTotalAmount(transactions))
            )
        }
        val dividerItemDecoration =
            DividerItemDecoration(binding.list.context, LinearLayoutManager.VERTICAL)
        binding.list.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(dividerItemDecoration)
            adapter = ProductAdapter(transactions)
        }
    }

    override fun onTwoButtonsDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
        binding.total.text = getString(R.string.calculation_error)
    }

    override fun onTwoButtonsDialogPositiveClick(dialog: DialogFragment) {
        dialog.dismiss()
        getConversions()
    }

    /**
     * Retrieves the list of conversions from the API
     */
    private fun getConversions() {
        if (NetworkUtils.isNetworkAvailable(activity!!)) {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getConversions().observe(viewLifecycleOwner, Observer {
                binding.progressBar.visibility = View.GONE
                if (it is ApiSuccessResponse) {
                    viewModel.saveConversions(it.body)
                    binding.total.text = getString(
                        R.string.label_total,
                        NumberFormat.getCurrencyInstance().format(
                            viewModel.getTotalAmount(
                                transactions
                            )
                        )
                    )
                } else {
                    binding.total.text = getString(R.string.calculation_error)
                    showErrorDialog(
                        activity!!, parentFragmentManager, getString(R.string.dialog_error_message),
                        this
                    )
                }
            })
        } else {
            binding.total.text = getString(R.string.calculation_error)
            showErrorDialog(
                activity!!, parentFragmentManager, getString(R.string.dialog_no_network_message),
                this
            )
        }
    }
}