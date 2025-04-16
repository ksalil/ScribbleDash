package com.github.ksalil.scribbledash.core.navigation.destinations

import com.github.ksalil.scribbledash.game.domain.DifficultyLevel
import kotlinx.serialization.Serializable

@Serializable
data class DrawScreenDestination(val difficultyLevel: DifficultyLevel)
