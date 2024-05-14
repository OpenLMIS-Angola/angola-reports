package org.openlmis.ao.reports.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.openlmis.ao.reports.domain.EmbeddedReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface EmbeddedReportRepository extends PagingAndSortingRepository<EmbeddedReport, UUID> {

  EmbeddedReport findByName(@Param("name") String name);

  List<EmbeddedReport> findAll();

  Page<EmbeddedReport> findAll(Pageable pageable);

  Page<EmbeddedReport> findAllByCategoryName(String name, Pageable pageable);

  Optional<EmbeddedReport> findById(UUID id);

}