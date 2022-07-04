package de.tfriem.tsukuru.games.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import de.tfriem.tsukuru.Postgres;
import de.tfriem.tsukuru.games.GameRepository;
import de.tfriem.tsukuru.games.GenreRepository;
import de.tfriem.tsukuru.games.PlatformRepository;
import de.tfriem.tsukuru.games.ReleaseRepository;
import de.tfriem.tsukuru.games.dao.HltbPlaytime.Category;
import de.tfriem.tsukuru.games.dao.Release.Region;

@SpringBootTest
@Testcontainers
public class GamesDaosTest {
  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private PlatformRepository platformRepository;

  @Autowired
  private ReleaseRepository releaseRepository;

  @Container
  private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
      Postgres.dockerImageName)
      .withDatabaseName("tsukuru").withUsername("TestUser")
      .withPassword("TestPassword").withExposedPorts(5432);

  @DynamicPropertySource
  public static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
  }

  @Test
  @Transactional
  void Write_game_only_with_name_to_database() {
    // Setup
    final String name = "TestGame";

    // Execute
    final Game game = new Game();
    game.setName(name);
    gameRepository.save(game);

    // Verify
    final var allGames = gameRepository.findAll();

    assertEquals(1, allGames.size());

    final Game dbGame = allGames.get(0);

    assertEquals(name, dbGame.getName());
  }

  @Test
  @Transactional
  void Write_game_with_genres_platforms_and_releases_to_database() {
    // Setup
    assertTrue(postgreSQLContainer.isRunning());

    final String name = "TestGame";
    final String summary = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
    final long steamId = 1234;
    final String steamUrl = "https://www.example.com";
    final long igdbId = 4567;
    final String igdbUrl = "https://www.example2.com";
    final String igdbCoverId = "TestCover";
    final double igdbRating = 1.23;
    final long igdbRatingCount = 42;

    final Genre genre1 = new Genre("TestGenre1");
    final Genre genre2 = new Genre("TestGenre2");
    final Set<Genre> genres = Set.of(genre1, genre2);

    final Platform platform1 = new Platform("TestPlatform1", "p1");
    final Platform platform2 = new Platform("TestPlatform2", "p2");
    final Set<Platform> platforms = Set.of(platform1, platform2);

    final Category main = new Category(1, 2, 3, 4);
    final Category extra = new Category(5, 6, 7, 8);
    final Category complete = new Category(9, 10, 11, 12);
    final HltbPlaytime playtime = new HltbPlaytime(main, extra, complete);

    final Release release1 = new Release(LocalDateTime.now(), platform1, Region.EUROPE);
    final Release release2 = new Release(LocalDateTime.now(), platform2, Region.NORTH_AMERICA);
    final Set<Release> releases = Set.of(release1, release2);

    // Execute
    final Game newGame = new Game();
    newGame.setName(name);
    newGame.setSummary(summary);
    newGame.setSteamId(steamId);
    newGame.setSteamUrl(steamUrl);
    newGame.setIgdbId(igdbId);
    newGame.setIgdbUrl(igdbUrl);
    newGame.setIgdbCoverId(igdbCoverId);
    newGame.setIgdbRating(igdbRating);
    newGame.setIgdbRatingCount(igdbRatingCount);
    newGame.setGenres(genres);
    newGame.setPlatforms(platforms);
    newGame.setHltbPlaytime(playtime);
    newGame.setReleases(releases);

    genreRepository.saveAll(genres);
    platformRepository.saveAll(platforms);
    releaseRepository.saveAll(releases);
    gameRepository.save(newGame);

    // Verify
    final var allGames = gameRepository.findAll();

    assertEquals(1, allGames.size());

    final Game dbGame = allGames.get(0);

    assertEquals(name, dbGame.getName());
    assertEquals(summary, dbGame.getSummary());
    assertEquals(steamId, dbGame.getSteamId());
    assertEquals(steamUrl, dbGame.getSteamUrl());
    assertEquals(igdbId, dbGame.getIgdbId());
    assertEquals(igdbUrl, dbGame.getIgdbUrl());
    assertEquals(igdbCoverId, dbGame.getIgdbCoverId());
    assertEquals(igdbRating, dbGame.getIgdbRating());
    assertEquals(igdbRatingCount, dbGame.getIgdbRatingCount());

    assertEquals(genres, dbGame.getGenres());
    assertEquals(platforms, dbGame.getPlatforms());

    assertEquals(1, dbGame.getHltbPlaytime().getMain().getAverage());
    assertEquals(2, dbGame.getHltbPlaytime().getMain().getMedian());
    assertEquals(3, dbGame.getHltbPlaytime().getMain().getRushed());
    assertEquals(4, dbGame.getHltbPlaytime().getMain().getLeisure());

    assertEquals(5, dbGame.getHltbPlaytime().getExtra().getAverage());
    assertEquals(6, dbGame.getHltbPlaytime().getExtra().getMedian());
    assertEquals(7, dbGame.getHltbPlaytime().getExtra().getRushed());
    assertEquals(8, dbGame.getHltbPlaytime().getExtra().getLeisure());

    assertEquals(9, dbGame.getHltbPlaytime().getComplete().getAverage());
    assertEquals(10, dbGame.getHltbPlaytime().getComplete().getMedian());
    assertEquals(11, dbGame.getHltbPlaytime().getComplete().getRushed());
    assertEquals(12, dbGame.getHltbPlaytime().getComplete().getLeisure());

    assertEquals(releases, dbGame.getReleases());
  }
}
