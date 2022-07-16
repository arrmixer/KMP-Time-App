package com.company.myapplication.android.ui

import androidx.compose.runtime.Composable

// Define an alias named OnAddType that takes a
// list of strings and does not return anything.
typealias OnAddType =  (List<String>) -> Unit

// Define an alias used when dismissing a dialog
typealias onDismissType =  () -> Unit

// Define a composable function
typealias composeFun =  @Composable () -> Unit

// Define a function that takes an integer
typealias topBarFun =  @Composable (Int) -> Unit

// Define an empty composable function (as a default variable for the Top Bar)
@Composable
fun EmptyComposable() {
}
