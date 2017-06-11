package hu.tsystems.msstools.service.mapper;

import hu.tsystems.msstools.domain.SystemApp;
import hu.tsystems.msstools.service.dto.SystemAppDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Component;

/**
 * Mapper for the entity System and its DTO SystemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemAppMapper extends EntityMapper <SystemAppDTO, SystemApp> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default SystemApp fromId(Long id) {
        if (id == null) {
            return null;
        }
        SystemApp system = new SystemApp();
        system.setId(id);
        return system;
    }
}
