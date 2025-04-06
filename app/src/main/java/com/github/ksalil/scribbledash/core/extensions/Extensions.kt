package com.github.ksalil.scribbledash.core.extensions

/**
 * Creates a copy of the ArrayDeque.
 * @return A new ArrayDeque containing all elements from the original.
 */
fun <T> ArrayDeque<T>.clone(): ArrayDeque<T> = ArrayDeque(this)