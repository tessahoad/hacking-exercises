package texasholdem.partone.userinterface

import texasholdem.partone.model.Game

trait UserInterface {
  def display(table: Game): String
}

class ConsoleUI extends UserInterface {
  def display(table: Game): String = {
    val toDisplay = ""

    println(table.getFlop())
    println(table.getTurn())
    println(table.getRiver())

//    print('U+1F0A1'.toChar)

    toDisplay
  }


}
