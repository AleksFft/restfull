package com.example.demo.controller;

import com.example.demo.model.common.FilterTaskDto;
import com.example.demo.service.BackgroundTaskRootService;
import com.example.demo.util.FilterUserDto;
import com.example.demo.util.GenericPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Validated
@RestController
@Transactional
@Api(tags = "Фоновая миграция. Скроллер организаций")
@RequiredArgsConstructor
@RequestMapping(value = "/tasks/background/orgs", produces = MediaType.APPLICATION_JSON_VALUE)
public class BackgroundTaskRootResourceImpl {

    private final BackgroundTaskRootService statisticsService;

//    @Trace
    @GetMapping(value = "/allUserIds")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Справочник: пользователи")
//    @PreAuthorize("hasStaticPermission('BACKGROUND_MIGRATION_TASK_VIEW')")
    public GenericPage<FilterUserDto> getAllUserIds(@RequestParam(value = "fullTextQuery", required = false) String fullTextQuery,
                                                    @ApiIgnore Pageable pageable) {
        Page<FilterUserDto> allUserIds = statisticsService.getAllUserIds(fullTextQuery, pageable);
        return new GenericPage<>(allUserIds.getContent(), allUserIds.getTotalElements());
    }

//    @Trace
    @GetMapping(value = "/allTaskIds")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Справочник: ID задач фоновой миграции")
//    @PreAuthorize("hasStaticPermission('BACKGROUND_MIGRATION_TASK_VIEW')")
    public GenericPage<FilterTaskDto> getAllTaskIds(@RequestParam(value = "fullTextQuery", required = false) String fullTextQuery,
                                                    @ApiIgnore Pageable pageable) {
        Page<FilterTaskDto> allTaskIds = statisticsService.getAllTaskIds(fullTextQuery, pageable);
        return new GenericPage<>(allTaskIds.getContent(), allTaskIds.getTotalElements());
    }
}
