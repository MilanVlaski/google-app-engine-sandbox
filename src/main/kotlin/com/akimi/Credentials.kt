package com.akimi

class Credentials(val accessSecret: ByteArray) {
    fun byteArray(): ByteArray {
        return accessSecret
    }
}