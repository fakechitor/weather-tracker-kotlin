package com.weathertracker.root.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TestController {
    @GetMapping
    fun sendParam(model: Model): String {
        model.addAttribute("name", "Alex")
        model.addAttribute("age", 18)
        return "test"
    }
}
