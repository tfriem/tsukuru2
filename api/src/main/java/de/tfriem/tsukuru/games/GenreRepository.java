package de.tfriem.tsukuru.games;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tfriem.tsukuru.games.dao.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
  Optional<Genre> findByName(String name);
}
