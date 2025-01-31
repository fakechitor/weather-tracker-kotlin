package com.weathertracker.root.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "sessions")
open class Session(
    @Id @Column(name = "id", unique = true, nullable = false) open var id: String = UUID.randomUUID().toString(),
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") open var user: User? = null,
    @Column(name = "expires_at") open var expiresAt: LocalDateTime? = null,
)
