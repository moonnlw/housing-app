package com.example.houseapp.data.models

import androidx.annotation.StringRes

sealed class UIMessage {
    data class DynamicString(val value: String) : UIMessage()
    data class StringResource(@StringRes val resId: Int) : UIMessage()
}