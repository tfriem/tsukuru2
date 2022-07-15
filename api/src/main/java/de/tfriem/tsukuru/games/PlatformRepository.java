package de.tfriem.tsukuru.games;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tfriem.tsukuru.games.dao.Platform;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
  public Optional<Platform> findByName(String name);
}
