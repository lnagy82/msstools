package hu.tsystems.msstools.service.mapper;

import hu.tsystems.msstools.domain.*;
import hu.tsystems.msstools.service.dto.SystemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity System and its DTO SystemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemMapper extends EntityMapper <SystemDTO, System> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default System fromId(Long id) {
        if (id == null) {
            return null;
        }
        System system = new System();
        system.setId(id);
        return system;
    }
}
