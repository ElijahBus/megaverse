package com.elijahbus.megaverse.phasetwo

import com.elijahbus.megaverse.common.AstralObject
import com.elijahbus.megaverse.requester.RequesterClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class XLogoRestClient : RequesterClient {

    private final val restClient: RestClient = RestClient.create(baseUrl)

    private final val webClient : WebClient = WebClient.create(baseUrl)

    private final val logger: Logger = LoggerFactory.getLogger(XLogoRestClient::class.java)

    final override val baseUrl: String
        get() = "https://megaverse-store.onrender.com/api"

    override val rollbackOnFailure: Boolean
        get() = true

    override val shouldRetry: Boolean
        get() = true

    override suspend fun buildRequest(method: HttpMethod, uri: String, body: AstralObject?): Any {
        try {
            logger.info("Sending the request to Megaverse API service...")

            val request = this.webClient.method(method)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

            if (body != null) request.bodyValue(body)

            return request.retrieve().awaitBody()
        } catch (ex: Exception) {
            if (ex !is HttpClientErrorException && shouldRetry) {
                logger.info("Retrying the request...")
                return buildRequest(method, uri, body)
            }

            if (rollbackOnFailure) {
                logger.info("Rolling back the sent data...")
                rollback(uri, body)
            }

            throw ex
        }
    }


    override fun rollback(uri: String, body: AstralObject?): ResponseEntity<String> {
        try {
            val request =  this.restClient.method(HttpMethod.DELETE)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)

            if (body != null) request.body(body)

            return request.retrieve().toEntity(String::class.java)
        } catch (ex: Exception) {
            throw ex
        }
    }
}

