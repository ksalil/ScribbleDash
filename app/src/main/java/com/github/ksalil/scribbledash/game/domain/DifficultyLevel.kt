package com.github.ksalil.scribbledash.game.domain

import kotlinx.serialization.Serializable

@Serializable
enum class DifficultyLevel {
    BEGINNER,
    CHALLENGING,
    MASTER
}