package com.weathertracker.root.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "locations")
class Location(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") var user: User? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
)
