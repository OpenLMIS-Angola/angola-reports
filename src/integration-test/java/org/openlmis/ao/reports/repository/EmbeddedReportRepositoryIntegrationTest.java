package org.openlmis.ao.reports.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import org.junit.Test;
import org.openlmis.ao.reports.domain.EmbeddedReport;
import org.openlmis.ao.testutils.EmbeddedReportCategoryDataBuilder;
import org.openlmis.ao.testutils.EmbeddedReportDataBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class EmbeddedReportRepositoryIntegrationTest extends
    BaseCrudRepositoryIntegrationTest<EmbeddedReport> {

  private static final String NAME = "EmbeddedReportIntegrationTest";
  private static final String DEFAULT_CATEGORY = "Default-category";
  private static final String UNIQUE_CATEGORY = "Unique-category";

  @Autowired
  private EmbeddedReportRepository embeddedReportRepository;

  @Override
  EmbeddedReportRepository getRepository() {
    return this.embeddedReportRepository;
  }

  @Override
  protected EmbeddedReport generateInstance() {
    return new EmbeddedReportDataBuilder().withName(NAME).buildAsNew();
  }

  @Test
  public void shouldFindEmbeddedReportByName() {
    embeddedReportRepository.save(generateInstance());

    EmbeddedReport found = embeddedReportRepository.findByName(NAME);

    assertThat(found.getName(), is(NAME));
  }

  @Test
  public void shouldFindAllEmbeddedReportsByCategory() {
    Pageable pageable = new PageRequest(0, 2);
    EmbeddedReport matchingReport1 = new EmbeddedReportDataBuilder()
        .withCategory(new EmbeddedReportCategoryDataBuilder().withName(DEFAULT_CATEGORY).build())
        .build();
    EmbeddedReport matchingReport2 = new EmbeddedReportDataBuilder()
        .withCategory(new EmbeddedReportCategoryDataBuilder().withName(DEFAULT_CATEGORY).build())
        .build();
    EmbeddedReport notMatchingReport = new EmbeddedReportDataBuilder()
        .withCategory(new EmbeddedReportCategoryDataBuilder().withName(UNIQUE_CATEGORY).build())
        .build();

    embeddedReportRepository.save(matchingReport1);
    embeddedReportRepository.save(matchingReport2);
    embeddedReportRepository.save(notMatchingReport);

    List<EmbeddedReport> found =
        embeddedReportRepository.findAllByCategoryName(DEFAULT_CATEGORY, pageable).getContent();

    assertThat(found.size(), is(2));
  }

}
