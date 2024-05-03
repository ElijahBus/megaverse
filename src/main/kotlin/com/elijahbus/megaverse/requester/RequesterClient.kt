package com.elijahbus.megaverse.requester

import com.elijahbus.megaverse.common.AstralObject
import org.springframework.http.HttpMethod

interface RequesterClient {

    val baseUrl: String

    val rollbackOnFailure: Boolean

    val shouldRetry: Boolean

    suspend fun buildRequest(method: HttpMethod, uri: String, body: AstralObject?): Any

    fun rollback(uri: String, body: AstralObject?): Any
}
