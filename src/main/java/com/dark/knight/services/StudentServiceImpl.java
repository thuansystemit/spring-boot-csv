package com.dark.knight.services;

import com.dark.knight.dao.StudentDao;
import com.dark.knight.dto.StudentDto;
import com.dark.knight.model.Address;
import com.dark.knight.model.Gender;
import com.dark.knight.model.Student;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private static final String FEMALE = "Female";
    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<StudentDto> findAllStudent() {
        List<StudentDto> result = new ArrayList<>();
        List<Student> students = studentDao.findAll();
        System.out.println("Console.log: " + students.get(0).getId());;
        result = convertListOfStudentToDto(students);
        return result;
    }

    @Override
    public void saveStudent(StudentDto studentDto) {
        Student student = convertToStudent(studentDto);
        studentDao.save(student);
    }

    @Override
    public StudentDto findStudent(Integer id) {
        StudentDto result = new StudentDto();
        result = convertToStudentDto(studentDao.findById(id).get());
        return result;
    }

    @Override
    public List<StudentDto> handleUploadFile(MultipartFile multipartFile) throws IOException {

        File file = convertMultiPartToFile(multipartFile);

        List<StudentDto> studentList = new ArrayList<>();

        try (Reader reader = new FileReader(file);) {
            @SuppressWarnings("unchecked")
            CsvToBean<StudentDto> csvToBean = new CsvToBeanBuilder<StudentDto>(reader).withType(StudentDto.class)
                    .withIgnoreLeadingWhiteSpace(true).build();
            studentList = csvToBean.parse();
            List<Student> students = convertListOfStudent(studentList);
            studentDao.saveAll(students);
        } catch (Exception ex) {
            System.out.println("Log" + ex.getMessage());
        }
        return studentList;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private Student convertToStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setGpa(studentDto.getGpa());
        if (FEMALE.equals(studentDto.getGender())) {
            student.setGender(Gender.Female);
        } else {
            student.setGender(Gender.Male);
        }
        Address address = new Address();
        address.setName(studentDto.getAddress());
        student.setAddress(address);
        return student;
    }

    private StudentDto convertToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setAddress(student.getAddress().getName());
        studentDto.setGpa(student.getGpa());
        studentDto.setGender(student.getGender().name());
        return studentDto;
    }
    private List<StudentDto> convertListOfStudentToDto(List<Student> students) {
        List<StudentDto> results = new ArrayList<>();
        students.forEach(student-> {
            results.add(convertToStudentDto(student));
        });
        return results;
    }

    private List<Student> convertListOfStudent(List<StudentDto> studentDtos) {
        List<Student> result = new ArrayList<>();
        studentDtos.forEach(studentDto -> {
            result.add(convertToStudent(studentDto));
        });
        return result;
    }
}
