package com.akimi.transit

import com.akimi.GetSecret
import com.akimi.authenticate
import com.akimi.googleCredentials
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.walletobjects.Walletobjects
import com.google.api.services.walletobjects.model.*
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import jakarta.inject.Singleton
import java.security.interfaces.RSAPrivateKey
import java.util.*
import kotlin.collections.HashMap

/**
 * Potential names: GoogleTransitService, GoogleWalletService, WalletService.
 * This one is dependent on Wallet API completely. But in the future, TPS might
 * use Wallet service, or it's regular services polymorphically (using the TransitService
 * interface)
 */
@Singleton
class TransitServiceImpl : TransitService {
    private val googleCredentials: GoogleCredentials? = GetSecret.accessSecret("orbital-anchor-431604-n2", "CREDENTIALS_PATH")?.let { googleCredentials(it) }
    private val walletObjects: Walletobjects? = googleCredentials?.let { authenticate("TPS", it) }


    override fun createObject(issuerId: String, className: String, passengerName: String, email: String): TransitObject? {
        val objectSuffix = email.replace("@", "_") + UUID.randomUUID().toString()
        try {
            walletObjects?.transitobject()?.get(String.format("%s.%s", issuerId, objectSuffix))?.execute()

            System.out.printf("Object %s.%s already exists!%n", issuerId, objectSuffix)
        } catch (ex: GoogleJsonResponseException) {
            if (ex.statusCode == 404) {
                // Object does not exist,
                // Do nothing
            } else {
                // Something else went wrong...
                ex.printStackTrace()
            }
        }

        val newObject = staticObject(objectSuffix, passengerName, className, issuerId)

        val response = walletObjects?.transitobject()?.insert(newObject)?.execute()

        return response
    }

    override fun generateJWTToken(transitObject: TransitObject): String {
        val claims = HashMap<String, Any?>()
        claims["iss"] = (googleCredentials as ServiceAccountCredentials?)!!.clientEmail
        claims["aud"] = "google"
        claims["origins"] = listOf("")
        claims["typ"] = "savetowallet"

        // Create the Google Wallet payload and add to the JWT
        val payload = HashMap<String, Any>()
        payload["transitObjects"] = listOf(transitObject)
        claims["payload"] = payload

        // The service account credentials are used to sign the JWT
        val algorithm =
            Algorithm.RSA256(
                null,
                googleCredentials!!.privateKey as RSAPrivateKey
            )
        val token = JWT.create().withPayload(claims).sign(algorithm)

        return String.format("https://pay.google.com/gp/v/save/%s", token)
    }

    override fun staticObject(objectSuffix: String, passengerName: String, className: String, issuerId: String): TransitObject = TransitObject()
        .setId(String.format("%s.%s", issuerId, objectSuffix))
        .setClassId(String.format("%s.%s", issuerId, className))
        .setState("ACTIVE")
        .setHeroImage(
            Image()
                .setSourceUri(
                    ImageUri()
                        .setUri(
                            "https://farm4.staticflickr.com/3723/11177041115_6e6a3b6f49_o.jpg"
                        )
                )
                .setContentDescription(
                    LocalizedString()
                        .setDefaultValue(
                            TranslatedString()
                                .setLanguage("en-US")
                                .setValue("Hero image description")
                        )
                )
        ).setBarcode(Barcode().setType("QR_CODE").setValue("QR code value"))
        .setPassengerType("SINGLE_PASSENGER")
        .setPassengerNames(passengerName)
        .setTripType("ONE_WAY")
        .setTicketLeg(
            TicketLeg()
                .setOriginStationCode("SRC")
                .setOriginName(
                    LocalizedString()
                        .setDefaultValue(
                            TranslatedString()
                                .setLanguage("en-US")
                                .setValue("Origin name")
                        )
                )
                .setDestinationStationCode("DST")
                .setDestinationName(
                    LocalizedString()
                        .setDefaultValue(
                            TranslatedString()
                                .setLanguage("en-US")
                                .setValue("Destination name")
                        )
                )
                .setDepartureDateTime("2020-04-12T16:20:50.52Z")
                .setArrivalDateTime("2020-04-12T20:20:50.52Z")
                .setFareName(
                    LocalizedString()
                        .setDefaultValue(
                            TranslatedString()
                                .setLanguage("en-US")
                                .setValue("Fare name")
                        )
                )
        )
}
