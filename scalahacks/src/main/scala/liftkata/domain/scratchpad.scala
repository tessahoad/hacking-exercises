package liftkata.domain

object scratchpad {

  type LiftId = String
  type FloorId = String
  type BuildingId = String

  sealed trait DoorState

  case object Open extends DoorState
  case object Closed extends DoorState


  case class Lift(liftId: LiftId, doorState: DoorState) {

    def closeDoors(): Unit = {

    }
    def goToFloor(floorId: FloorId): Unit = {

    }
  }

  case class Floor(floorId: FloorId) {
    def callLift(): Unit = {

    }
  }

  case class Building(buildingId: BuildingId, floors: Seq[Floor], lift: Lift) {

  }


}
