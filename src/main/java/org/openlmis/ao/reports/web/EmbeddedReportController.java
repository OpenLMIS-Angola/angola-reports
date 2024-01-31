package org.openlmis.ao.reports.web;

import static org.openlmis.ao.reports.i18n.EmbeddedReportsMessageKeys.ERROR_EMBEDDED_REPORT_NOT_FOUND;
import static org.openlmis.ao.reports.i18n.EmbeddedReportsMessageKeys.ERROR_EMBEDDED_REPORT_NAME_DUPLICATED;
import static org.openlmis.ao.reports.web.EmbeddedReportController.RESOURCE_PATH;

import java.util.UUID;
import org.apache.log4j.Logger;
import org.openlmis.ao.reports.domain.EmbeddedReport;
import org.openlmis.ao.reports.dto.EmbeddedReportDto;
import org.openlmis.ao.reports.exception.NotFoundMessageException;
import org.openlmis.ao.reports.exception.ValidationMessageException;
import org.openlmis.ao.reports.i18n.EmbeddedReportsMessageKeys;
import org.openlmis.ao.reports.repository.EmbeddedReportRepository;
import org.openlmis.ao.reports.service.PermissionService;
import org.openlmis.ao.utils.Message;
import org.openlmis.ao.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@Transactional
@RequestMapping(RESOURCE_PATH)
public class EmbeddedReportController extends BaseController {

  public static final String RESOURCE_PATH = "/api/reports/embeddedReports";

  private static final Logger LOGGER = Logger.getLogger(EmbeddedReportController.class);

  @Autowired
  private EmbeddedReportRepository embeddedReportRepository;

  @Autowired
  private PermissionService permissionService;

  /**
   * Get chosen embedded report.
   *
   * @param reportId UUID of embedded report we want to get
   * @return Embedded report.
   */
  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public EmbeddedReportDto getEmbeddedReport(@PathVariable("id") UUID reportId) {
    permissionService.canViewEmbeddedReports();
    EmbeddedReport embeddedReport = embeddedReportRepository.findOne(reportId);

    if (embeddedReport == null) {
      throw new NotFoundMessageException(
          EmbeddedReportsMessageKeys.ERROR_EMBEDDED_REPORT_NOT_FOUND);
    }

    return EmbeddedReportDto.newInstance(embeddedReport);
  }

  /**
   * Get page of all embedded reports. Filter result list by category parameter.
   *
   * @return All embedded reports matching certain criteria.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Page<EmbeddedReportDto> getAllEmbeddedReports(
      Pageable pageable, @RequestParam(required = false) String category) {
    permissionService.canViewEmbeddedReports();

    Page<EmbeddedReport> embeddedReports = (category != null)
        ? embeddedReportRepository.findAllByCategory(category, pageable)
        : embeddedReportRepository.findAll(pageable);

    return Pagination.getPage(EmbeddedReportDto.newInstance(embeddedReports), pageable);
  }

  /**
   * Adds or updates embedded report to database.
   *
   * @param dto Data transfer object containing Embedded Report data.
   */
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public EmbeddedReportDto createEmbeddedReport(@RequestBody EmbeddedReportDto dto) {
    permissionService.canViewEmbeddedReports();
    LOGGER.debug("Creating new embedded report");

    EmbeddedReport embeddedReportToUpdate = embeddedReportRepository.findByName(dto.getName());
    if (embeddedReportToUpdate == null) {
      LOGGER.debug("Creating new embedded report");
      embeddedReportToUpdate = EmbeddedReport.newInstance(dto);
      embeddedReportToUpdate.setId(null);
    } else {
      LOGGER.debug("Existing embedded report found, updating");
      dto.setId(embeddedReportToUpdate.getId());
      embeddedReportToUpdate = EmbeddedReport.newInstance(dto);
    }

    try {
      embeddedReportToUpdate = embeddedReportRepository.save(embeddedReportToUpdate);
    } catch (DataIntegrityViolationException integrityException) {
      throw new ValidationMessageException(new Message(ERROR_EMBEDDED_REPORT_NAME_DUPLICATED),
          integrityException);
    }

    LOGGER.debug("Saved embedded report with id: " + embeddedReportToUpdate.getId());
    return EmbeddedReportDto.newInstance(embeddedReportToUpdate);
  }

  /**
   * Allows deleting embedded report.
   *
   * @param reportId UUID of embedded report we want to delete
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTemplate(@PathVariable("id") UUID reportId) {
    permissionService.canViewEmbeddedReports();
    LOGGER.debug("Deleting embedded report");
    EmbeddedReport embeddedReport = embeddedReportRepository.findOne(reportId);
    if (embeddedReport == null) {
      throw new NotFoundMessageException(new Message(
          ERROR_EMBEDDED_REPORT_NOT_FOUND, reportId));
    } else {
      embeddedReportRepository.delete(embeddedReport);
      LOGGER.debug("Deleted embedded report with id: " + reportId);
    }
  }

}
