package de.tfriem.tsukuru.games.igdb;

import proto.Game;

public interface IgdbService {
  Game fetchGameByIgdbId(long igdbId);
}
