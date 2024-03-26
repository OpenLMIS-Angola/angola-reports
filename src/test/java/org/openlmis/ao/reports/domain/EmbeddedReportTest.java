package org.openlmis.ao.reports.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.openlmis.ao.reports.dto.EmbeddedReportDto;
import org.openlmis.ao.testutils.EmbeddedReportDataBuilder;

public class EmbeddedReportTest {

  @Test
  public void shouldCreateNewInstance() {
    EmbeddedReport embeddedReport = new EmbeddedReportDataBuilder().build();
    EmbeddedReportDto importer = EmbeddedReportDto.newInstance(embeddedReport);

    EmbeddedReport newInstance = EmbeddedReport.newInstance(importer);

    assertThat(importer.getName()).isEqualTo(newInstance.getName());
    assertThat(importer.getUrl()).isEqualTo(newInstance.getUrl());
    assertThat(importer.getCategory()).isEqualTo(newInstance.getCategory());
    assertThat(importer.isEnabled()).isEqualTo(newInstance.isEnabled());
  }

  @Test
  public void shouldExportData() {
    EmbeddedReport embeddedReport = new EmbeddedReportDataBuilder().build();
    EmbeddedReportDto dto = new EmbeddedReportDto();

    embeddedReport.export(dto);

    assertThat(dto.getName()).isEqualTo(embeddedReport.getName());
    assertThat(dto.getUrl()).isEqualTo(embeddedReport.getUrl());
    assertThat(dto.getCategory()).isEqualTo(embeddedReport.getCategory());
    assertThat(dto.isEnabled()).isEqualTo(embeddedReport.isEnabled());
  }

}
