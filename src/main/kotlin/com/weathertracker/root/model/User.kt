package com.weathertracker.root.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(name = "login", unique = true)
    var login: String? = null,
    var password: String? = null,
)
