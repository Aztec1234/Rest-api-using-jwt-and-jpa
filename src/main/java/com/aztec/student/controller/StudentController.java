package com.aztec.student.controller;

import com.aztec.student.domain.Student;
import com.aztec.student.domain.StudentDto;
import com.aztec.student.exception.custom.EntityNotFoundException;
import com.aztec.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StudentController {


	Logger logger = LoggerFactory.getLogger(StudentController.class);

	private StudentService studentService;

	@Autowired
	private StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping(value = "/students")
	public ResponseEntity<List<Student>> get(String authorization) {

		//JwtToken jwtToken=new JwtToken(authorization);
		logger.info("getting student information");
		List<Student> students=studentService.findAll();
		return ResponseEntity.ok(students);
	}

	@GetMapping(value="/students/{id}")
	public Optional<Student> getById(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorization) {



		logger.info("getting student information");
		Optional<Student> students=studentService.findById(id);
		return students;
	}

	@GetMapping(value = "/studentnames")
	public Map<String,List<StudentDto>> getStudentName() {
		logger.info("getting students' name ");
		return studentService.genderMapper();
	}

	@PostMapping(value="/students")
	public ResponseEntity<Void> add(@RequestBody Student student,@RequestHeader(value = "Authorization") String authorization){
		logger.info("adding student information");
		studentService.save(student);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping(value="/students/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		logger.info("deleteing information");
		studentService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value="students/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Student student) throws Exception{
		logger.info("updating information");
		studentService.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("Id  %s doesnt  exist",id)));
		student.setId(id);
		studentService.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("csv")
	public void save() throws IOException {
		 studentService.readFile();
	}

}



