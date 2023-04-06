package wordle.model

import wordle.Word
import java.net.URL


interface WordSource {
    fun listWords(length: Int): List<Word>
}

class InternetWordSource : WordSource {
    override fun listWords(length: Int): List<Word> {
        return URL("http://www.mieliestronk.com/corncob_caps.txt").readText()
            .split("\r\n")
            .filter { it.length == 5 }
    }

}