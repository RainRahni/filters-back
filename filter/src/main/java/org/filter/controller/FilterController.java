package org.filter.controller;

import lombok.RequiredArgsConstructor;
import org.filter.dto.FilterCreationDto;
import org.filter.service.FilterService;
import org.filter.service.FilterServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("filter")
@RequiredArgsConstructor
public class FilterController {
    private final FilterServiceImpl filterService;

    @PostMapping()
    public void createNewFilter(@RequestBody FilterCreationDto filterCreationDto) {
        filterService.createNewFilter(filterCreationDto);
    }
}
