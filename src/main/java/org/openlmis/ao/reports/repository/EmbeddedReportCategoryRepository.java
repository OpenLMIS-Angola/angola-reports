package org.openlmis.ao.reports.repository;

import org.openlmis.ao.reports.domain.EmbeddedReportCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface EmbeddedReportCategoryRepository extends
    PagingAndSortingRepository<EmbeddedReportCategory, UUID> {

  EmbeddedReportCategory findByName(@Param("name") String name);

}
