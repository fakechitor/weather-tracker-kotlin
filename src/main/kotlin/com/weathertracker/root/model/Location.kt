package com.weathertracker.root.model

import jakarta.persistence.*

@Entity
@Table(name = "locations", uniqueConstraints = [UniqueConstraint(columnNames = ["latitude", "longitude", "user_id"])])
open class Location(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) open var id: Int? = null,
    open var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") open var user: User? = null,
    open var latitude: Double? = null,
    open var longitude: Double? = null,
)
