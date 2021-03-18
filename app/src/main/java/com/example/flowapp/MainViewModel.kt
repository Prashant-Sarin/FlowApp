package com.example.flowapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName

    fun startCollecting() {
        CoroutineScope(Dispatchers.Default).launch {
            processNumber()
                .collect {
                    Log.d(TAG, "collected: $it")
                    delay(2000)
                }

        }
    }

    private suspend fun processNumber() = flow {
        for (num in 0..10) {
            emit(num)
            Log.d(TAG, "processNumber() Emitted: $num ")
            delay(1000)
        }

    }.flowOn(Dispatchers.Default)


    fun startZipping() {
        CoroutineScope(Dispatchers.Default).launch {
            processNumber().zip(processLargeNumbers()) { num1, num2 -> num1 + num2 }
                .collect {
                    Log.d(TAG, "startZipping: value = $it")
                }
        }
    }


    private suspend fun processLargeNumbers() = flow {
        // Todo: change no of iterations
        for (num in 100..110) {
            emit(num)
            Log.d(TAG, "processLargeNumbers() Emitted: $num ")
            delay(1000)
        }
    }

//                .reduce { accumulator, value ->
//                    Log.d(TAG, "accumulator:$accumulator, value:$value")
//                    accumulator + value
//                }
//                .fold(50) { accumulator, value ->
//                    Log.d(TAG, "accumulator:$accumulator, value:$value")
//                    accumulator + value
//                }
//                .transform {
//                    val square = it * it
//                    emit("Number square = $square")
//                    emit(it)
//                }
//                .filter {
//                    it % 2 == 0
//                }
//                .map {
//                    "The number collected is $it"
//                }
//                .take(5) // limits no of result
//                .buffer() // keeps emitted values in buffer and gives to collect whenever available
//                .conflate() // gets the latest value from emit to collect


}