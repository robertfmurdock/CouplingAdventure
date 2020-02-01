package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.Character
import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.Player

private var characterNumber = 1
fun stubCharacter() = Character(
        Player("Stubby $characterNumber"), People.Elf, HeroClass.Mage, HeroClass.Mage.characterSheet
).also { characterNumber++ }