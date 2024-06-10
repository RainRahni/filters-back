package org.filter.mapper;

import org.filter.dto.FilterDto;
import org.filter.model.Filter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FilterMapper {
    @Mapping(target = "criteria", ignore = true)
    Filter toFilter(FilterDto filterDto);
}
