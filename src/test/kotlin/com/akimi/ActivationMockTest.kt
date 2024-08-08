package com.akimi

import com.akimi.transit.TransitService
import com.akimi.transit.TransitServiceImpl
import com.google.api.services.walletobjects.model.ActivationStatus
import com.google.api.services.walletobjects.model.RotatingBarcode
import com.google.api.services.walletobjects.model.TransitObject
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
@Disabled
@MicronautTest
class ActivationMockTest {

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var transitService: TransitService

    @MockBean(TransitServiceImpl::class)
    fun transitService() = org.mockito.kotlin.mock<TransitService>()

    @Test
    fun `When Google calls back endpoint, correct transitObject patch is done`() {
        val objectId = "MEUCIAKrJb6SfblhX98zu+dGRd1KDzv5i8wsSz2UtMIb6PMQAiEAsletRJ0m/mlgh+Hi8/GZO5X3WWz9d+h5mk9gyLAJ6pQ="
        val requestBody = mapOf("objectIds" to listOf(objectId))

       client.toBlocking().exchange(
            HttpRequest.POST("/activateTicket", requestBody),
            Any::class.java
        )

        val expectedPatch = TransitObject()
            .setActivationStatus(ActivationStatus().setState("ACTIVATED"))
            .setRotatingBarcode(
                RotatingBarcode()
                    .setType("AZTEC")
            )
            .set("valuePattern", "{vdv_barcode}")
            .set("deviceEntitlementSupport", object {
                val vdvEntitlementDetails = object {
                    val applicationData = "123"
                }
            })

        org.mockito.kotlin.verify(transitService).patchObject(objectId, expectedPatch)
//        verify(transitService).patchObject(objectId, expectedPatch)
    }
}