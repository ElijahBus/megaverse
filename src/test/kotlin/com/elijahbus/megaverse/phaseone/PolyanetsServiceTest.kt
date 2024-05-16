package com.elijahbus.megaverse.phaseone

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.http.HttpMethod

class PolyanetsServiceTest {

    private val polyanetsRestClient: PolyanetsRestClient = mockk(relaxed = true)

    private val polyanetsService = PolyanetsService(polyanetsRestClient, "candidate-id")

    @Test
    fun `should return a map of axis one data with the same key and value`() {

        val axis1 = polyanetsService.axis1

        assertTrue(axis1.isNotEmpty())
        axis1.forEach { poly -> assertTrue(poly.key == poly.value) }

    }

    @Test
    fun `should plot the axis data on the remote map`() {
        coEvery { polyanetsRestClient.buildRequest(any(), any(), any()) } returns Unit

        runBlocking { polyanetsService.plot() }

        coVerify(atLeast = 2) { polyanetsRestClient.buildRequest(any(), any(), any()) }
    }
}
