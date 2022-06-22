package worldcupprognostication

import worldcupprognostication.scratchpad.{Fixture, Group, Team, getGroupStanding, getTeamInPosition, getWinner, playFixture, playGroupRound}

import scala.io.Source

object Application {
  def readInstructionsFile(instructionFileLocation: String): Seq[String] = {
    Source.fromResource(instructionFileLocation).getLines().toSeq
  }

  def main(args: Array[String]): Unit = {
    val groupsWithFixtures = parseGroupsWithRatingsFiles
    val initMap = groupsWithFixtures.flatMap{ case (_, group) =>
      group.teams
    }.map(team => team.name -> 0L).toMap

    val finalResults = (1 to 1000000).foldLeft(initMap){ case (teamsToWins, i) =>

      val iterator = Iterator.iterate(groupsWithFixtures.values.toSeq)(playGroupRound).take(4)
      val endOfGroupStage = iterator.toSeq.last

      val finalGroupStandings = endOfGroupStage.map(group => group.name -> getGroupStanding(group)).toMap

      val winnerA = getTeamInPosition(0, finalGroupStandings("A"))
      val runnerUpB = getTeamInPosition(1, finalGroupStandings("B"))

      val winnerB = getTeamInPosition(0, finalGroupStandings("B"))
      val runnerUpA = getTeamInPosition(1, finalGroupStandings("A"))

      val winnerC = getTeamInPosition(0, finalGroupStandings("C"))
      val runnerUpD = getTeamInPosition(1, finalGroupStandings("D"))

      val winnerD = getTeamInPosition(0, finalGroupStandings("D"))
      val runnerUpC = getTeamInPosition(1, finalGroupStandings("C"))

      val quarterFinal1 = Fixture(winnerA, runnerUpB)
      val quarterFinal2 = Fixture(winnerB, runnerUpA)
      val quarterFinal3 = Fixture(winnerC, runnerUpD)
      val quarterFinal4 = Fixture(winnerD, runnerUpC)

      val quarterFinal1Result = playFixture(quarterFinal1)
      val quarterFinal2Result = playFixture(quarterFinal2)
      val quarterFinal3Result = playFixture(quarterFinal3)
      val quarterFinal4Result = playFixture(quarterFinal4)

      val semiFinal1 = Fixture(getWinner(quarterFinal3Result), getWinner(quarterFinal1Result));
      val semiFinal2 = Fixture(getWinner(quarterFinal4Result), getWinner(quarterFinal2Result));

      val semiFinal1Result = playFixture(semiFinal1)
      val semiFinal2Result = playFixture(semiFinal2)

      val finalMatch = Fixture(getWinner(semiFinal1Result), getWinner(semiFinal2Result))

      val finalResult = playFixture(finalMatch)
      val winner = getWinner(finalResult)

      val numberOfWins = teamsToWins.get(winner.name) match {
        case Some(value) => value + 1
        case None => 1
      }
      val updatedTeamsToWins = teamsToWins + (winner.name -> numberOfWins)
      if (i % 10000 == 0) {
        println(s"after $i tournaments")
        println(teamsToWins)
      }
      updatedTeamsToWins
    }
    println(finalResults)
  }

  private def parseGroupsWithRatingsFiles = {
    val teams = readInstructionsFile("worldcupprognostication/ratings.txt").map { input =>
      val teamName = input.split(",").head
      val teamRating = input.split(",").last.toLong
      Team(teamName, teamRating)
    }

    val groupsNoFixtures = readInstructionsFile("worldcupprognostication/groups.txt").map { input =>
      val teamNames = input.split(",").tail
      val groupTeams = teamNames.flatMap(teamName => teams.find(team => team.name == teamName)).toSeq
      val groupName = input.split(",").head
      groupName -> Group(groupTeams, groupName, Seq(), Seq())
    }.toMap

    val groupsWithFixtures = groupsNoFixtures.map { case (name, group) =>
      val firstTeam = group.teams.head
      val secondTeam = group.teams(1)
      val thirdTeam = group.teams(2)
      val fourthTeam = group.teams(3)
      name -> group.copy(upcomingRounds = Seq(
        Set(
          Fixture(firstTeam, secondTeam),
          Fixture(thirdTeam, fourthTeam)
        ),
        Set(
          Fixture(firstTeam, thirdTeam),
          Fixture(secondTeam, fourthTeam)
        ),
        Set(
          Fixture(fourthTeam, firstTeam),
          Fixture(secondTeam, thirdTeam)
        )))
    }
    groupsWithFixtures
  }
}
