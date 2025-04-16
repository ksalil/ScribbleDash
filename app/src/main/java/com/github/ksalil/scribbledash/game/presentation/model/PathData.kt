package com.github.ksalil.scribbledash.game.presentation.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin

data class PathData(
    val id:String,
    val color: Color,
    val paths: List<Offset>,
    val thickness: Float = 10f,
    val smoothness:Int = 5,
    val strokeCap: StrokeCap = StrokeCap.Round,
    val strokeJoin: StrokeJoin = StrokeJoin.Round
)
