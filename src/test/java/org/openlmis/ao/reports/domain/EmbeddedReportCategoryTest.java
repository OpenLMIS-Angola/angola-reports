package org.openlmis.ao.reports.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openlmis.ao.reports.dto.EmbeddedReportCategoryDto;
import org.openlmis.ao.testutils.EmbeddedReportCategoryDataBuilder;

public class EmbeddedReportCategoryTest {

  @Test
  public void shouldCreateNewInstance() {
    EmbeddedReportCategory embeddedReportCategory =
        new EmbeddedReportCategoryDataBuilder().build();
    EmbeddedReportCategoryDto importer =
        EmbeddedReportCategoryDto.newInstance(embeddedReportCategory);

    EmbeddedReportCategory newInstance = EmbeddedReportCategory.newInstance(importer);

    assertThat(importer.getName()).isEqualTo(newInstance.getName());
  }

  @Test
  public void shouldExportData() {
    EmbeddedReportCategory embeddedReportCategory =
        new EmbeddedReportCategoryDataBuilder().build();
    EmbeddedReportCategoryDto dto = new EmbeddedReportCategoryDto();

    embeddedReportCategory.export(dto);

    assertThat(dto.getName()).isEqualTo(embeddedReportCategory.getName());
  }

}
