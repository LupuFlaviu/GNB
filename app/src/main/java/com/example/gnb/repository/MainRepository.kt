package com.example.gnb.repository

import androidx.lifecycle.liveData
import com.example.gnb.api.WebService
import com.example.gnb.api.model.ApiResponse
import com.example.gnb.api.model.Conversion
import com.example.gnb.api.model.Transaction
import com.example.gnb.utils.ConversionEdge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.DefaultDirectedWeightedGraph
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for the MainActivity used for retaining and processing data
 * @param webService the service from which the data is retrieved
 */
@Singleton
class MainRepository @Inject constructor(private val webService: WebService) {

    companion object {
        const val EUR_CURRENCY = "EUR"
        const val SCALE = 2
    }

    var transactionList: List<Transaction>? = null
    var conversionList: List<Conversion>? = null
    private lateinit var graph: DefaultDirectedWeightedGraph<String, ConversionEdge>

    /**
     * Get conversion from the service
     * @return the response from the service wrapped in an [ApiResponse]
     */
    fun getConversions() = liveData(Dispatchers.IO) {
        emit(coroutineScope {
            val currencies = async { webService.getConversions() }
            return@coroutineScope ApiResponse.create(currencies.await())
        })
    }

    /**
     * Get the transactions from the service
     * @return the response from the service wrapped in an [ApiResponse]
     */
    fun getTransactions() = liveData(Dispatchers.IO) {
        emit(coroutineScope {
            val transactions = async { webService.getTransactions() }
            return@coroutineScope ApiResponse.create(transactions.await())
        })
    }

    /**
     * Returns the list of transactions for a specified product
     * @param product the product for which the transactions should be returned
     * @return the list of transactions for the specified product
     */
    fun getTransactionList(product: String): List<Transaction> {
        val list = arrayListOf<Transaction>()
        for (transaction in transactionList!!) {
            if (transaction.sku.contentEquals(product)) {
                list.add(transaction)
            }
        }
        return list
    }

    /**
     * Saves the list of conversions retrieved from the API and creates a graph in order to be able to convert
     * from one currency to another even if the direct conversion is not available
     * @param list the list of conversions retrieved from the service
     */
    fun saveConversions(list: List<Conversion>) {
        conversionList = list
        graph = DefaultDirectedWeightedGraph(ConversionEdge::class.java)
        list.forEach {
            graph.addVertex(it.from)
            graph.addVertex(it.to)
            graph.addEdge(it.from, it.to, ConversionEdge(it.rate.toBigDecimal()))
        }
    }

    /**
     * Calculates the total amount of the transactions for a given product
     * @param list the list of transactions
     * @return the total amount represented as a [BigDecimal] in the EUR currency
     */
    fun getTotalAmount(list: List<Transaction>): BigDecimal {
        var total = BigDecimal(0)
        list.forEach {
            var amount = it.amount.toBigDecimal()
            val path = DijkstraShortestPath.findPathBetween(graph, it.currency, EUR_CURRENCY)
            path.edgeList.forEach { edge ->
                amount = (edge.rate * (amount))
                    .setScale(SCALE, BigDecimal.ROUND_HALF_EVEN)
            }
            total += amount
        }
        return total
    }
}
