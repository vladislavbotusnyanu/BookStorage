package com.morning_angel.book_storage.data.ui.entityes

import androidx.compose.ui.graphics.vector.ImageVector
import com.morning_angel.book_storage.data.ui.navigation.Screens

data class NavItem (
    val screen: Screens,
    val imageVector: ImageVector,
    val title: String,
    val onClick: (route : String) -> Unit
)