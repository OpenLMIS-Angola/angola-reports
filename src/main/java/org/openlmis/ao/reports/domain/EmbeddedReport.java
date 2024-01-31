/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org.
 */

package org.openlmis.ao.reports.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "embedded_reports")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmbeddedReport extends BaseEntity {

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  private String name;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  private String url;

  @Column(columnDefinition = TEXT_COLUMN_DEFINITION)
  private String category;

  /**
   * Create a new instance of Embedded report based on data from {@link EmbeddedReport.Importer}
   *
   * @param importer instance of {@link EmbeddedReport.Importer}
   * @return new instance of embedded report.
   */
  public static EmbeddedReport newInstance(EmbeddedReport.Importer importer) {
    EmbeddedReport embeddedReport = new EmbeddedReport();

    embeddedReport.setId(importer.getId());
    embeddedReport.setName(importer.getName());
    embeddedReport.setUrl(importer.getUrl());
    embeddedReport.setCategory(importer.getCategory());

    return embeddedReport;
  }

  /**
   * Copy values of attributes into new or updated Embedded Report.
   *
   * @param embeddedReport Embedded report with new values.
   */
  public void updateFrom(EmbeddedReport embeddedReport) {
    this.name = embeddedReport.getName();
    this.url = embeddedReport.getUrl();
    this.category = embeddedReport.getCategory();
  }

  /**
   * Export this object to the specified exporter (DTO).
   *
   * @param exporter exporter to export to
   */
  public void export(EmbeddedReport.Exporter exporter) {
    exporter.setId(id);
    exporter.setName(name);
    exporter.setUrl(url);
    exporter.setCategory(category);
  }

  public interface Exporter {

    void setId(UUID id);

    void setName(String name);

    void setUrl(String url);

    void setCategory(String category);

  }

  public interface Importer {

    UUID getId();

    String getName();

    String getUrl();

    String getCategory();

  }

}
