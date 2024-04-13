package com.example.morningroutine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM : ViewModel> createViewModelFactory(
    viewModelInit: () -> VM,
): ViewModelProvider.Factory {

    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return viewModelInit() as T
        }
    }
}

fun ViewModel.createViewModelFactory(): ViewModelProvider.Factory = createViewModelFactory { this }

fun <VM : ViewModel> createViewModelFactoryProducer(
    viewModelInit: () -> VM,
): (() -> ViewModelProvider.Factory) = {
    createViewModelFactory { viewModelInit() }
}

fun ViewModel.createViewModelFactoryProducer(): () -> ViewModelProvider.Factory =
    createViewModelFactoryProducer { this }