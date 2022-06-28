package de.tfriem.tsukuru.games;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tfriem.tsukuru.games.dao.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
