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
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddedReportCategoryDto implements EmbeddedReportCategory.Importer,
    EmbeddedReportCategory.Exporter {

  private UUID id;

  @NotNull(message = "Name needs to be provided")
  @NotEmpty(message = "Name cannot be empty")
  private String name;

  /**
   * Create new instance of EmbeddedReportCategoryDto based on given {@link EmbeddedReportCategory}
   *
   * @param embeddedReportCategory instance of Embedded Report Category
   * @return new instance of EmbeddedReportCategoryDto.
   */
  public static EmbeddedReportCategoryDto newInstance(
      EmbeddedReportCategory embeddedReportCategory) {
    if (embeddedReportCategory == null) {
      return null;
    }

    EmbeddedReportCategoryDto embeddedReportCategoryDto = new EmbeddedReportCategoryDto();
    embeddedReportCategory.export(embeddedReportCategoryDto);

    return embeddedReportCategoryDto;
  }

  /**
   * Create new list of EmbeddedReportCategoryDto based on given list
   * of {@link EmbeddedReportCategory}
   *
   * @param embeddedReportCategories list of {@link EmbeddedReportCategory}
   * @return new list of EmbeddedReportCategoryDto.
   */
  public static List<EmbeddedReportCategoryDto> newInstance(
      Iterable<EmbeddedReportCategory> embeddedReportCategories) {
    return StreamSupport.stream(embeddedReportCategories.spliterator(), false)
        .map(EmbeddedReportCategoryDto::newInstance)
        .collect(Collectors.toList());
  }
}
