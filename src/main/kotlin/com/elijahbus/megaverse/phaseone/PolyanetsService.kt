package com.elijahbus.megaverse.phaseone

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service

@Service
class PolyanetsService(
    private val polyanetsRestClient : PolyanetsRestClient,
    @Value("\${candidate.id}") private val candidateId: String
) {

    val axis1 = mapOf(
        2 to 2,
        3 to 3,
        4 to 4,
        5 to 5,
        6 to 6,
        7 to 7,
        8 to 8
    )
    val axis2 = mapOf(
        2 to 8,
        3 to 7,
        4 to 6,
        6 to 4,
        7 to 3,
        8 to 2
    )

    suspend fun plot(): Any? {
        axis1.forEach { poly ->
            polyanetsRestClient.buildRequest(HttpMethod.POST, "/polyanets", Polyanet(poly.key, poly.value, candidateId))
        }

        axis2.forEach { poly ->
            polyanetsRestClient.buildRequest(HttpMethod.POST, "/polyanets", Polyanet(poly.key, poly.value, candidateId))
        }

        return "OK"
    }

    suspend fun delete(): Any? {
        axis1.forEach { poly ->
            polyanetsRestClient.buildRequest(
                HttpMethod.DELETE,
                "/polyanets",
                Polyanet(poly.key, poly.value, candidateId)
            )
        }

        axis2.forEach { poly ->
            polyanetsRestClient.buildRequest(
                HttpMethod.DELETE,
                "/polyanets",
                Polyanet(poly.key, poly.value, candidateId)
            )
        }

        return "OK"
    }

}
