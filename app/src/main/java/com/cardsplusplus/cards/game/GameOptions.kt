package com.cardsplusplus.cards.game

enum class GameMode{
    FREE_PLAY, WAR, MAKAO
}

class GameOptions(var nrOfPlayers: Int,
                  var nrOfDecks: Int,
                  var timePerPlayer: Int,
                  var initialCardsPerPlayer: Int,
                  var gameMode: GameMode = GameMode.FREE_PLAY) {
}