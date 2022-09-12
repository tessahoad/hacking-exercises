package dawkinsweasel.domain

interface Individual {
    val bestScore: Int

    fun score(): Int

    fun mutate(): Individual

    fun niceToString(): String
}