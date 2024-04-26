package com.example.callscreenapp.redux.action

import androidx.fragment.app.Fragment

sealed class AppAction {
    data class ExampleAction(val updateValue: String) : AppAction()
    data class SetIconCallShowId(val updateValueGreen: String, val updateValueRed: String) : AppAction()
    data class SetAvatarUrl(val updateValue: String) : AppAction()
    data class SetAvatarPosition(val updateValue: Int) : AppAction()
    data class SetCategory(val updateValue: String) : AppAction()
    data class SetBackgroundUrl(val updateValue: String) : AppAction()
}