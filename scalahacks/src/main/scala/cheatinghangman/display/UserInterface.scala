package cheatinghangman.display

import cheatinghangman.model.GameState

trait UserInterface {
  def display(gameState: GameState): Unit
}
