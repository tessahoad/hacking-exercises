module ComplexNumbers exposing
    ( Complex
    , abs
    , add
    , conjugate
    , div
    , exp
    , fromPair
    , fromReal
    , imaginary
    , mul
    , real
    , sub
    )


type Complex
    = ComplexNumber Float Float


fromPair : ( Float, Float ) -> Complex
fromPair pair =
    ComplexNumber (Tuple.first pair) (Tuple.second pair)


fromReal : Float -> Complex
fromReal realNumber =
    ComplexNumber realNumber 0


real : Complex -> Float
real z =
    case z of
        ComplexNumber realPart _ ->
            realPart


imaginary : Complex -> Float
imaginary z =
    case z of
        ComplexNumber _ imaginaryPart ->
            imaginaryPart


conjugate : Complex -> Complex
conjugate z =
    case z of
        ComplexNumber realPart imaginaryPart ->
            ComplexNumber realPart (-1 * imaginaryPart)


abs : Complex -> Float
abs z =
    case z of
        ComplexNumber realPart imaginaryPart ->
            sqrt (realPart ^ 2 + imaginaryPart ^ 2)


add : Complex -> Complex -> Complex
add z1 z2 =
    let
        z1Real =
            case z1 of
                ComplexNumber realPart _ ->
                    realPart

        z2Real =
            case z2 of
                ComplexNumber realPart _ ->
                    realPart

        z1Imaginary =
            case z1 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart

        z2Imaginary =
            case z2 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart
    in
    ComplexNumber (z1Real + z2Real) (z1Imaginary + z2Imaginary)


sub : Complex -> Complex -> Complex
sub z1 z2 =
    let
        z1Real =
            case z1 of
                ComplexNumber realPart _ ->
                    realPart

        z2Real =
            case z2 of
                ComplexNumber realPart _ ->
                    realPart

        z1Imaginary =
            case z1 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart

        z2Imaginary =
            case z2 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart
    in
    ComplexNumber (z1Real - z2Real) (z1Imaginary - z2Imaginary)


mul : Complex -> Complex -> Complex
mul z1 z2 =
    let
        z1Real =
            case z1 of
                ComplexNumber realPart _ ->
                    realPart

        z2Real =
            case z2 of
                ComplexNumber realPart _ ->
                    realPart

        z1Imaginary =
            case z1 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart

        z2Imaginary =
            case z2 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart
    in
    ComplexNumber ((z1Real * z2Real) - (z1Imaginary * z2Imaginary)) ((z1Imaginary * z2Real) + (z2Imaginary * z1Real))


div : Complex -> Complex -> Complex
div z1 z2 =
    let
        a =
            case z1 of
                ComplexNumber realPart _ ->
                    realPart

        c =
            case z2 of
                ComplexNumber realPart _ ->
                    realPart

        b =
            case z1 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart

        d =
            case z2 of
                ComplexNumber _ imaginaryPart ->
                    imaginaryPart

        dividend =
            c^2 + d^2
    in
    ComplexNumber (((a * c) + (b * d)) / dividend) (((b * c) - (d * a)) / dividend)


exp : Complex -> Complex
exp z =
    case z of
        ComplexNumber realPart imaginaryPart ->
            ComplexNumber (e ^ realPart * cos imaginaryPart) (sin imaginaryPart)
