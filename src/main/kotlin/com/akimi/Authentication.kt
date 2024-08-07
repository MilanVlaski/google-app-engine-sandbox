package com.akimi

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.walletobjects.Walletobjects
import com.google.api.services.walletobjects.WalletobjectsScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import java.io.ByteArrayInputStream


fun authenticate(appName: String, credentials: GoogleCredentials): Walletobjects {
    val httpTransport: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    // Initialize Google Wallet API service
    val walletobjects =
        Walletobjects.Builder(
            httpTransport,
            GsonFactory.getDefaultInstance(),
            HttpCredentialsAdapter(credentials)
        )
            .setApplicationName(appName)
            .build()
    return walletobjects
}

fun googleCredentials(secretContent: ByteArray): GoogleCredentials {
    val credentials = GoogleCredentials.fromStream(ByteArrayInputStream(secretContent))
        .createScoped(listOf(WalletobjectsScopes.WALLET_OBJECT_ISSUER))
    credentials.refresh()
    return credentials
}