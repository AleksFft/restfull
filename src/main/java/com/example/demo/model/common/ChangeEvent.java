package com.example.demo.model.common;

import com.example.demo.model.User;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class ChangeEvent implements Serializable {

    /**
     * Change description.
     */
    private String description;

    /**
     * Change status time.
     */
    private Date time;

    /**
     * Who changed status.
     */
    private User who;
}