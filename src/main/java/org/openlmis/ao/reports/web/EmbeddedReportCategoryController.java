package org.openlmis.ao.reports.web;

import static org.openlmis.ao.reports.i18n.EmbeddedReportCategoryMessageKeys.ERROR_EMBEDDED_REPORT_CATEGORY_NAME_DUPLICATED;
import static org.openlmis.ao.reports.i18n.EmbeddedReportCategoryMessageKeys.ERROR_EMBEDDED_REPORT_CATEGORY_NOT_FOUND;
import static org.openlmis.ao.reports.web.EmbeddedReportCategoryController.RESOURCE_PATH;

import javax.validation.Valid;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;
import org.openlmis.ao.reports.dto.EmbeddedReportCategoryDto;
import org.openlmis.ao.reports.exception.NotFoundMessageException;
import org.openlmis.ao.reports.exception.ValidationMessageException;
import org.openlmis.ao.reports.i18n.EmbeddedReportCategoryMessageKeys;
import org.openlmis.ao.reports.repository.EmbeddedReportCategoryRepository;
import org.openlmis.ao.reports.service.PermissionService;
import org.openlmis.ao.utils.Message;
import org.openlmis.ao.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Transactional
@RequestMapping(RESOURCE_PATH)
public class EmbeddedReportCategoryController extends BaseController {

  public static final String RESOURCE_PATH = "/api/reports/embeddedReportCategories";

  private static final Logger LOGGER = Logger.getLogger(EmbeddedReportCategoryController.class);

  @Autowired
  private EmbeddedReportCategoryRepository embeddedReportCategoryRepository;

  @Autowired
  private PermissionService permissionService;

  /**
   * Get chosen embedded report category.
   *
   * @param categoryId UUID of embedded report category we want to get
   * @return Embedded report.
   */
  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public EmbeddedReportCategoryDto getEmbeddedReportCategory(@PathVariable("id") UUID categoryId) {
    permissionService.canViewEmbeddedReportsCategory();
    EmbeddedReportCategory embeddedReportCategory = embeddedReportCategoryRepository
        .findOne(categoryId);

    if (embeddedReportCategory == null) {
      throw new NotFoundMessageException(
          EmbeddedReportCategoryMessageKeys.ERROR_EMBEDDED_REPORT_CATEGORY_NOT_FOUND);
    }

    return EmbeddedReportCategoryDto.newInstance(embeddedReportCategory);
  }

  /**
   * Get page of all embedded report categories.
   *
   * @return All embedded report categories matching certain criteria.
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Page<EmbeddedReportCategoryDto> getAllEmbeddedReportCategories(Pageable pageable) {
    permissionService.canViewEmbeddedReportsCategory();

    Page<EmbeddedReportCategory> embeddedReportCategories =
        embeddedReportCategoryRepository.findAll(pageable);

    return Pagination.getPage(EmbeddedReportCategoryDto.newInstance(embeddedReportCategories),
        pageable);
  }

  /**
   * Adds or updates embedded report category to database.
   *
   * @param dto Data transfer object containing Embedded Report Category data.
   */
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public EmbeddedReportCategoryDto createEmbeddedReportCategory(
      @Valid @RequestBody EmbeddedReportCategoryDto dto) {
    permissionService.canViewEmbeddedReportsCategory();
    LOGGER.debug("Creating new embedded report category");

    EmbeddedReportCategory embeddedReportCategoryToUpdate =
        embeddedReportCategoryRepository.findByName(dto.getName());
    if (embeddedReportCategoryToUpdate == null) {
      LOGGER.debug("Creating new embedded report category");
      embeddedReportCategoryToUpdate = EmbeddedReportCategory.newInstance(dto);
      embeddedReportCategoryToUpdate.setId(null);
    } else {
      LOGGER.debug("Existing embedded report category found, updating");
      dto.setId(embeddedReportCategoryToUpdate.getId());
      embeddedReportCategoryToUpdate = EmbeddedReportCategory.newInstance(dto);
    }

    try {
      embeddedReportCategoryToUpdate = embeddedReportCategoryRepository
          .save(embeddedReportCategoryToUpdate);
    } catch (DataIntegrityViolationException integrityException) {
      throw new ValidationMessageException(new Message(
          ERROR_EMBEDDED_REPORT_CATEGORY_NAME_DUPLICATED), integrityException);
    }

    LOGGER.debug("Saved embedded report category with id: "
        + embeddedReportCategoryToUpdate.getId());
    return EmbeddedReportCategoryDto.newInstance(embeddedReportCategoryToUpdate);
  }

  /**
   * Allows deleting embedded report category.
   *
   * @param categoryId UUID of embedded report category we want to delete
   */
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmbeddedReportCategory(@PathVariable("id") UUID categoryId) {
    permissionService.canViewEmbeddedReportsCategory();
    LOGGER.debug("Deleting embedded report category");
    EmbeddedReportCategory embeddedReportCategory =
        embeddedReportCategoryRepository.findOne(categoryId);
    if (embeddedReportCategory == null) {
      throw new NotFoundMessageException(new Message(
          ERROR_EMBEDDED_REPORT_CATEGORY_NOT_FOUND, categoryId));
    } else {
      embeddedReportCategoryRepository.delete(embeddedReportCategory);
      LOGGER.debug("Deleted embedded report category with id: " + categoryId);
    }
  }

}
