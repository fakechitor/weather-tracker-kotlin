package com.weathertracker.root.service

import com.weathertracker.root.dto.UserDto
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class LoginService {
    private val userRepository = UserRepository()

    fun saveUser(userDto: UserDto): User = userRepository.save(User(login = userDto.login, password = userDto.password))
}
