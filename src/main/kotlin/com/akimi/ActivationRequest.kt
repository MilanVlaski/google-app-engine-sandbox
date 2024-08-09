package com.akimi

import io.micronaut.serde.annotation.Serdeable

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
        val keyValue: Long,
        val keyExpiration: Long
    )

    @Serdeable
    class SignedMessage(
        val classId: String,
        val objectIds: List<String>,
        val eventType: String,
        val expTimeMillis: Long,
        val count: Int,
        val nonce: String,
        val deviceContext: String
    )
}
