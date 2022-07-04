package de.tfriem.tsukuru.games.dao;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "genre", schema = "games")
@Getter
@Setter
@NoArgsConstructor
public class Genre {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_generator")
  @SequenceGenerator(name = "genre_generator", sequenceName = "games.genre_id_seq", allocationSize = 50)
  @Column(nullable = false)
  private Long id;

  @NonNull
  @Column(nullable = false)
  private String name;

  @ManyToMany(mappedBy = "genres")
  private Set<Game> games;

  public Genre(@NonNull String name) {
    this.name = name;
  }
}
