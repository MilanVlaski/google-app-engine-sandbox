package com.akimi

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JsonParsingTest {

    private val objectId = "3388000000022335801.efd427a7-05a4-439d-b314-e4b999c8509b"
    private val body = """
    {"signature":"MEUCIQCpYXep9bIFAT/PMe2kekpGdsWBPSIXZRFe2//QICWOFAIgCGPUJ6BnLDxvuJnMFz7IFkIV8jnIK0FQnKbNJw1i7oQ\u003d","intermediateSigningKey":{"signedKey":"{\"keyValue\":\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAElMAF/BuFhKQzFwpbZDWmpFRGsW8Ow4QZE31CbT4Y0O15xQ8AAMD8y698okEifN6UQlEh4et3hth9BDH81/Rkaw\\u003d\\u003d\",\"keyExpiration\":\"1723850860000\"}","signatures":["MEUCIGU5NAJcFcg03FWUq04gC0hYzVmRLHbwvlSCz6BTqTHGAiEAsRG9gdW5r5Gds4vk+Dj3V8X3iwF1aeflCiSCTi5qkgQ\u003d"]},"protocolVersion":"ECv2SigningOnly","signedMessage":"{\"classId\":\"3388000000022335801.eth-motics-with-link\",\"objectIds\":[\"3388000000022335801.efd427a7-05a4-439d-b314-e4b999c8509b\"],\"eventType\":\"activate\",\"expTimeMillis\":1723181833549,\"count\":1,\"nonce\":\"0cfe3927-7ce2-4471-bc27-c819c2910901\",\"deviceContext\":\"49776974-bcf3-4445-9c01-2c9467ef87ff\"}"}
    """.trimIndent()

    @Test
    fun `Extracts objectId from json body`() {
        assertEquals(objectId, ActivationController.extractObjectId(body))
    }
}