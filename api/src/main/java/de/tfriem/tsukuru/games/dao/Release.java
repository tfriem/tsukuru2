package de.tfriem.tsukuru.games.dao;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "release", schema = "games")
@Getter
@Setter
@NoArgsConstructor
public class Release {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "release_generator")
  @SequenceGenerator(name = "release_generator", sequenceName = "games.release_id_seq", allocationSize = 50)
  @Column(nullable = false)
  private Long id;

  @Column(name = "date")
  private LocalDateTime date;

  @OneToOne
  @JoinColumn(name = "platform_id", referencedColumnName = "id")
  private Platform platform;

  @Enumerated(EnumType.STRING)
  private Region region;

  public Release(LocalDateTime date, Platform platform, Region region) {
    this.date = date;
    this.platform = platform;
    this.region = region;
  }

  public enum Region {
    EUROPE, NORTH_AMERICA, AUSTRALIA, NEW_ZEALAND, JAPAN, CHINA, ASIA, WORLDWIDE, KOREA, BRAZIL;
  }
}
