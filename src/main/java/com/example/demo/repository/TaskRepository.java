package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.common.FilterTaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends BaseRepository<Task, String>{

    @Query(" SELECT distinct t.user " +
            " FROM Task t " +
            " WHERE :fullTextQuery is null or UPPER(t.user.name) like :fullTextQuery" +
            " order by t.user.name")
    Page<User> getTaskUsers(@Param("fullTextQuery") String fullTextQuery, Pageable pageable);

//    Pageable pageable = PageRequest.of(filterOption.getPageNo(), filterOption.getPageSize(), Sort.Direction.DESC, C);
//
//
//    // repository class
//    @Query(value = "SELECT DISTINCT t.C FROM table t WHERE t.param = :param",
//            countQuery = "SELECT count(DISTINCT t.C) FROM table t WHERE t.param = :param")
//    Page<String> findUniqueWithCountPagination(Pageable pageable,
//                                               @Param("param") String param);

    @Query(" SELECT new com.example.demo.model.common.FilterTaskDto(t.id, t.number)" +
            " FROM Task t " +
            " WHERE (:fullTextQuery is null or cast(t.number as string) like :fullTextQuery)" +
            " AND t.type = 'BACKGROUND_MIGRATION'" +
            " order by t.number")
    Page<FilterTaskDto> getTasks(@Param("fullTextQuery") String fullTextQuery, Pageable pageable);
}
