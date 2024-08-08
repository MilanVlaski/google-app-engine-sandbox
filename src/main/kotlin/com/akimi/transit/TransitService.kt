package com.akimi.transit

import com.google.api.services.walletobjects.model.TransitClass
import com.google.api.services.walletobjects.model.TransitClassListResponse
import com.google.api.services.walletobjects.model.TransitObject

interface TransitService {

    fun createObject(issuerId: String, className: String, passengerName: String, email: String): TransitObject?
    fun generateJWTToken(transitObject: TransitObject): String?
    fun staticObject(objectSuffix: String, passengerName: String, className: String, issuerId: String): TransitObject
    fun patchObject(objectId: String, patchedBody: TransitObject): String
}