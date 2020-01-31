package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.HeroClass.*
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.People.*

fun toPresentationString(people: People) = when (people) {
    Human -> "Human"
    Dwarf -> "Dwarf"
    Elf -> "Elf"
}

fun toPresentationString(heroClass: HeroClass) = when (heroClass) {
    Warrior -> "Warrior"
    Mage -> "Mage"
    Rogue -> "Rogue"
    Priest -> "Priest"
}