package com.javohir.task2.service;

import com.javohir.task2.entity.Category;
import com.javohir.task2.entity.Task;
import com.javohir.task2.payload.ApiResponse;
import com.javohir.task2.payload.TaskDTO;
import com.javohir.task2.repository.CategoryRepository;
import com.javohir.task2.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse addOne(TaskDTO taskDTO){
        Optional<Category> optionalCategory = categoryRepository.findById(taskDTO.getCategoryId());
        if (!optionalCategory.isPresent()) return new ApiResponse("Category is not found",false);
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setCategory(optionalCategory.get());

        Task savedTask = taskRepository.save(task);
        return new ApiResponse("Task is added", true, savedTask);
    }

    public ApiResponse getOne(Integer id){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) return new ApiResponse("Task is not found", false);

        return new ApiResponse("Success", true, taskOptional.get());
    }

    public ApiResponse deleteOne(Integer id){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return new ApiResponse("Task is not found", false);
        }
        taskRepository.deleteById(id);
        return new ApiResponse("Success deleted", true, taskOptional.get());

    }

    public ApiResponse editOne(Integer id, TaskDTO taskDTO){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) return new ApiResponse("Task is not found", false);

        Optional<Category> optionalCategory = categoryRepository.findById(taskDTO.getCategoryId());
        if (!optionalCategory.isPresent()) return new ApiResponse("Category is not found",false);

        Task task = taskOptional.get();
        task.setCompleted(taskDTO.isCompleted());
        task.setDescription(taskDTO.getDescription());
        task.setCategory(optionalCategory.get());
        task.setName(taskDTO.getName());
        taskRepository.save(task);
        return new ApiResponse("Task edited successfully", true);
    }

    public List<Task> getAll(){
        return taskRepository.findAll();
    }

}
