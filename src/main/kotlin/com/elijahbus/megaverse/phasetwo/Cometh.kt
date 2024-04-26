package com.elijahbus.megaverse.phasetwo

import com.elijahbus.megaverse.common.AstralObject

data class Cometh(
    override val row: Int,
    override val column: Int,
    val direction: String,
    val candidateId: String
) : AstralObject

