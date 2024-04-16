package com.example.callscreenapp.redux.action

import androidx.fragment.app.Fragment

sealed class AppAction {
    data class ExampleAction(val updateValue: String) : AppAction()
    data class SetIconCallShowId(val updateValueGreen: Int, val updateValueRed: Int) : AppAction()
}