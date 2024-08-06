package com.akimi

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.serde.annotation.Serdeable

@Controller("/activateTicket")
class ActivationController {

    @Post("/")
    fun activateTicket(@Body body: ActivationRequestBody)
            : HttpResponse<Any> {
        return HttpResponse.ok()
    }

    @Serdeable
    data class ActivationRequestBody(
        val classId: String,
        val expTimeMillis: Int,
        val eventType: String,
        val objectId: String, // base64 encoded ID of the TransitObject
        val deviceContext: String // base64 encoded SCE_ID
    )
}