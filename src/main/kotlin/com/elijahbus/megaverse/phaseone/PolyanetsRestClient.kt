package com.elijahbus.megaverse.phaseone

import com.elijahbus.megaverse.common.AstralObject
import com.elijahbus.megaverse.requester.RequesterClient
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient

class PolyanetsRestClient : RequesterClient {

    private val restClient = RestClient.create(baseUrl)

    override val baseUrl: String
        get() = "https://coding-challenge-eosin.vercel.app/api"

    override val rollbackOnFailure: Boolean
        get() = false

    override val shouldRetry: Boolean
        get() = false

    override suspend fun buildRequest(method: HttpMethod, uri: String, body: AstralObject?): Any {
        try {
            val request = restClient.method(method)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

            if (body != null) request.body(body)

            return request.retrieve().toBodilessEntity()
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun rollback(uri: String, body: AstralObject?): Any {
        return false
    }
}
