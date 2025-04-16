package com.github.ksalil.scribbledash.core

import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(
    private val testDispatcher: TestDispatcher
) : DispatcherProvider {
    override val default = testDispatcher
    override val main = testDispatcher
    override val io = testDispatcher
}