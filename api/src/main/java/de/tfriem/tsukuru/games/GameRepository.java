package de.tfriem.tsukuru.games;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tfriem.tsukuru.games.dao.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
