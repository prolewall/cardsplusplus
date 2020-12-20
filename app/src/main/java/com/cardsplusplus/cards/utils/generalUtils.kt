package com.cardsplusplus.cards.utils

fun incrementIndex(ind: Int, size: Int): Int {
    return (ind + 1) % size
}

fun decrementIndex(ind: Int, size: Int): Int {
    return (size + ind - 1) % size
}