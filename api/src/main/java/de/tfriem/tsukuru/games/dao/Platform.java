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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "platform", schema = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Platform {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platform_generator")
  @SequenceGenerator(name = "platform_generator", sequenceName = "games.platform_id_seq", allocationSize = 50)
  @Column(nullable = false)
  private Long id;

  @NonNull
  @Column(nullable = false)
  private String name;

  private String abbreviation;

  @ManyToMany(mappedBy = "platforms")
  private Set<Game> games;

  public Platform(@NonNull String name, @NonNull String abbreviation) {
    this.name = name;
    this.abbreviation = abbreviation;
  }
}
