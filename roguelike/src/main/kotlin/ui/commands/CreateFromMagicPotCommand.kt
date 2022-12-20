package ui.commands

import entity.models.User

class CreateFromMagicPotCommand(private val user: User) : Command {
    override fun execute() {
        user.createFromMagicPot()
    }
}
