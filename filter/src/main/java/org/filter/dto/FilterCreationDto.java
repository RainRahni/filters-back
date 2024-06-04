package org.filter.dto;

import java.util.List;

public record FilterCreationDto(String filterName, List<CriteriaCreationDto> criterias){
}
