package cheatinghangman

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.mockito.MockitoSugar
import cheatinghangman.model.{CheatingExecutioner, ComputerPlayer, Executioner, GameFinished, GameState, HonestExecutioner, InProgress, Lost, Player, SimpleDictionary, Won}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when

class HangmanGameTest extends AnyFlatSpec with MockitoSugar {

  behavior of "HangmanGame"

  it should "playRound should return game with GameFinished status if previous round status is Win or Lost" in new SimpleDictionaryFixture with MockPlayerFixture with MockExecutionerFixture {
    val state = GameState("test", Set(), Set(), Won)
    val expectedStatus = GameFinished
    val actualGame = new HangmanGame(player, executioner, dictionary.getWords(), Seq(state)).playRound()

    assert(expectedStatus.equals(actualGame.turns.last.status))
  }

  it should "playRound should return game with Lost status if incorrect guesses totals 11" in new SimpleDictionaryFixture with MockExecutionerFixture with MockPlayerFixture {
    when(player.guessLetter(any(), any(), any())).thenReturn("z")

    val state = GameState("test", Set('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'), Set(), InProgress)
    val expectedStatus = Lost
    val actualGame = new HangmanGame(player, executioner, dictionary.getWords(), Seq(state)).playRound()

    assert(expectedStatus.equals(actualGame.turns.last.status))
  }

  it should "playRound should return game with Win status if all letters have been revealed in guess word" in new SimpleDictionaryFixture with MockExecutionerFixture with MockPlayerFixture {
    val correctWord = "word"
    when(executioner.pickWord(any(), any())).thenReturn(correctWord)
    when(player.guessLetter(any(), any(), any())).thenReturn("w")

    val state = GameState(correctWord, Set('o', 'r', 'd'), Set(), InProgress)
    val expectedStatus = Won
    val actualGame = new HangmanGame(player, executioner, dictionary.getWords(), Seq(state)).playRound()

    assert(expectedStatus.equals(actualGame.turns.last.status))
  }

  trait SimpleDictionaryFixture {
    val dictionary = new SimpleDictionary()
  }

  trait CheatingExecutionerFixture {
    val executioner = new CheatingExecutioner()
  }

  trait MockExecutionerFixture {
    val executioner = mock[Executioner]
  }

  trait MockPlayerFixture {
    val player = mock[Player]
  }

  trait HonestExecutionerFixture {
    val executioner = new HonestExecutioner()
  }

  trait ComputerPlayerFixture {
    val player = new ComputerPlayer()
  }
}
