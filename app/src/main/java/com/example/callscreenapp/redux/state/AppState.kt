package com.example.callscreenapp.redux.state


data class AppState(
    val exampleProperty: String = "initial state",
    val iconCallShowGreen: String = "",
    val iconCallShowRed: String = "",
    val avatarUrl: String = "",
    val avatarPosition: Int = 0,
    val categoryName: String = "All",
    val backgroundUrl: String = ""
)
