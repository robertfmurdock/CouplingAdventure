package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.HeroClass.*
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.People.*

private val peopleToPresentation = mapOf(
        Human to "Human",
        Dwarf to "Dwarf",
        Elf to "Elf"
)

fun toPresentationString(people: People) = peopleToPresentation.getValue(people)

fun String.toPeople(): People = peopleToPresentation.entries.find { (_, value) ->
    value == this
}!!.key

private val heroClassToPresentation = mapOf(
        Warrior to "Warrior",
        Mage to "Mage",
        Rogue to "Rogue",
        Priest to "Priest"
)

fun toPresentationString(heroClass: HeroClass) = heroClassToPresentation.getValue(heroClass)

fun String.toHeroClass(): HeroClass = heroClassToPresentation.entries.find { (_, value) ->
    value == this
}!!.key