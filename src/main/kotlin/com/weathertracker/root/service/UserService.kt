package com.weathertracker.root.service

import com.weathertracker.root.dto.LoginUserDto
import com.weathertracker.root.dto.mapper.UserMapper
import com.weathertracker.root.exception.UserAlreadyExistsException
import com.weathertracker.root.model.User
import com.weathertracker.root.repository.UserRepository
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
) {
    fun saveUser(loginUserDto: LoginUserDto): User =
        try {
            userRepository.save(userMapper.convertToModel(loginUserDto))
        } catch (e: DataIntegrityViolationException) {
            throw UserAlreadyExistsException("User already exists")
        } catch (e: ConstraintViolationException) {
            throw UserAlreadyExistsException("User already exists")
        }

    fun getAllUsers(): List<User> = userRepository.getAll()

    fun findByLogin(loginUserDto: LoginUserDto): User? = userRepository.findByLogin(loginUserDto.username)

    fun findById(id: Int?): User? = userRepository.findById(id)
}
