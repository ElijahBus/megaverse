package com.elijahbus.megaverse.phasetwo

import kotlinx.coroutines.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/xlogo")
class XLogoController(val xLogoService: XLogoService) {

    @GetMapping
    fun plotAstralObject(): String {
        CoroutineScope(Dispatchers.IO).launch {
            xLogoService.runMap()
        }

        return "OK"
    }
}

