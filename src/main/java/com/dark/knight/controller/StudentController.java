package com.dark.knight.controller;

import com.dark.knight.dto.StudentDto;
import com.dark.knight.services.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service){
        this.service = service;
    }

    @PostMapping("/student")
    public void createStudent(@RequestBody StudentDto studentDto) {
        service.saveStudent(studentDto);
    }

    @GetMapping("/student")
    public List<StudentDto> getAllStudent() {
        return service.findAllStudent();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public List<StudentDto> uploadFile(@RequestPart(value = "file") MultipartFile multiPartFile) throws IOException {
        return service.handleUploadFile(multiPartFile);
    }
}
