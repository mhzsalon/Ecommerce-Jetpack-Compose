package com.example.ecomm.core.extensions

fun String.toTitleCase(): String =
    split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.titlecase() }
    }