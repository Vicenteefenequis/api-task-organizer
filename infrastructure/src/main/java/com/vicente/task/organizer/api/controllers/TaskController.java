package com.vicente.task.organizer.api.controllers;

import com.vicente.task.organizer.api.TaskAPI;
import com.vicente.task.organizer.task.models.CreateTaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController implements TaskAPI {


    @Override
    public ResponseEntity<?> createCategory(CreateTaskRequest request) {
        return null;
    }
}
