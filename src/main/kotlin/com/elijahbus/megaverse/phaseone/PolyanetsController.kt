package com.elijahbus.megaverse.phaseone

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/polyanets")
class PolyanetsController(private val polyanetsService: PolyanetsService) {

    @GetMapping
    suspend fun newPolyanets(): Any? {
        return polyanetsService.plot()
    }

    @GetMapping("/delete")
    suspend fun deletePolyanets(): Any? {
        return polyanetsService.delete()
    }
}
