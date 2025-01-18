package com.weathertracker.root.exception.handler

import com.weathertracker.root.exception.*
import com.weathertracker.root.util.Util
import com.weathertracker.root.util.Util.Companion.getDtoName
import com.weathertracker.root.util.Util.Companion.getErrorPagePath
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.validation.BindingResult.MODEL_KEY_PREFIX
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice("")
class GlobalExceptionHandler(
    private val util: Util,
) {
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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        req: HttpServletRequest,
    ) = ModelAndView(req.requestURI.getErrorPagePath()).apply {
        addObject(req.requestURI.getDtoName(), ex.bindingResult.target)
        addObject(MODEL_KEY_PREFIX + req.requestURI.getDtoName(), ex.bindingResult)
    }
}
