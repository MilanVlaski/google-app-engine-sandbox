package com.akimi

import com.akimi.transit.TransitService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.api.services.walletobjects.model.ActivationStatus
import com.google.api.services.walletobjects.model.RotatingBarcode
import com.google.api.services.walletobjects.model.TransitObject
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.serde.annotation.Serdeable
import jakarta.inject.Inject
import org.slf4j.LoggerFactory

@Controller("/activateTicket")
class ActivationController(@Inject val transitService: TransitService) {

    private val log = LoggerFactory.getLogger(ActivationController::class.java)

    private val mapper = ObjectMapper().registerKotlinModule()

    @Post("/")
    fun activateTicket(@Body body: String): HttpResponse<Any> {
        log.info("Activating ticket with body: $body")
        try {
//            `val objectId = body.signedMessage.objectIds[0]

            val hexAppData =
                "9E" + "8180" + "00".repeat(128) +
                        "9A" + "00" +
                        "7F21" + "81C8" + "00".repeat(200) +
                        "42" + "08" + "00".repeat(8)

//            transitService.patchObject(objectId, moticsPatchedObject(hexAppData))
        } catch (e: Exception) {
            log.error("Error activating ticket ", e)
        }
        return HttpResponse.ok()
    }

    companion object {

        fun extractObjectId(body: String): String {
            return ""
        }
    }

    @Serdeable
    data class ActivationRequestBody(
        val classId: String?, val expTimeMillis: Int?, val eventType: String?, val objectId: String?, // base64 encoded ID of the TransitObject
        val deviceContext: String? // base64 encoded SCE_ID
    )

    fun moticsPatchedObject(hexAppData: String): TransitObject = TransitObject().setActivationStatus(ActivationStatus().setState("ACTIVATED")).setRotatingBarcode(
        RotatingBarcode().setType("AZTEC")
    ).set("valuePattern", "{vdv_barcode}").set("deviceEntitlementSupport", object {
        val vdvEntitlementDetails = object {
            val applicationData = hexAppData
        }
    })

}