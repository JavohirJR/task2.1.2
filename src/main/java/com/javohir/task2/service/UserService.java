package com.javohir.task2.service;

import com.javohir.task2.entity.StarBadge;
import com.javohir.task2.entity.Task;
import com.javohir.task2.entity.User;
import com.javohir.task2.payload.ApiResponse;
import com.javohir.task2.payload.UserDTO;
import com.javohir.task2.repository.StarBadgeRepository;
import com.javohir.task2.repository.TaskRepository;
import com.javohir.task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StarBadgeRepository starBadgeRepository;

    @Autowired
    TaskRepository taskRepository;

    public ApiResponse addOne(UserDTO userDTO) {
        Integer[] taskList = userDTO.getTaskList();
        List<Task> tasks = new ArrayList<>();
        for (Integer integer : taskList) {
            Optional<Task> optionalTask = taskRepository.findById(integer);
            optionalTask.ifPresent(tasks::add);
        }
        if (tasks.isEmpty()) return new ApiResponse("Tasks are not find", false);

        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(userDTO.getStarBadgeId());
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Star Badge is not find", false);

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFullName(userDTO.getFullName());
        user.setTaskList(tasks);
        user.setStarBadge(optionalStarBadge.get());
        User savedUser = userRepository.save(user);
        return new ApiResponse("User saved successfully", true, savedUser);

    }

    public ApiResponse editOne(Integer id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return new ApiResponse("user is not found", false);

        Integer[] taskList = userDTO.getTaskList();
        List<Task> tasks = new ArrayList<>();
        for (Integer integer : taskList) {
            Optional<Task> optionalTask = taskRepository.findById(integer);
            optionalTask.ifPresent(tasks::add);
        }
        if (tasks.isEmpty()) return new ApiResponse("Tasks are not find", false);

        Optional<StarBadge> optionalStarBadge = starBadgeRepository.findById(userDTO.getStarBadgeId());
        if (!optionalStarBadge.isPresent()) return new ApiResponse("Star Badge is not find", false);

        User user = optionalUser.get();
        user.setFullName(user.getFullName());
        user.setPassword(user.getPassword());
        user.setTaskList(tasks);
        user.setStarBadge(optionalStarBadge.get());
        userRepository.save(user);
        return new ApiResponse("user edited successfully", true);

    }

    public ApiResponse getOne(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) return new ApiResponse("Success", true, optionalUser.get());
        return new ApiResponse("User is not found", false);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public ApiResponse deleteOne(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return new ApiResponse("User is successfully deleted", true);
        }
        return new ApiResponse("User is not found", false);
    }
}
