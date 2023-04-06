package wordle.userinterfaces

import wordle.WordleGame
import wordle.model.LetterState
import wordle.model.LetterState.*
import wordle.model.ValidationError


interface UserInterface {

    val BLACK: String
        get() = "\u001b[30m"

    /** Foreground color for ANSI red
     * @group color-red
     */
    val RED: String
        get() = "\u001b[31m"

    /** Foreground color for ANSI green
     * @group color-green
     */
    val GREEN: String
        get() = "\u001b[32m"

    /** Foreground color for ANSI yellow
     * @group color-yellow
     */
    val YELLOW: String
        get() = "\u001b[33m"

    /** Foreground color for ANSI blue
     * @group color-blue
     */
    val BLUE: String
        get() = "\u001b[34m"

    /** Foreground color for ANSI magenta
     * @group color-magenta
     */
    val MAGENTA: String
        get() = "\u001b[35m"

    /** Foreground color for ANSI cyan
     * @group color-cyan
     */
    val CYAN: String
        get() = "\u001b[36m"

    /** Foreground color for ANSI white
     * @group color-white
     */
    val WHITE: String
        get() = "\u001b[37m"

    /** Background color for ANSI black
     * @group color-black
     */
    val BLACK_B: String
        get() = "\u001b[40m"

    /** Background color for ANSI red
     * @group color-red
     */
    val RED_B: String
        get() = "\u001b[41m"

    /** Background color for ANSI green
     * @group color-green
     */
    val GREEN_B: String
        get() = "\u001b[42m"

    /** Background color for ANSI yellow
     * @group color-yellow
     */
    val YELLOW_B: String
        get() = "\u001b[43m"

    /** Background color for ANSI blue
     * @group color-blue
     */
    val BLUE_B: String
        get() = "\u001b[44m"

    /** Background color for ANSI magenta
     * @group color-magenta
     */
    val MAGENTA_B: String
        get() = "\u001b[45m"

    /** Background color for ANSI cyan
     * @group color-cyan
     */
    val CYAN_B: String
        get() = "\u001b[46m"

    /** Background color for ANSI white
     * @group color-white
     */
    val WHITE_B: String
        get() = "\u001b[47m"

    /** Reset ANSI styles
     * @group style-control
     */
    val RESET: String
        get() = "\u001b[0m"

    /** ANSI bold
     * @group style-control
     */
    val BOLD: String
        get() = "\u001b[1m"

    /** ANSI underlines
     * @group style-control
     */
    val UNDERLINED: String
        get() = "\u001b[4m"

    /** ANSI blink
     * @group style-control
     */
    val BLINK: String
        get() = "\u001b[5m"

    /** ANSI reversed
     * @group style-control
     */
    val REVERSED: String
        get() = "\u001b[7m"

    /** ANSI invisible
     * @group style-control
     */
    val INVISIBLE: String
        get() = "\u001b[8m"

    fun displayState(wordleState: WordleGame.WordleState): UserInterface

    fun displayError(errors: List<ValidationError>): UserInterface

    fun askForUserInput(): UserInterface

    fun displayResultMessage(wordleState: WordleGame.WordleState): UserInterface
}

class ConsoleUserInterface : UserInterface {
    override fun displayState(wordleState: WordleGame.WordleState): UserInterface {
        wordleState.userGuesses.forEach { guess ->
            guess.wordState.forEach {
                when (it.second) {
                    CORRECT_PLACE -> print("$RESET$GREEN${it.first}$RESET")
                    INCORRECT_PLACE -> print("$RESET$YELLOW${it.first}$RESET")
                    WRONG_GUESS -> print("$RESET$RED${it.first}$RESET")
                    UNKNOWN -> print("$RESET$CYAN${it.first}$RESET")
                }
            }
            println()
        }
        return this
    }

    override fun displayError(errors: List<ValidationError>): UserInterface {
        errors.forEach { println(it) }
        return this
    }

    override fun askForUserInput(): UserInterface {
        println("Please guess a word!")
        return this
    }

    override fun displayResultMessage(wordleState: WordleGame.WordleState): UserInterface {
        displayState(wordleState)
        when (wordleState.gameState) {
            WordleGame.GameState.IN_PROGRESS -> println("Game in progress")
            WordleGame.GameState.WON -> println("Winner Winner! The word was ${wordleState.correctWord}")
            WordleGame.GameState.LOST -> println("You are a big loser \uD83D\uDCA9 The word was ${wordleState.correctWord}")

        }
        return this
    }

}