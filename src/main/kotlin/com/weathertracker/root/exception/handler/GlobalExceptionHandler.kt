package com.weathertracker.root.exception.handler

import com.weathertracker.root.exception.*
import com.weathertracker.root.util.Util.getDtoName
import com.weathertracker.root.util.Util.getErrorPagePath
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.validation.BindingResult.MODEL_KEY_PREFIX
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice("")
class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(e: UserNotFoundException) = ModelAndView("login").apply { addObject("error", e.message) }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(e: UserAlreadyExistsException) = ModelAndView("sign_up").apply { addObject("error", e.message) }

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
    ) = when (req.requestURI) {
        "/search" -> ModelAndView("error").apply { addObject("errors", ex.bindingResult.fieldErrors.map { it.defaultMessage }) }
        else ->
            ModelAndView(req.requestURI.getErrorPagePath()).apply {
                addObject(req.requestURI.getDtoName(), ex.bindingResult.target)
                addObject(MODEL_KEY_PREFIX + req.requestURI.getDtoName(), ex.bindingResult)
            }
    }
}
