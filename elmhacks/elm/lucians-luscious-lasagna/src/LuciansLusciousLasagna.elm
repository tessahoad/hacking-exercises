module LuciansLusciousLasagna exposing (elapsedTimeInMinutes, expectedMinutesInOven, preparationTimeInMinutes)

-- TODO: define the expectedMinutesInOven constant
-- TODO: define the preparationTimeInMinutes function
-- TODO: define the elapsedTimeInMinutes function

expectedMinutesInOven = 40
preparationTimeInMinutes numberOfLayers = numberOfLayers * 2
elapsedTimeInMinutes numberOfLayers timeInOven = (preparationTimeInMinutes numberOfLayers) + timeInOven