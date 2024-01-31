package org.openlmis.ao.testutils;

import java.util.UUID;
import org.apache.commons.lang.RandomStringUtils;
import org.openlmis.ao.reports.domain.EmbeddedReport;

public class EmbeddedReportDataBuilder {

  private final UUID id = UUID.randomUUID();
  private String name = RandomStringUtils.random(6);
  private String url = "http://example.com";
  private String category = "default-category";

  public EmbeddedReportDataBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public EmbeddedReportDataBuilder withUrl(String url) {
    this.url = url;
    return this;
  }

  public EmbeddedReportDataBuilder withCategory(String category) {
    this.category = category;
    return this;
  }

  /**
   * Builds an instance of the {@link EmbeddedReport} class with populated ID.
   *
   * @return the instance of {@link EmbeddedReport} class
   */
  public EmbeddedReport build() {
    EmbeddedReport embeddedReport = buildAsNew();
    embeddedReport.setId(id);
    return embeddedReport;
  }

  /**
   * Build an instance of the {@link EmbeddedReport} class without ID field populated.
   *
   * @return the instance of {@link EmbeddedReport} class
   */
  public EmbeddedReport buildAsNew() {
    return new EmbeddedReport(
        name,
        url,
        category
    );
  }

}
