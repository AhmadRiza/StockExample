package com.github.ahmadriza.stockbit.models

data class MenuItem(
    val title: String,
    val onClick: () -> Unit
)