package org.openlmis.ao.reports.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import org.junit.Test;
import org.openlmis.ao.reports.domain.EmbeddedReport;
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;
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

  @Autowired
  private EmbeddedReportCategoryRepository embeddedReportCategoryRepository;

  @Override
  EmbeddedReportRepository getRepository() {
    return this.embeddedReportRepository;
  }

  @Override
  protected EmbeddedReport generateInstance() {
    EmbeddedReportCategory category = embeddedReportCategoryRepository
        .save(new EmbeddedReportCategoryDataBuilder().buildAsNew());

    return new EmbeddedReportDataBuilder()
        .withName(NAME)
        .withCategory(category)
        .buildAsNew();
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
    EmbeddedReportCategory defaultCategory = embeddedReportCategoryRepository.save(
        new EmbeddedReportCategoryDataBuilder().withName(DEFAULT_CATEGORY).build()
    );
    EmbeddedReportCategory uniqueCategory = embeddedReportCategoryRepository.save(
        new EmbeddedReportCategoryDataBuilder().withName(UNIQUE_CATEGORY).build()
    );
    EmbeddedReport matchingReport1 = new EmbeddedReportDataBuilder()
        .withCategory(defaultCategory)
        .build();
    EmbeddedReport matchingReport2 = new EmbeddedReportDataBuilder()
        .withCategory(defaultCategory)
        .build();
    EmbeddedReport notMatchingReport = new EmbeddedReportDataBuilder()
        .withCategory(uniqueCategory)
        .build();

    embeddedReportRepository.save(matchingReport1);
    embeddedReportRepository.save(matchingReport2);
    embeddedReportRepository.save(notMatchingReport);

    List<EmbeddedReport> found =
        embeddedReportRepository.findAllByCategoryName(DEFAULT_CATEGORY, pageable).getContent();

    assertThat(found.size(), is(2));
  }

}
