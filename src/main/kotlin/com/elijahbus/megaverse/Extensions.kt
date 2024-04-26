package com.elijahbus.megaverse

import java.util.*
import javax.management.InvalidAttributeValueException

/**
 * Provided the String is composed with the astral object name and its color,
 * the function extracts the color from the string to be used when plotting
 * astral object to the MAP.
 *
 * e.g. SOLOON_WHITE => white
 */
fun String.extractColor(): String {
    val colors: List<String> = listOf("blue", "red", "purple", "white");

    return colors.find { color -> lowercase(Locale.getDefault()).contains(color) }
        ?: throw InvalidAttributeValueException("Invalid Color Attribute: $this")
}

/**
 * Provided the String is composed with the astral object name and its direction,
 * the function extracts the direction from the string to be used when plotting
 * astral object to the MAP.
 *
 * e.g. COMETH_UP => up
 */
fun String.extractDirection(): String {
    val directions: List<String> = listOf("up", "down", "right", "left")

    return directions.find { direction -> lowercase(Locale.getDefault()).contains(direction) }
        ?: throw InvalidAttributeValueException("Invalid Direction Attribute: $this")
}
