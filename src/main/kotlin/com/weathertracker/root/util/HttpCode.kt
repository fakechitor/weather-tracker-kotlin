package com.weathertracker.root.util

enum class HttpCode(
    val code: Int,
) {
    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    ;

    companion object {
        fun fromCode(code: Int): HttpCode = entries.find { it.code == code } ?: INTERNAL_SERVER_ERROR
    }
}
