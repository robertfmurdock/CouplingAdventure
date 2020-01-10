package com.zegreatrob.couplingadventure.engine

enum class HeroClass(val characterSheet: CharacterSheet) {
    Warrior(CharacterSheet(
            strength = 9,
            intelligence = 1
    )),
    Mage(CharacterSheet(
            strength = 4,
            intelligence = 7
    )),
    Rogue(CharacterSheet(
            strength = 7,
            intelligence = 6
    )),
    Priest(CharacterSheet(
            strength = 2,
            intelligence = 9
    ))
}
