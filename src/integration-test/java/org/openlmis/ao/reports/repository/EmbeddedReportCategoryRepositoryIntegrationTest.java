package org.openlmis.ao.reports.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;
import org.openlmis.ao.testutils.EmbeddedReportCategoryDataBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class EmbeddedReportCategoryRepositoryIntegrationTest extends
    BaseCrudRepositoryIntegrationTest<EmbeddedReportCategory> {

  private static final String NAME = "EmbeddedReportCategoryIntegrationTest";

  @Autowired
  private EmbeddedReportCategoryRepository embeddedReportCategoryRepository;

  @Override
  EmbeddedReportCategoryRepository getRepository() {
    return this.embeddedReportCategoryRepository;
  }

  @Override
  protected EmbeddedReportCategory generateInstance() {
    return new EmbeddedReportCategoryDataBuilder().withName(NAME).buildAsNew();
  }

  @Test
  public void shouldFindEmbeddedReportCategoryByName() {
    embeddedReportCategoryRepository.save(generateInstance());

    EmbeddedReportCategory found = embeddedReportCategoryRepository.findByName(NAME);

    assertThat(found.getName(), is(NAME));
  }

}
