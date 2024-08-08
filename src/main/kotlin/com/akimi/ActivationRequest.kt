package com.akimi

import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.config.annotation.SerdeConfig.SerError

@Serdeable
data class ActivationRequest(
    val signature: String,
    val intermediateSigningKey: IntermediateSigningKey,
    val protocolVersion: String,
    val signedMessage: SignedMessage,
) {
    @Serdeable
    class IntermediateSigningKey(
        val signedKey: SignedKey,
        val signatures: List<String>
    )

    @Serdeable
    class SignedKey(
        val keyValue: String,
        val keyExpiration: String
    )

    @Serdeable
    class SignedMessage(
        val classId: String,
        val objectIds: List<String>,
        val eventType: String,
        val expTimeMillis: Int,
        val count: Int,
        val nonce: String,
        val deviceContext: String
    )
}
