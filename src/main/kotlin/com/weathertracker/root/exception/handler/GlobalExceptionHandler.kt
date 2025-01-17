package com.weathertracker.root.exception.handler

import com.weathertracker.root.exception.*
import org.hibernate.exception.ConstraintViolationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice("")
class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException): ModelAndView = ModelAndView("login").apply { addObject("error", e.message) }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(e: UserAlreadyExistsException) = ModelAndView("sign_up").apply { addObject("error", e.message) }

    @ExceptionHandler(IncorrectPasswordException::class)
    fun handleIncorrectPasswordException(e: IncorrectPasswordException) = ModelAndView("login").apply { addObject("error", e.message) }

    @ExceptionHandler(PasswordMismatchException::class)
    fun handleIllegalArgumentException(e: PasswordMismatchException) = ModelAndView("sign_up").apply { addObject("error", e.message) }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException) = ModelAndView("error").apply { addObject("error", e.message) }

    @ExceptionHandler(OpenWeatherApiException::class)
    fun handleOpenWeatherApiException(e: OpenWeatherApiException) = ModelAndView("error").apply { addObject("error", e.message) }

    @ExceptionHandler(LocationAlreadyExistsException::class)
    fun handleLocationAlreadyExistsException(e: LocationAlreadyExistsException) =
        ModelAndView("error").apply { addObject("error", e.message) }
}
