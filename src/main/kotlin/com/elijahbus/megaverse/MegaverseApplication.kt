package com.elijahbus.megaverse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication (scanBasePackages = ["com.elijahbus.megaverse"])
@PropertySource(value = ["classpath:application.yml"], factory = AppPropertiesSourceFactory::class)
@EnableAsync
class MegaverseApplication

fun main(args: Array<String>) {
    runApplication<MegaverseApplication>(*args)
}


