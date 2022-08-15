package com.example.demo.model.common;

import lombok.Data;
import lombok.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class History implements Serializable {

    /**
     * Change events.
     */
    private List<ChangeEvent> events = new ArrayList<>();

    /**
     * Add new event into history.
     *
     * @param event new event
     * @return this for chaining
     */
    public History addEvent(@NonNull ChangeEvent event) {
        events.add(event);
        return this;
    }
}