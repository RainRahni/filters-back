package org.filter.dto;

import java.util.List;

public record FilterDto(String name, List<CriteriaDto> criteria) {
}
