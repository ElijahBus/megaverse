package com.elijahbus.megaverse.phaseone

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient

@RestController
@RequestMapping("/api/polyanets")
class PolyanetsController(private val polyanetsService: PolyanetsService) {

    @GetMapping
    fun newPolyanets(): Any? {
        return polyanetsService.plot()
    }

    @GetMapping("/delete")
    fun deletePolyanets(): Any? {
        return polyanetsService.delete()
    }
}
