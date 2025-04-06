package com.github.ksalil.scribbledash.game.presentation.mvi

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class PathData(
    val id:String,
    val color: Color,
    val paths: List<Offset>
)
