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
//        try {
//            userRepository.save(userMapper.convertToModel(loginUserDto))
//        } catch (e: DataIntegrityViolationException) {
//            throw UserAlreadyExistsException("User already exists")
//        } catch (e: ConstraintViolationException) {
//            throw UserAlreadyExistsException("User already exists")
//        }
        runCatching { userRepository.save(userMapper.convertToModel(loginUserDto)) }.onFailure {
            throw UserAlreadyExistsException("User already exists")
        }

    fun findByLogin(loginUserDto: LoginUserDto): User? = userRepository.findByLogin(loginUserDto.username)

    fun findById(id: Int?): User? = userRepository.findById(id)
}
