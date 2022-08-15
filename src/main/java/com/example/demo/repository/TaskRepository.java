package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends BaseRepository<Task, String>{
    @Query(" SELECT distinct t.user " +
            " FROM Task t " +
            " WHERE :fullTextQuery is null or UPPER(t.user.name) like :fullTextQuery" +
            " order by t.user.name")
    Page<User> getTaskUsers(@Param("fullTextQuery") String fullTextQuery, Pageable pageable);
}
