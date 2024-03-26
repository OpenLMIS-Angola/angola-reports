package org.openlmis.ao.reports.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import guru.nidi.ramltester.junit.RamlMatchers;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;
import org.openlmis.ao.reports.dto.EmbeddedReportCategoryDto;
import org.openlmis.ao.reports.repository.EmbeddedReportCategoryRepository;
import org.openlmis.ao.testutils.EmbeddedReportCategoryDataBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

@SuppressWarnings("PMD.TooManyMethods")
public class EmbeddedReportCategoryControllerIntegrationTest extends BaseWebIntegrationTest {

  private static final String RESOURCE_URL = "/api/reports/embeddedReportCategories";
  private static final String ID_URL = RESOURCE_URL + "/{id}";
  public static final String ID = "id";

  @MockBean
  private EmbeddedReportCategoryRepository embeddedReportCategoryRepository;

  @Before
  public void setUp() {
    mockUserAuthenticated();
    doNothing().when(permissionService).canViewEmbeddedReportsCategory();
  }

  @Test
  public void shouldGetExistentEmbeddedReportCategory() {
    // given
    EmbeddedReportCategory embeddedReportCategory =
        new EmbeddedReportCategoryDataBuilder().build();
    given(embeddedReportCategoryRepository.findOne(embeddedReportCategory.getId()))
        .willReturn(embeddedReportCategory);

    // when
    EmbeddedReportCategoryDto result = restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, embeddedReportCategory.getId())
        .when()
        .get(ID_URL)
        .then()
        .statusCode(200)
        .extract().as(EmbeddedReportCategoryDto.class);

    // then
    assertEquals(embeddedReportCategory.getId(), result.getId());
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

  @Test
  public void shouldNotGetNonExistentEmbeddedReportCategory() {
    // given
    given(embeddedReportCategoryRepository.findOne(anyUuid())).willReturn(null);

    // when
    restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, UUID.randomUUID())
        .when()
        .get(ID_URL)
        .then()
        .statusCode(404);

    // then
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

  @Test
  public void shouldGetAllEmbeddedReportCategories() {
    // given
    EmbeddedReportCategory category1 = new EmbeddedReportCategoryDataBuilder().build();
    EmbeddedReportCategory category2 = new EmbeddedReportCategoryDataBuilder().build();
    List<EmbeddedReportCategory> categories = Arrays.asList(category1, category2);

    Pageable pageable = new PageRequest(0, categories.size());
    Page<EmbeddedReportCategory> categoryPage = new PageImpl<>(categories, pageable,
        categories.size());

    given(embeddedReportCategoryRepository.findAll(pageable)).willReturn(categoryPage);

    // when
    restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .queryParam("page", pageable.getPageNumber())
        .queryParam("size", pageable.getPageSize())
        .when()
        .get(RESOURCE_URL)
        .then()
        .statusCode(200);

    // then
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

  @Test
  public void shouldDeleteExistentEmbeddedReportCategory() {
    // given
    EmbeddedReportCategory embeddedReportCategory =
        new EmbeddedReportCategoryDataBuilder().build();
    given(embeddedReportCategoryRepository.findOne(embeddedReportCategory.getId()))
        .willReturn(embeddedReportCategory);

    // when
    restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, embeddedReportCategory.getId())
        .when()
        .delete(ID_URL)
        .then()
        .statusCode(204);

    // then
    verify(embeddedReportCategoryRepository, atLeastOnce()).delete(eq(embeddedReportCategory));
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

  @Test
  public void shouldNotDeleteNonExistentEmbeddedReportCategory() {
    // given
    given(embeddedReportCategoryRepository.findOne(anyUuid())).willReturn(null);

    // when
    restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, UUID.randomUUID())
        .when()
        .delete(ID_URL)
        .then()
        .statusCode(404);

    // then
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

}
