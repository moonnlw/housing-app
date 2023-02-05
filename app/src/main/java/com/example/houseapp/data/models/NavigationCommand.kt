package com.example.houseapp.data.models

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class NavigateToDirection(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
}