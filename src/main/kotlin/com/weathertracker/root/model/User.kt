package com.weathertracker.root.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
open class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) open var id: Int? = null,
    @Column(name = "login", unique = true) open var login: String? = null,
    open var password: String? = null,
)
