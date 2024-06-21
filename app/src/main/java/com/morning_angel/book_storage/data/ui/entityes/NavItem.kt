package com.morning_angel.book_storage.data.ui.entityes

import androidx.compose.ui.graphics.vector.ImageVector
data class NavItem(
    val imageVector: ImageVector,
    val title: String,
    val selected: Boolean,
    val onClick: () -> Unit
)