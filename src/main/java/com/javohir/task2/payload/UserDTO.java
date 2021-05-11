package com.javohir.task2.payload;


import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String fullName;
    private Integer[] taskList;
    private Integer starBadgeId;
}
