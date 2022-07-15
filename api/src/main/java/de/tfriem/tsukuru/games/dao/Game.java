package de.tfriem.tsukuru.games.dao;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "game", schema = "games")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_generator")
  @SequenceGenerator(name = "game_generator", sequenceName = "games.game_id_seq", allocationSize = 50)
  @Column(nullable = false)
  private Long id;

  @NonNull
  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String summary;

  @Column(name = "steam_id")
  private String steamId;

  @Column(name = "steam_url")
  private String steamUrl;

  @Column(name = "igdb_id")
  private long igdbId;

  @Column(name = "igdb_url")
  private String igdbUrl;

  @Column(name = "igdb_cover_id")
  private String igdbCoverId;

  @Column(name = "igdb_rating")
  private double igdbRating;

  @Column(name = "igdb_rating_count")
  private long igdbRatingCount;

  @Embedded
  private HltbPlaytime hltbPlaytime;

  @NonNull
  @ManyToMany
  @JoinTable(name = "game_genre", schema = "games", joinColumns = @JoinColumn(name = "GAME_ID", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "id"))
  private Set<Genre> genres;

  @NonNull
  @ManyToMany
  @JoinTable(name = "game_platform", schema = "games", joinColumns = @JoinColumn(name = "GAME_ID", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "PLATFORM_ID", referencedColumnName = "id"))
  private Set<Platform> platforms;

  @NonNull
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "game_id")
  private Set<Release> releases;
}
