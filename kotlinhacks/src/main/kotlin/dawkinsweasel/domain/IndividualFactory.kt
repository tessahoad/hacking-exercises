package dawkinsweasel.domain

interface IndividualFactory {
    fun idealIndividual(): Individual

    fun randomIndividual(): Individual
}