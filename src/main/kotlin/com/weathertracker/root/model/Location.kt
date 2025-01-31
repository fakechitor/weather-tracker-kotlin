package com.weathertracker.root.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "locations", uniqueConstraints = [UniqueConstraint(columnNames = ["latitude", "longitude", "user_id"])])
class Location(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int? = null,
    var name: String? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") var user: User? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
)
