package org.filter.mapper;

import org.filter.dto.CriteriaDto;
import org.filter.model.Criteria;
import org.filter.model.enums.CriteriaType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CriteriaMapper {
    @Mapping(source = "type", target = "type", qualifiedByName = "stringToEnum")
    Criteria toCriteria(CriteriaDto dto);

    @Named("stringToEnum")
    default CriteriaType stringToEnum(String type) {
        return CriteriaType.valueOf(type.toUpperCase());
    }
    List<Criteria> toCriteriaList(List<CriteriaDto> dtoList);
    CriteriaDto toDto(Criteria criteria);
    List<CriteriaDto> toDtoList(List<Criteria> criteriaList);
}
