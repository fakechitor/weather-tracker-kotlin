package com.weathertracker.root.service

import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.dto.mapper.UserMapper
import com.weathertracker.root.exception.UserAlreadyExistsException
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
) {
    @Transactional
    fun saveUser(loginUserDto: LoginUserDto) =
        runCatching { userRepository.save(userMapper.convertToModel(loginUserDto)) }.fold(
            onSuccess = { it },
            onFailure = { throw UserAlreadyExistsException("User already exists") },
        )

    @Transactional(readOnly = true)
    fun findByLogin(loginUserDto: LoginUserDto): User? = userRepository.findByLogin(loginUserDto.username)

    @Transactional(readOnly = true)
    fun findById(id: Int?): User? = userRepository.findById(id)
}
