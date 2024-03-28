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
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.ao.reports.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.openlmis.ao.reports.domain.EmbeddedReport;
import org.openlmis.ao.reports.domain.EmbeddedReport.Exporter;
import org.openlmis.ao.reports.domain.EmbeddedReport.Importer;
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddedReportDto implements Importer, Exporter {

  private UUID id;

  @NotNull(message = "Name needs to be provided")
  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @NotNull(message = "URL needs to be provided")
  @NotEmpty(message = "URL cannot be empty")
  private String url;

  @NotNull(message = "Category needs to be provided")
  private EmbeddedReportCategoryDto category;

  private boolean enabled;

  /**
   * Create new instance of EmbeddedReportDto based on given {@link EmbeddedReport}
   *
   * @param embeddedReport instance of Embedded Report
   * @return new instance of EmbeddedReportDto.
   */
  public static EmbeddedReportDto newInstance(EmbeddedReport embeddedReport) {
    if (embeddedReport == null) {
      return null;
    }

    EmbeddedReportDto jasperTemplateDto = new EmbeddedReportDto();
    embeddedReport.export(jasperTemplateDto);

    return jasperTemplateDto;
  }

  /**
   * Create new list of EmbeddedReportDto based on given list of {@link EmbeddedReport}
   *
   * @param embeddedReports list of {@link EmbeddedReport}
   * @return new list of EmbeddedReportDto.
   */
  public static List<EmbeddedReportDto> newInstance(Iterable<EmbeddedReport> embeddedReports) {
    return StreamSupport.stream(embeddedReports.spliterator(), false)
        .map(EmbeddedReportDto::newInstance)
        .collect(Collectors.toList());
  }

  @Override
  public void setCategory(EmbeddedReportCategory category) {
    this.category = new EmbeddedReportCategoryDto();
    category.export(this.category);
  }
}
