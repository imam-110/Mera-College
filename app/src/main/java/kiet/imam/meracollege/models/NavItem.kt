package kiet.imam.meracollege.models

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title : String,
    val icon : Int
)

data class BottomNavigationItem(
    val title : String,
    val icon : Int,
    val route : String
)
