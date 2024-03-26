package org.openlmis.ao.testutils;

import java.util.UUID;
import org.apache.commons.lang.RandomStringUtils;
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;

public class EmbeddedReportCategoryDataBuilder {

  private final UUID id = UUID.randomUUID();
  private String name = RandomStringUtils.random(6);

  public EmbeddedReportCategoryDataBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Builds an instance of the {@link EmbeddedReportCategory} class with populated ID.
   *
   * @return the instance of {@link EmbeddedReportCategory} class
   */
  public EmbeddedReportCategory build() {
    EmbeddedReportCategory embeddedReportCategory = buildAsNew();
    embeddedReportCategory.setId(id);
    return embeddedReportCategory;
  }

  /**
   * Build an instance of the {@link EmbeddedReportCategory} class without ID field populated.
   *
   * @return the instance of {@link EmbeddedReportCategory} class
   */
  public EmbeddedReportCategory buildAsNew() {
    return new EmbeddedReportCategory(name);
  }

}
