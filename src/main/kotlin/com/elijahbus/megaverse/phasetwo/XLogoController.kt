package com.elijahbus.megaverse.phasetwo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@RestController
@RequestMapping("/api/xlogo")
class XLogoController(val xLogoService: XLogoService) {

    @GetMapping
    fun plotAstralObject(): String {
        val execution = xLogoService.runMap();

        return if (!execution.isDone) {
            "Processing..."
        } else if (execution.isCancelled) {
            "The Process has been cancelled."
        } else if (execution.isDone) {
            // Communicate to the client and inform when the process is done ...
            execution.get()
        } else {
            "You might need to retry..."
        }

    }
}

