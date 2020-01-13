package com.example.gnb.utils

import org.jgrapht.graph.DefaultEdge
import java.math.BigDecimal

/**
 * Helper class for representing a node in a graph representation
 * @param rate the rate of this conversion
 */
class ConversionEdge(var rate: BigDecimal) : DefaultEdge()