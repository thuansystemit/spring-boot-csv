package com.dark.knight.services;

import com.dark.knight.dto.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    List<StudentDto> findAllStudent();

    void saveStudent(StudentDto student);

    StudentDto findStudent(Integer id);

    List<StudentDto> handleUploadFile(MultipartFile multipartFile) throws IOException;

}
