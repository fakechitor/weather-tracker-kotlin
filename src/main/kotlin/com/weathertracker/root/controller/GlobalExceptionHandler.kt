package com.weathertracker.root.controller

import com.weathertracker.root.exception.UserAlreadyExistsException
import com.weathertracker.root.exception.UserNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice("")
class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException): ModelAndView = ModelAndView("login").apply { addObject("error", e.message) }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(e: UserAlreadyExistsException): ModelAndView =
        ModelAndView("sign_up").apply { addObject("error", e.message) }
}
