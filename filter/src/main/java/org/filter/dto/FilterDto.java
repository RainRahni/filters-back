package org.filter.dto;

import java.util.List;

public record FilterDto(String filterName, List<CriteriaDto> criterias){
}
