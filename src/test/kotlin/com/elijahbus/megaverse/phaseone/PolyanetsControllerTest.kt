package com.elijahbus.megaverse.phaseone

import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
class PolyanetsControllerTest {
    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    var polyanetsRestClient: PolyanetsRestClient = mockk()

    @Test
    fun `should return ok when sending new polyanets`() {
        

    }
}
