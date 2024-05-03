package com.elijahbus.megaverse.phasetwo

import com.elijahbus.megaverse.common.AstralObject

data class Soloon(
    override val row: Int,
    override val column: Int,
    val color: String,
) : AstralObject
