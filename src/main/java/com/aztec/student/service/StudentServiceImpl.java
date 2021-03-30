package com.aztec.student.service;

import com.aztec.student.controller.StudentController;
import com.aztec.student.domain.Student;
import com.aztec.student.domain.StudentDto;
import com.aztec.student.exception.custom.EntityNotFoundException;
import com.aztec.student.repository.StudentRepository;
import com.aztec.student.utils.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	Logger logger = LoggerFactory.getLogger(StudentController.class);

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Map<String, List<StudentDto>> genderMapper() {
        List<StudentDto> studentDtos = studentRepository.findStudents();
        Map<String,List<StudentDto>> mapper = new HashMap<>();
        List<StudentDto> males = studentDtos.stream().filter(studentDto -> studentDto.getGender().equalsIgnoreCase(Constants.MALE)).collect(Collectors.toList());
        List<StudentDto> females = studentDtos.stream().filter(studentDto -> studentDto.getGender().equalsIgnoreCase(Constants.FEMALE)).collect(Collectors.toList());
        mapper.put(Constants.MALE,males);
        mapper.put(Constants.FEMALE,females);
        return mapper;
    }

    @Override
    public void save(Student student){
        if (isAlreadyExist(student.getCitizenshipNo())) {
            new EntityNotFoundException(String.format("student ctz no %s already exist in table",student.getCitizenshipNo()));
        }
        studentRepository.save(student);
    }

    private boolean isAlreadyExist(String citizenshipNo) {
        return studentRepository.findByCitizenshipNo(citizenshipNo).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findByCitizenshipNo(String citizenshipNo) {
        return studentRepository.findByCitizenshipNo(citizenshipNo);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Optional<Student> findById(Long id) {
        Optional<Student> student= Optional.ofNullable(studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("student of id %s not found in table",id))));
        return student;
    }

    @Override
    public Map<String, Integer> countMapper(String data) {
        Map<String,Integer> countMapper=new HashMap<>();
        String[] wordList = data.split(" ");
        for (String w : wordList) {
            if (countMapper.get(w) == null) {
                countMapper.put(w, 1);
            } else
                countMapper.put(w, countMapper.get(w) + 1);
        }
        return countMapper;
    }

    @Override
    public void readFile() throws IOException {
        File file = new File("/root/Documents/student.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int index =0;
        while ((st = br.readLine()) != null) {
            if(index!=0){
                System.out.println(st);
                String[] data = st.split(",");
                logger.info("Splitting data {}");
            }
            index++;
        }




    }
}
