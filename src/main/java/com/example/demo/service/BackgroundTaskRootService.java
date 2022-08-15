package com.example.demo.service;

import com.example.demo.model.common.FilterTaskDto;
import com.example.demo.repository.TaskRepository;
import com.example.demo.util.FilterUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundTaskRootService {

    private final TaskRepository taskRepository;

    public Page<FilterUserDto> getAllUserIds(String fullTextQuery, Pageable pageable) {
        return taskRepository.getTaskUsers(formatFullTextQuery(fullTextQuery), pageable)
                .map(u -> new FilterUserDto(u.getId(), u.getName()));
    }

    public Page<FilterTaskDto> getAllTaskIds(String fullTextQuery, Pageable pageable) {
        return taskRepository.getTasks(formatFullTextQuery(fullTextQuery), pageable);
    }

    private static String formatFullTextQuery(String fullTextQuery) {
        return Optional.ofNullable(fullTextQuery)
                .map(String::toUpperCase)
                .map(query -> "%" + query + "%")
                .orElse(null);
    }
}
