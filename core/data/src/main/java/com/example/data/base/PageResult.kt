package com.example.data.base

data class PageResult<T>(
    val items: List<T>,
    val nextUrl: String?
)