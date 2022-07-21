module RolePlayingGame exposing (Player, castSpell, introduce, revive)


type alias Player =
    { name : Maybe String
    , level : Int
    , health : Int
    , mana : Maybe Int
    }


introduce : Player -> String
introduce { name } =
    case name of
        Just someName ->
            someName

        Nothing ->
            "Mighty Magician"


revive : Player -> Maybe Player
revive player =
    if player.health == 0 && player.level < 10 then
        Just { player | health = 100 }

    else if player.health == 0 && player.level >= 10 then
        Just { player | health = 100, mana = Just 100 }

    else
        Nothing


castSpell : Int -> Player -> ( Player, Int )
castSpell manaCost player =
    case player.mana of
        Nothing ->
            let
                updatedHealth =
                    if manaCost > player.health then
                        0
                    else
                        player.health - manaCost
            in
            ( { player | health = updatedHealth }, 0 )

        Just playerMana ->
            if playerMana < manaCost then
                ( player, 0 )

            else
                ( { player | mana = Just (playerMana - manaCost) }, manaCost * 2 )
