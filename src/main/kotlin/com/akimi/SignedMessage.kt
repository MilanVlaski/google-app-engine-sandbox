package com.akimi

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val signature: String,
    val intermediateSigningKey: IntermediateSigningKey,
    val protocolVersion: String,
    val signedMessage: String
)

@Serializable
data class IntermediateSigningKey(
    val signedKey: String,
    val signatures: List<String>
)

@Serializable
data class SignedMessage(
    val classId: String,
    val objectIds: List<String>,
    val eventType: String,
    val expTimeMillis: Long,
    val count: Int,
    val nonce: String,
    val deviceContext: String
)
