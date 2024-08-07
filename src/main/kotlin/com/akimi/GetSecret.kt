package com.akimi

import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import java.io.IOException

object GetSecret {
    fun accessSecret(projectId: String, secretId: String, versionId: String = "latest"): ByteArray? {
        try {
            SecretManagerServiceClient.create().use { client ->
                val request = AccessSecretVersionRequest.newBuilder()
                    .setName("projects/$projectId/secrets/$secretId/versions/$versionId")
                    .build()

                val response = client.accessSecretVersion(request)
                return response.payload.data.toByteArray()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException("Failed to access secret: $secretId")
        }
    }
}