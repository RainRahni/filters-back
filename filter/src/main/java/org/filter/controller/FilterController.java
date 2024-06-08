package org.filter.controller;

import lombok.RequiredArgsConstructor;
import org.filter.dto.FilterDto;
import org.filter.service.FilterServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("filter")
@RequiredArgsConstructor
public class FilterController {
    private final FilterServiceImpl filterService;

    @PostMapping()
    public void createNewFilter(@RequestBody FilterDto filterDto) {
        filterService.createNewFilter(filterDto);
    }
    @GetMapping()
    public List<FilterDto> readAllFilters() {
        return filterService.readAllFilters();
    }
}
