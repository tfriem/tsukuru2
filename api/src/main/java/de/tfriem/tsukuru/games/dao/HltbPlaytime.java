package de.tfriem.tsukuru.games.dao;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HltbPlaytime {
  public static final HltbPlaytime ZERO = new HltbPlaytime(Category.ZERO, Category.ZERO, Category.ZERO);

  @Embedded
  @AttributeOverride(name = "average", column = @Column(name = "hltb_main_average"))
  @AttributeOverride(name = "median", column = @Column(name = "hltb_main_median"))
  @AttributeOverride(name = "rushed", column = @Column(name = "hltb_main_rushed"))
  @AttributeOverride(name = "leisure", column = @Column(name = "hltb_main_leisure"))
  private Category main;

  @Embedded
  @AttributeOverride(name = "average", column = @Column(name = "hltb_extra_average"))
  @AttributeOverride(name = "median", column = @Column(name = "hltb_extra_median"))
  @AttributeOverride(name = "rushed", column = @Column(name = "hltb_extra_rushed"))
  @AttributeOverride(name = "leisure", column = @Column(name = "hltb_extra_leisure"))
  private Category extra;

  @Embedded
  @AttributeOverride(name = "average", column = @Column(name = "hltb_complete_average"))
  @AttributeOverride(name = "median", column = @Column(name = "hltb_complete_median"))
  @AttributeOverride(name = "rushed", column = @Column(name = "hltb_complete_rushed"))
  @AttributeOverride(name = "leisure", column = @Column(name = "hltb_complete_leisure"))
  private Category complete;

  @Embeddable
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Category {
    public static final Category ZERO = new Category(0, 0, 0, 0);

    @ColumnDefault("0")
    private long average;

    @ColumnDefault("0")
    private long median;

    @ColumnDefault("0")
    private long rushed;

    @ColumnDefault("0")
    private long leisure;
  }
}
