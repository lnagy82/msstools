package hu.tsystems.msstools.service.mapper;

import hu.tsystems.msstools.domain.*;
import hu.tsystems.msstools.service.dto.TableStatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TableStat and its DTO TableStatDTO.
 */
@Mapper(componentModel = "spring", uses = {SystemAppMapper.class, })
public interface TableStatMapper extends EntityMapper <TableStatDTO, TableStat> {
    @Mapping(source = "system.id", target = "systemId")
    @Mapping(source = "system.name", target = "systemName")
    TableStatDTO toDto(TableStat tableStat); 
    @Mapping(source = "systemId", target = "system")
    TableStat toEntity(TableStatDTO tableStatDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default TableStat fromId(Long id) {
        if (id == null) {
            return null;
        }
        TableStat tableStat = new TableStat();
        tableStat.setId(id);
        return tableStat;
    }
}
