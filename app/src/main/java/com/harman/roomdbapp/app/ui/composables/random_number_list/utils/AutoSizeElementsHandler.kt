package com.harman.roomdbapp.app.ui.composables.random_number_list.utils

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

/**
 * Update overall specified size value what can be observed from different elements to make size equal
 * when all elements are calculated set CalculatedState to Complete
 *  @param initialOverallSize set initial size value what should be general for each elements
 *  @param elementCount amount of all elements
 */
class AutoSizeElementsHandler<T>(private val initialOverallSize: T, private val elementCount: Int) {
    private var calculationState =
        mutableStateOf<CalculationState>(CalculationState.CalculationInProcess)

    private val calculatedElements = mutableStateListOf<CalculationState>()
    private val overallElementSize = mutableStateOf<T>(initialOverallSize)

    fun emitNewValue(value: T) {
        overallElementSize.value = value
    }

    fun emitCalculationComplete() {
        calculatedElements.add(CalculationState.CalculationComplete)
        if (calculatedElements.size == elementCount)
            calculationState.value = CalculationState.CalculationComplete
    }

    fun getOverallElementSizeState() = overallElementSize.value
    fun getCalculatedState() = calculationState.value
}

enum class CalculationState {
    CalculationInProcess,
    CalculationComplete
}
