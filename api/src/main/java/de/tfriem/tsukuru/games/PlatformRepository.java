package de.tfriem.tsukuru.games;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tfriem.tsukuru.games.dao.Platform;

public interface PlatformRepository extends JpaRepository<Platform, Long> {

}
