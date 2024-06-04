package org.filter.mapper;

import org.filter.dto.CriteriaCreationDto;
import org.filter.model.Criteria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CriteriaMapper {
    List<Criteria> toCriteriaList(List<CriteriaCreationDto> dtoList);
}
