package worldcupprognostication

import scala.util.Random

object scratchpad {

  private val random = Random

  case class Team(
                   name: String,
                   rating: Long
                 )


  case class Group(
                    teams: Seq[Team],
                    name: String,
                    upcomingRounds: Seq[Round],
                    roundHistory: Seq[RoundResult]
                  )

  type Round = Set[Fixture]
  type RoundResult = Set[MatchResult]


  case class Fixture(
                      homeTeam: Team,
                      awayTeam: Team,
                    )

  sealed trait Result

  case object HomeWin extends Result

  case object Draw extends Result

  case object HomeLoss extends Result


  def inverseResult(result: Result): Result = {
    result match {
      case HomeWin => HomeLoss
      case Draw => Draw
      case HomeLoss => HomeWin
    }
  }

  case class MatchResult(
                          fixture: Fixture,
                          result: Result
                        )

  case class GroupStanding(
                            team: Team,
                            wins: Int,
                            draws: Int,
                            losses: Int,
                            points: Int
                          )

  def homeBeatsAwayProbability(homeTeam: Team, awayTeam: Team): Double = {
    val ratingDiff: Double = awayTeam.rating - homeTeam.rating
    val x: Double = ratingDiff / 400
    val y: Double = scala.math.pow(10, x) + 1
    val prob: Double = 1 / y
    prob
  }

  def newRating(team: Team, result: Result, winProbability: Double): Team = {
    val winParameter = result match {
      case HomeWin => 1.0
      case Draw => 0.5
      case HomeLoss => 0.0
    }
    val newRating = scala.math.round(team.rating + 60 * (winParameter - winProbability))
    team.copy(rating = newRating)
  }

  def getGroupStanding(group: Group): Seq[GroupStanding] = {
    group.teams.map { team =>
      val homeResults = group.roundHistory.flatten.filter(result => result.fixture.homeTeam.name == team.name)
      val awayResults = group.roundHistory.flatten.filter(result => result.fixture.awayTeam.name == team.name)
      val wins = homeResults.count(_.result == HomeWin) + awayResults.count(_.result == HomeLoss)
      val draws = homeResults.count(_.result == Draw)
      val losses = homeResults.count(_.result == HomeLoss) + awayResults.count(_.result == HomeWin)
      val points = (wins * 3) + draws
      GroupStanding(
        team,
        wins,
        draws,
        losses,
        points
      )
    }
  }

  def getTeamInPosition(position: Int, groupStandings: Seq[GroupStanding]): Team = {
    val orderedTeams = groupStandings.sortBy(-_.points)
    orderedTeams(position).team
  }

  def playGroupRound(groups: Seq[Group]): Seq[Group] = {
    groups.map { group =>
      val roundToPlay = group.upcomingRounds.head
      val roundResult = roundToPlay.map(playFixture)
      val teamsWithNewRatings = roundResult.flatMap{ result =>
        Seq(result.fixture.homeTeam, result.fixture.awayTeam)
      }.toSeq
      group.copy(
        upcomingRounds = group.upcomingRounds.tail,
        roundHistory = group.roundHistory.appended(roundResult),
        teams = teamsWithNewRatings
      )
    }
  }

  def playFixture(fixture: Fixture): MatchResult = {
    val probHomeBeatsAway = homeBeatsAwayProbability(fixture.homeTeam, fixture.awayTeam)
    val randomDouble = random.nextDouble
    if (randomDouble < probHomeBeatsAway) {
      val newTeamRatings = adjustRatings(fixture, HomeWin)
      MatchResult(
        fixture.copy(newTeamRatings._1, newTeamRatings._2),
        HomeWin
      )
    } else if (randomDouble == probHomeBeatsAway) {
      val newTeamRatings = adjustRatings(fixture, Draw)
      MatchResult(
        fixture.copy(newTeamRatings._1, newTeamRatings._2),
        Draw
      )
    } else {
      val newTeamRatings = adjustRatings(fixture, HomeLoss)
      MatchResult(
        fixture.copy(newTeamRatings._1, newTeamRatings._2),
        HomeLoss
      )
    }
  }

  def adjustRatings(fixture: Fixture, result: Result): (Team, Team) = {
    val probHomeBeatsAway = homeBeatsAwayProbability(fixture.homeTeam, fixture.awayTeam)
    val probAwayBeatsHome = homeBeatsAwayProbability(fixture.awayTeam, fixture.homeTeam)
    (
      newRating(fixture.homeTeam, result, probHomeBeatsAway),
      newRating(fixture.awayTeam, inverseResult(result), probAwayBeatsHome)
    )
  }

  def getWinner(matchResult: MatchResult): Team = {
    matchResult.result match {
      case HomeWin => matchResult.fixture.homeTeam
      case Draw => matchResult.fixture.homeTeam
      case HomeLoss => matchResult.fixture.awayTeam
    }
  }

}
