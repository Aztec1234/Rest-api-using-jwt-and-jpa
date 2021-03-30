package com.aztec.student.repository;

import com.aztec.student.domain.Student;
import com.aztec.student.domain.StudentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT new com.aztec.student.domain.StudentDto(s.firstName,s.middleName,s.lastName,s.dateOfBirth,s.gender) from Student s")
    public List<StudentDto> findStudents();

    public Optional<Student> findByCitizenshipNo(String citizenshipNo);
}