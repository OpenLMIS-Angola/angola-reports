package org.openlmis.ao;

import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.openlmis.ao.reports.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * AuditLogInitializer runs after its associated Spring application has loaded.
 * It examines each domain object in the database and registers them with JaVers
 * if they haven't already been so. This is, in part, a fix for
 * <a href="https://github.com/javers/javers/issues/214">this issue</a>.
 */

@Component
@Profile("!test")
public class AuditLogInitializer implements CommandLineRunner {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private Javers javers;

  /**
   * This method is part of CommandLineRunner and is called automatically by Spring.
   * @param args Main method arguments.
   */
  public void run(String... args) {
    //Get all JaVers repositories.
    Map<String,Object> repositoryMap =
            applicationContext.getBeansWithAnnotation(JaversSpringDataAuditable.class);

    //For each one...
    for (Object object : repositoryMap.values()) {

      //... retrieve all of its domain objects and...
      Iterable<?> domainObjects = ((CrudRepository)(object)).findAll();

      //... for each one...
      for (Object o : domainObjects) {

        //...check whether there exists a snapshot for it in the audit log.
        // Note that we don't care about checking for logged changes, per se,
        // and thus use findSnapshots() rather than findChanges()
        BaseEntity baseEntity = (BaseEntity)o;

        QueryBuilder jqlQuery = QueryBuilder.byInstanceId(baseEntity.getId(), o.getClass());
        List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());

        //If there are no snapshots of the domain object, then take one
        if (snapshots.size() == 0) {
          javers.commit("System: AuditLogInitializer" , baseEntity);
        }

      }
    }

  }
}
