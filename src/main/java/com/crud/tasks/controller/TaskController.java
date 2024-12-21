package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper mapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return mapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long taskId) throws TaskNotFoundException {
        return ResponseEntity.ok(mapper.mapToTaskDto(service.getTask(taskId)));
    }

    @DeleteMapping(value = "{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Task task = mapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);
        return ResponseEntity.ok(mapper.mapToTaskDto(savedTask));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = mapper.mapToTask(taskDto);
        Task createdTask = service.saveTask(task);
        return ResponseEntity.ok(mapper.mapToTaskDto(createdTask));
    }

}
