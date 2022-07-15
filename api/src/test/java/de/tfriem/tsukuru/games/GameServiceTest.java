package de.tfriem.tsukuru.games;

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

@SpringBootTest
@Testcontainers
public class GameServiceTest {
  @Autowired
  private GameService gameService;

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
  void FetchGame() {
    final var game = gameService.addGame(new IgdbId(123));

    System.out.println(game);
  }
}
