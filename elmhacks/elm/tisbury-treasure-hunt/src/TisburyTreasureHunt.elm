module TisburyTreasureHunt exposing (..)

type alias TreasureLocation = (Int, Char)
type alias Treasure = String
type alias PlaceLocation = (Char, Int)
type alias Place = String

placeLocationToTreasureLocation : PlaceLocation -> TreasureLocation
placeLocationToTreasureLocation (letter, number) =
    Tuple.pair number letter

treasureLocationMatchesPlaceLocation : PlaceLocation -> TreasureLocation -> Bool
treasureLocationMatchesPlaceLocation (placeLetter, placeNumber) (treasureNumber, treasureLetter) =
    placeLetter == treasureLetter && placeNumber == treasureNumber


countPlaceTreasures : ( Place, PlaceLocation ) -> List ( Treasure, TreasureLocation ) -> Int
countPlaceTreasures (_, placeLocation) treasures =
    let
        treasureLocations = List.map Tuple.second treasures
        treasuresAtLocation = List.filter (treasureLocationMatchesPlaceLocation placeLocation) treasureLocations
    in
    List.length treasuresAtLocation


specialCaseSwapPossible : ( String, TreasureLocation ) -> ( String, PlaceLocation ) -> ( String, TreasureLocation ) -> Bool
specialCaseSwapPossible ( foundTreasure, _ ) ( place, _ ) ( desiredTreasure, _ ) =
    case (foundTreasure, place, desiredTreasure) of
        ("Brass Spyglass", "Abandoned Lighthouse", _) -> True
        ("Amethyst Octopus", "Stormy Breakwater", "Crystal Crab") -> True
        ("Amethyst Octopus", "Stormy Breakwater", "Glass Starfish") -> True
        ("Vintage Pirate Hat", "Harbor Managers Office", "Model Ship in Large Bottle") -> True
        ("Vintage Pirate Hat", "Harbor Managers Office", "Antique Glass Fishnet Float") -> True
        _ -> False
