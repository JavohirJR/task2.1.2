package com.javohir.task2.payload;

import lombok.Data;

@Data
public class TaskDTO {
    private String name;
    private String description;
    private boolean completed;
    private Integer categoryId;
}
