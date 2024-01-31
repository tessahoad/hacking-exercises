# Texas Hold 'em - part one #

We're going to code up dealing Texas Hold 'em hands. There's a summary of the game on
[Wikipedia](https://en.wikipedia.org/wiki/Texas_hold_%27em) if you're not familiar.

The game uses a standard deck of 52 cards. The game proceeds as follows:

- The dealer deals two cards to each player (called hole cards), which no-one else sees.
- The dealer then discards a card from the deck and deals three cards out on to the table which everyone sees -- this is
  called the flop.
- The dealer then discards a card from the deck and deals a fourth card to the table -- this is called the turn.
- The dealer then discards a card from the deck and deals a fifth card to the table -- this is called the river.

There's a whole bunch of additional complexity to do with betting and raising, but we'll ignore that for now. We're just
going to handle dealing the cards to the players and the table as described above.

The discarding of a card between deals doesn't change any odds but is traditional so let's keep that in.

# Texas Hold 'em - part two #

This doesn't really require the previous week's work, but if you've got it then you can just slot it in there. This week
we're going to try to classify poker hands. Poker has the following types of hands:

- Royal flush: A-K-Q-J-10, all the same suit
- Straight flush: Five cards in a sequence, all in the same suit
- Four of a kind: All four cards of the same rank
- Full house: Three of a kind with a pair
- Flush: Any five cards of the same suit, but not in a sequence
- Straight: Five cards in a sequence, but not of the same suit
- Three of a kind: Three cards of the same rank
- Two pair: Two different pairs
- Pair: Two cards of the same rank
- High Card: When you haven't made any of the hands above, the highest card wins

Hands higher in the list beat hands lower down.

If hands have the same 'type' (e.g., a pair vs a pair) then draws are resolved as follows:

- If the hands are of different rank in the 'type' of the hand then the higher ranked hand wins. For example,
  10-10-4-6-9 beats 9-9-4-6-8: a pair of tens beats a pair of nines, and the other cards (4-6-9 vs 4-6-8) are irrelevant
  because they aren't part of the pair. As another example, 10-9-8-7-6 beats 9-8-7-6-5: a straight where the high card
  is a ten is higher than a straight where the high card is a nine.

- If the hands have equal rank in the 'type' (for example, if both hands have a pair of twos) then the cards which do
  not take part in determining the 'type' of the hand (called kickers) are checked. The hand with the highest kicker
  wins, and if they are equal then the next highest kickers are compared. If the next highest kickers are equal then the
  next are compared, and so on. For example, 10-10-4-6-8 beats 10-10-3-6-8: both have a pair of tens, but the former has
  8-6-4 kickers which are higher ranked than 8-6-3 kickers.

There's a good summary on [Wikipedia](https://en.wikipedia.org/wiki/List_of_poker_hands) if you need more details and
examples.

Write a function that takes two hands of five cards and tells you which hand is better. If you can handle draws and
kickers then that's even better.
