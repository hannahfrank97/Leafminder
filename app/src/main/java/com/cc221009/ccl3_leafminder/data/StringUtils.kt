package com.cc221009.ccl3_leafminder.data

fun String.toNormalCase(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

