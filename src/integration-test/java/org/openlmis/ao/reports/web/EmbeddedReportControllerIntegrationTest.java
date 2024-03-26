package org.openlmis.ao.reports.web;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.nidi.ramltester.junit.RamlMatchers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.openlmis.ao.reports.domain.EmbeddedReport;
import org.openlmis.ao.reports.dto.EmbeddedReportDto;
import org.openlmis.ao.reports.repository.EmbeddedReportRepository;
import org.openlmis.ao.testutils.EmbeddedReportDataBuilder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SuppressWarnings("PMD.TooManyMethods")
public class EmbeddedReportControllerIntegrationTest extends BaseWebIntegrationTest {

  private static final String RESOURCE_URL = "/api/reports/embeddedReports";
  private static final String ID_URL = RESOURCE_URL + "/{id}";
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String ENABLED = "enabled";

  @MockBean
  private EmbeddedReportRepository embeddedReportRepository;

  @Before
  public void setUp() {
    mockUserAuthenticated();
    doNothing().when(permissionService).canViewEmbeddedReports();
  }

  @Test
  public void shouldGetExistentEmbeddedReport() {
    // given
    EmbeddedReport embeddedReport = new EmbeddedReportDataBuilder().build();
    given(embeddedReportRepository.findOne(embeddedReport.getId())).willReturn(embeddedReport);

    // when
    EmbeddedReportDto result = restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, embeddedReport.getId())
        .when()
        .get(ID_URL)
        .then()
        .statusCode(200)
        .extract().as(EmbeddedReportDto.class);

    // then
    assertEquals(embeddedReport.getId(), result.getId());
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

  @Test
  public void shouldNotGetNonExistentEmbeddedReport() {
    // given
    given(embeddedReportRepository.findOne(anyUuid())).willReturn(null);

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
  public void shouldGetAllEmbeddedReports() {
    // given
    EmbeddedReport report1 = new EmbeddedReportDataBuilder().build();
    EmbeddedReport report2 = new EmbeddedReportDataBuilder().build();
    List<EmbeddedReport> reports = Arrays.asList(report1, report2);

    Pageable pageable = new PageRequest(0, reports.size());
    Page<EmbeddedReport> reportsPage = new PageImpl<>(reports, pageable, reports.size());

    given(embeddedReportRepository.findAll(pageable)).willReturn(reportsPage);

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
  public void shouldDeleteExistentEmbeddedReport() {
    // given
    EmbeddedReport embeddedReport = new EmbeddedReportDataBuilder().build();
    given(embeddedReportRepository.findOne(embeddedReport.getId())).willReturn(embeddedReport);

    // when
    restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, embeddedReport.getId())
        .when()
        .delete(ID_URL)
        .then()
        .statusCode(204);

    // then
    verify(embeddedReportRepository, atLeastOnce()).delete(eq(embeddedReport));
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

  @Test
  public void shouldNotDeleteNonExistentEmbeddedReport() {
    // given
    given(embeddedReportRepository.findOne(anyUuid())).willReturn(null);

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

  @Test
  public void shouldUpdateEmbeddedReport() {
    // given
    EmbeddedReport embeddedReport = new EmbeddedReportDataBuilder().disabled().build();
    EmbeddedReportDto embeddedReportDto = EmbeddedReportDto.newInstance(embeddedReport);
    embeddedReportDto.setName("new_name");
    given(embeddedReportRepository.findById(embeddedReport.getId()))
        .willReturn(Optional.of(embeddedReport));

    // when
    restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, embeddedReport.getId())
        .body(embeddedReportDto)
        .when()
        .put(ID_URL)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(NAME, is(embeddedReportDto.getName()))
        .body(ENABLED, is(embeddedReportDto.isEnabled()));

    // then
    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

  @Test
  public void shouldCreateEmbeddedReportIfEmbeddedReportDoesNotExistForUpdatePEndpoint()
      throws IOException {
    // given
    EmbeddedReportDto embeddedReportDto = EmbeddedReportDto
        .newInstance(new EmbeddedReportDataBuilder().build());
    String erJson = "{\"url\":\"" + embeddedReportDto.getUrl() + "\",\"name\":\""
        + embeddedReportDto.getName() + "\",\"category\":\""
        + embeddedReportDto.getCategory() + "\"}";
    Map<String, String> widgetMap = new ObjectMapper().readValue(erJson,
        new TypeReference<Map<String, String>>() {
        });
    UUID pathId = UUID.randomUUID();
    given(embeddedReportRepository.findById(pathId)).willReturn(Optional.empty());

    // when
    restAssured.given()
        .queryParam(ACCESS_TOKEN, getToken())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .pathParam(ID, pathId)
        .body(widgetMap)
        .when()
        .put(ID_URL)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(NAME, is(embeddedReportDto.getName()));

    assertThat(RAML_ASSERT_MESSAGE, restAssured.getLastReport(), RamlMatchers.hasNoViolations());
  }

}
