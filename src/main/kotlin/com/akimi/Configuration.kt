package com.akimi

import com.google.api.ResourceProto.resource
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Primary
import io.micronaut.core.io.ResourceLoader
import java.io.FileInputStream
import java.io.IOException
import java.net.URL
import java.util.*

@Factory
class Configuration(val loader: ResourceLoader) {


    @Bean
    fun fileCredentials(): Credentials {
        val resource = loader.getResource("classpath:test-credentials.json")
            .orElseThrow {IOException("COULD NOT LOAD test-credentials.json")}
        return Credentials(FileInputStream(resource.path).readAllBytes())
    }

    @Primary
    @Bean
    fun secretCredentials(): Credentials {
        return Credentials(
            GetSecret.accessSecret(
                "eth-sbx",
                "GOOGLE_APPLICATION_CREDENTIALS"
            )!!
        )
    }

}
