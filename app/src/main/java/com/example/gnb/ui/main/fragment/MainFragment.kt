package com.example.gnb.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gnb.R
import com.example.gnb.api.model.ApiSuccessResponse
import com.example.gnb.databinding.MainFragmentBinding
import com.example.gnb.ui.main.MainViewModel
import com.example.gnb.ui.main.adapter.ItemAdapter
import com.example.gnb.utils.platform.extension.TwoButtonsDialog
import com.example.gnb.utils.platform.extension.showErrorDialog
import com.example.gnb.utils.platform.network.NetworkUtils
import com.example.gnb.utils.platform.ui.BaseFragment

/**
 * Fragment representing the product list
 */
class MainFragment : BaseFragment(), TwoButtonsDialog.TwoButtonDialogListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private var viewCreated = false

    override fun layoutId() = R.layout.main_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View {
        return if (!viewCreated) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getBinding() as MainFragmentBinding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.isTransactionListEmpty() || !viewCreated) {
            getTransactions()
            viewCreated = true
        }
    }

    override fun onTwoButtonsDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
        activity?.finish()
    }

    override fun onTwoButtonsDialogPositiveClick(dialog: DialogFragment) {
        dialog.dismiss()
        getTransactions()
    }

    /**
     * Retrieves the list of transactions from the service
     */
    private fun getTransactions() {
        if (NetworkUtils.isNetworkAvailable(activity!!)) {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getTransactions().observe(viewLifecycleOwner, Observer {
                binding.progressBar.visibility = View.GONE
                if (it is ApiSuccessResponse) {
                    val items = it.body.map { transaction -> transaction.sku }.toTypedArray()
                    val dividerItemDecoration = DividerItemDecoration(
                        binding.list.context,
                        LinearLayoutManager.VERTICAL
                    )
                    binding.list.apply {
                        layoutManager = LinearLayoutManager(activity)
                        addItemDecoration(dividerItemDecoration)
                        adapter =
                            ItemAdapter(items.distinct().toTypedArray(),
                                object :
                                    ItemAdapter.ItemClickListener {
                                    override fun onItemClick(item: String) {
                                        val action =
                                            MainFragmentDirections.actionMainFragmentToProductFragment(
                                                item
                                            )
                                        findNavController().navigate(action)
                                    }
                                })
                    }
                    viewModel.saveTransactionList(it.body)
                } else {
                    showErrorDialog(
                        activity!!, parentFragmentManager, getString(R.string.dialog_error_message),
                        this
                    )
                }
            })
        } else {
            binding.progressBar.visibility = View.GONE
            showErrorDialog(
                activity!!, parentFragmentManager, getString(R.string.dialog_no_network_message),
                this
            )
        }
    }
}
