package de.tfriem.tsukuru.games;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tfriem.tsukuru.games.dao.Game;
import de.tfriem.tsukuru.games.dao.Genre;
import de.tfriem.tsukuru.games.dao.Platform;
import de.tfriem.tsukuru.games.dao.Release;
import de.tfriem.tsukuru.games.igdb.IgdbService;
import proto.ExternalGame;
import proto.ExternalGameCategoryEnum;
import proto.Website;
import proto.WebsiteCategoryEnum;

@Service
public class GameServiceImpl implements GameService {
  @Autowired
  private IgdbService igdbService;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private PlatformRepository platformRepository;

  @Autowired
  private ReleaseRepository releaseRepository;

  @Override
  public Game addGame(IgdbId id) {
    final var igdbGame = igdbService.fetchGameByIgdbId(id.getId());

    final var game = new Game();
    game.setName(igdbGame.getName());
    game.setSummary(igdbGame.getSummary());
    game.setSteamId(getSteamIdFromIgdbGame(igdbGame));
    game.setSteamUrl(getSteamUrlFromIgdbGame(igdbGame));
    game.setIgdbId(igdbGame.getId());
    game.setIgdbUrl(igdbGame.getUrl());
    game.setIgdbCoverId(igdbGame.getCover().getImageId());
    game.setIgdbRating(igdbGame.getTotalRating());
    game.setIgdbRatingCount(igdbGame.getTotalRatingCount());

    // TODO: Get Hltb playtime

    game.setGenres(igdbGame.getGenresList().stream().map(this::getOrCreateGenre).collect(Collectors.toSet()));
    game.setPlatforms(igdbGame.getPlatformsList().stream().map(this::getOrCreatePlatform).collect(Collectors.toSet()));
    game.setReleases(igdbGame.getReleaseDatesList().stream().map(this::getOrCreateRelease).collect(Collectors.toSet()));

    gameRepository.save(game);

    return game;
  }

  @Override
  public void updateIgdbData(GameId id) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateHltbData(GameId id) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteGame(GameId id) {
    // TODO Auto-generated method stub

  }

  private String getSteamIdFromIgdbGame(proto.Game game) {
    return game.getExternalGamesList().stream()
        .filter(e -> ExternalGameCategoryEnum.EXTERNALGAME_STEAM.equals(e.getCategory()))
        .findFirst().map(ExternalGame::getUid).orElse(null);
  }

  private String getSteamUrlFromIgdbGame(proto.Game game) {
    return game.getWebsitesList().stream()
        .filter(site -> site.getCategory() == WebsiteCategoryEnum.WEBSITE_STEAM)
        .findFirst().map(Website::getUrl).orElse(null);
  }

  private Genre getOrCreateGenre(proto.Genre protoGenre) {
    final Optional<Genre> optionalGenre = genreRepository.findByName(protoGenre.getName());

    if (optionalGenre.isPresent()) {
      return optionalGenre.get();
    }

    final Genre genre = new Genre(protoGenre.getName());
    return genreRepository.save(genre);
  }

  private Platform getOrCreatePlatform(proto.Platform protoPlatform) {
    final Optional<Platform> optionalPlatform = platformRepository.findByName(protoPlatform.getName());

    if (optionalPlatform.isPresent()) {
      return optionalPlatform.get();
    }

    final Platform platform = new Platform(protoPlatform.getName(), protoPlatform.getAbbreviation());
    return platformRepository.save(platform);
  }

  private Release getOrCreateRelease(proto.ReleaseDate protoRelease) {
    final Optional<Release> optionalRelease = releaseRepository.findByIgdbId(protoRelease.getId());

    if (optionalRelease.isPresent()) {
      return optionalRelease.get();
    }

    final var protoDate = protoRelease.getDate();
    final var seconds = protoDate.getSeconds();
    final var nanos = protoDate.getNanos();

    final var region = Release.Region.valueOf(protoRelease.getRegion().toString());

    final Release release = new Release(Instant.ofEpochSecond(seconds, nanos),
        this.getOrCreatePlatform(protoRelease.getPlatform()),
        region);
    return releaseRepository.save(release);
  }

}
