package hu.tsystems.msstools.repository;

import hu.tsystems.msstools.domain.SystemApp;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the System entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemRepository extends JpaRepository<SystemApp,Long> {

}
