package com.aztec.student.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.aztec.student.domain.Student;
import com.aztec.student.domain.StudentDto;

public interface StudentService {
	public List<Student> findAll();
	public Map<String,List<StudentDto>> genderMapper();
	public void save(Student student);
    public Optional<Student> findByCitizenshipNo(String citizenshipNo);
	public void delete(Long id);
	public Optional<Student> findById(Long id);
	public Map<String,Integer> countMapper(String data);
	public void readFile() throws IOException;

}
