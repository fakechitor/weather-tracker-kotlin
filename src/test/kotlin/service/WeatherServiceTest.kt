package service

import com.weathertracker.root.config.spring.SpringConfiguration
import com.weathertracker.root.exception.OpenWeatherApiException
import com.weathertracker.root.model.Location
import com.weathertracker.root.model.User
import com.weathertracker.root.service.LocationService
import com.weathertracker.root.service.OpenWeatherApiService
import com.weathertracker.root.service.WeatherService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals

@ActiveProfiles("test")
@SpringJUnitConfig(SpringConfiguration::class)
@WebAppConfiguration
@Transactional(transactionManager = "transactionManager")
@Rollback
class WeatherServiceTest {
    @MockitoBean
    private lateinit var locationService: LocationService

    @Autowired
    private lateinit var weatherService: WeatherService

    @MockitoBean
    private lateinit var openWeatherApiService: OpenWeatherApiService

    @Test
    fun getCitiesWithApiTest() {
        whenever(openWeatherApiService.getJsonDataForAllCities("sad")).thenReturn(
            """
            [{
              "name": "sad",
              "lat": 58.6035661,
              "lon": 49.6666241,
              "country": "KR",
              "state": "North Korea"
            },
            {
             "name": "sad",
              "lat": 54.074322,
              "lon": 34.302608,
              "country": "KR",
              "state": "South Korea"
            }
            ]
            """.trimIndent(),
        )
        val data = weatherService.getAllLocationsByCityName("sad")
        assertNotNull(data)
        assertEquals(58.6035661, data[0].latitude)
        assertEquals(54.074322, data[1].latitude)
    }

    @Test
    fun getWeatherInfoWithApiTest() {
        val location = Location(name = "Seoul")
        val user = User(login = "Alexey")
        whenever(locationService.getLocationsForUser(user)).thenReturn(listOf(location))
        whenever(openWeatherApiService.getJsonDataForLocationInfo(location)).thenReturn(
            """
            {   
                 "coord": {
                    "lon": 228,
                    "lat": 322
                 },
                 "main": {
                     "temp": 269.91,
                     "feels_like": 269.91,
                     "humidity": 80
                 },
                 "weather": [
                     {
                         "icon": "02n"
                     }
                 ],
                 "sys": {
                     "country": "KR"
                 },
                 "name": "Seoul",
                 "cod": 200
            }
            """.trimIndent(),
        )
        val data = weatherService.getWeatherInfoForAuthorizedUser(user)
        assertNotNull(data)
        assertEquals("KR", data[0].country)
        assertEquals("Seoul", data[0].city)
    }

    @Test
    fun throwOpenApiExceptionTest() {
        val location = Location(name = "some_wrong_location")
        val user = User(login = "Alexey")
        whenever(locationService.getLocationsForUser(user)).thenReturn(listOf(location))
        whenever(openWeatherApiService.getJsonDataForLocationInfo(location)).thenReturn(
            """
            {
            "cod": 500
            }
            """.trimIndent(),
        )

        val exception =
            assertThrows<OpenWeatherApiException> {
                weatherService.getWeatherInfoForAuthorizedUser(user)
            }
        assertEquals("Internal Server Error", exception.message)
    }
}
