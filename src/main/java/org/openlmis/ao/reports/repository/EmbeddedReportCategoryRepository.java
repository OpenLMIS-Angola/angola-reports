package org.openlmis.ao.reports.repository;

import java.util.Optional;
import java.util.UUID;
import org.openlmis.ao.reports.domain.EmbeddedReportCategory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface EmbeddedReportCategoryRepository extends
    PagingAndSortingRepository<EmbeddedReportCategory, UUID> {

  EmbeddedReportCategory findByName(@Param("name") String name);

  Optional<EmbeddedReportCategory> findById(UUID id);

}
