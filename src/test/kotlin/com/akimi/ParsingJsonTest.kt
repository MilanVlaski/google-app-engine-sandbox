package com.akimi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Test

class ParsingJsonTest {

    val json = "{\"signature\": \"MEYCIQCs9FJ4xV9As878eBive+4fcK0dkQ1XBVSI5o/cjMfdSwIhAOSkviIY4tColToiTcsXNOcBxqgTL9sW43jTuCgSxiTK\", \"intermediateSigningKey\": {\"signedKey\":\"{\\\"keyValue\\\":\\\"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAETt48SlRpPOBcGagtieILFG0Qn7j2XsEI+Rb0AB8rMTxtuICoOtq48H7vaIOenDKD2a8C6cOaLivwyqTE50xV0w\\\\u003d\\\\u003d\\\",\\\"keyExpiration\\\":\\\"1723787680000\\\"}\", \"signatures\":[\"MEUCIQCMHiBrOk2s2BVmbM8U2q50MYPdSXAbZOUheNgRnY6aVQIgBgaTsslSdUuKPcO797O+9DjKErzeHYn1sqtQtVGAHkA\\u003d\"]}, \"protocolVersion\": \"ECv2SigningOnly\", \"signedMessage\": \"{\\\"classId\\\":\\\"3388000000022335801.eth-motics-with-link\\\",\\\"objectIds\\\":[\\\"3388000000022335801.5d1ef42c-c9f4-4d29-b8b6-5dfc0de351b9\\\"],\\\"eventType\\\":\\\"activate\\\",\\\"expTimeMillis\\\":1723117456414,\\\"count\\\":1,\\\"nonce\\\":\\\"841e0468-3cce-4bae-b82a-33c2aca7150c\\\",\\\"deviceContext\\\":\\\"002ddae0-a66e-4155-b1a9-d23c471431ff\\\"}\"}"
    val mapper = ObjectMapper().registerKotlinModule()


    @Test
    fun `Parse request`() {
        val tree = mapper.readTree(json)
        val signedMessage = tree.get("signedMessage")
        println(signedMessage.asText())
        val objectIds = signedMessage.get("objectIds")
//        val objectId = objectIds.get(0).asText()
    }
}