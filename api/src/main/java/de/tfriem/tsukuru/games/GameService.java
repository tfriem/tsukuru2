package de.tfriem.tsukuru.games;

import de.tfriem.tsukuru.games.dao.Game;

public interface GameService {
  Game addGame(IgdbId id);

  void updateIgdbData(GameId id);

  void updateHltbData(GameId id);

  void deleteGame(GameId id);
}
