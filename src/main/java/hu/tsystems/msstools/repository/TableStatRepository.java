package hu.tsystems.msstools.repository;

import hu.tsystems.msstools.domain.TableStat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TableStat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TableStatRepository extends JpaRepository<TableStat,Long> {

}
