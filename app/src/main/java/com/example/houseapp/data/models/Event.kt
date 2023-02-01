package com.example.houseapp.data.models

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /**
     * Возвращает content и предотвращает повторное использование.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}