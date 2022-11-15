package entity.models

import controls.UserEvent
import controls.UserEventListener
import engine.GameObject
import generator.Characteristics
import inventory.UserInventory

class User(
    gameObject: GameObject,
    characteristics: Characteristics,
) : EntityWithCharacteristics(gameObject, characteristics), UserEventListener {
    val inventory: UserInventory = UserInventory()

    override fun onEvent(event: UserEvent) {
        TODO("Not yet implemented")
    }
}