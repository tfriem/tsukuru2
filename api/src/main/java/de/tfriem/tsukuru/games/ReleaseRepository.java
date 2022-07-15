package de.tfriem.tsukuru.games;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tfriem.tsukuru.games.dao.Release;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
  public Optional<Release> findByIgdbId(long id);
}
