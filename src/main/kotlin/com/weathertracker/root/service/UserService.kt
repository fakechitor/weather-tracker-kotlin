package com.weathertracker.root.service

import com.weathertracker.root.dto.UserMapper
import com.weathertracker.root.dto.UserDto
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {
    fun saveUser(userDto: UserDto): User = userRepository.save(userMapper.convertToModel(userDto))

    fun getAllUsers(): List<User> = userRepository.getAll()

    fun isUserExist(loginDto: UserDto): Boolean = findByLoginAndPassword(loginDto) != null

    fun findByLoginAndPassword(loginDto: UserDto): User? =
        userRepository.findByLoginAndPassword(login = loginDto.login, password = loginDto.password)

    fun findById(id: Int?): User? = userRepository.findById(id)
}
