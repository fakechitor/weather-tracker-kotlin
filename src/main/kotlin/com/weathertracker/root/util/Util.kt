package com.weathertracker.root.util

import org.springframework.stereotype.Component

@Component
class Util {
    companion object {
        fun String.getErrorPagePath() = "sign_up_error".takeIf { this.contains("/sign_up") } ?: "login_error"

        fun String.getDtoName() = "signupUserDto".takeIf { this.contains("/sign_up") } ?: "loginUserDto"
    }
}
