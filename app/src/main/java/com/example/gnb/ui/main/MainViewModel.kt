package com.example.gnb.ui.main

import androidx.lifecycle.ViewModel
import com.example.gnb.api.model.Conversion
import com.example.gnb.api.model.Transaction
import com.example.gnb.repository.MainRepository
import javax.inject.Inject

/**
 * [ViewModel] for the MainActivity
 * @param repository the repository where all the data is stored and processed
 */
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    /**
     * Retrieves the list of conversions from the service
     * @return the response object containing the data
     */
    fun getConversions() = repository.getConversions()

    /**
     * Retrieves the list of transactions from the service
     * @return the response object containing the data
     */
    fun getTransactions() = repository.getTransactions()

    /**
     * Save a list of transactions
     * @param list the transaction list to be saved
     */
    fun saveTransactionList(list: List<Transaction>) {
        repository.transactionList = list
    }

    /**
     * Get the transaction list for a given product
     * @param product the name of the product
     * @return the list of transactions for the specified product
     */
    fun getTransactionList(product: String) = repository.getTransactionList(product)

    /**
     * Save a list of conversions
     * @param list the list of conversions to be saved
     */
    fun saveConversions(list: List<Conversion>) {
        repository.saveConversions(list)
    }

    /**
     * Get the already saved conversion list
     */
    fun getConversionList() = repository.conversionList

    /**
     * Get the total amount for a list of transactions
     * @param list the list of transactions
     * @return the total amount represented in the EUR currency
     */
    fun getTotalAmount(list: List<Transaction>) = repository.getTotalAmount(list)

    /**
     * Check if the transaction list was already retrieved
     * @return true if it already saved, false otherwise
     */
    fun isTransactionListEmpty() = repository.transactionList.isNullOrEmpty()
}
