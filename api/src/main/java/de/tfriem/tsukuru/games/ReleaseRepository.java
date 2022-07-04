package de.tfriem.tsukuru.games;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tfriem.tsukuru.games.dao.Release;

public interface ReleaseRepository extends JpaRepository<Release, Long> {

}
