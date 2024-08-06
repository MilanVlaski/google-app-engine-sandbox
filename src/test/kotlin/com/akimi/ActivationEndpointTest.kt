package com.akimi

import com.akimi.ActivationController.ActivationRequestBody
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
class ActivationEndpointTest {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Test
    fun `Activation url responds with 'ok'`() {
        val requestBody = ActivationRequestBody(
            classId = "testClassId",
            expTimeMillis = 1234567890,
            eventType = "activate",
            objectId = "testObjectId",
            deviceContext = "testDeviceContext"
        )

        val response = client.toBlocking().exchange(
            HttpRequest.POST("/activateTicket", requestBody),
            Any::class.java
        )

        assertEquals(HttpStatus.OK, response.status)
    }
}