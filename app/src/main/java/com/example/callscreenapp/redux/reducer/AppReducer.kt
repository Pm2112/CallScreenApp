package com.example.callscreenapp.redux.reducer

import com.example.callscreenapp.redux.action.AppAction
import com.example.callscreenapp.redux.state.AppState
import org.reduxkotlin.Reducer

val appReducer: Reducer<AppState> = { state, action ->
    when (action) {
        is AppAction.ExampleAction -> state.copy(exampleProperty = action.updateValue)
        is AppAction.SetIconCallShowId -> state.copy(iconCallShowGreen = action.updateValueGreen, iconCallShowRed = action.updateValueRed)
        is AppAction.SetAvatarPosition -> state.copy(avatarPosition = action.updateValue)
        is AppAction.SetCategory -> state.copy(categoryName = action.updateValue)
        is AppAction.SetBackgroundUrl -> state.copy(backgroundUrl = action.updateValue)
        is AppAction.SetAvatarUrl -> state.copy(avatarUrl = action.updateValue)
        else -> state
    }
}