package com.aztec.student;
import com.aztec.student.domain.StudentDto;
import com.aztec.student.repository.StudentRepository;
import com.aztec.student.service.StudentServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentServiceImpl studentServiceImpl;

    @Before
    public void setUp() {
        System.out.println("this method is called before each test case");
        MockitoAnnotations.initMocks(this);
        //object creation for each test case
        studentServiceImpl = new StudentServiceImpl(studentRepository);
    }

    @Test
    public void whenStudentListGiven_thenReturnGenderMapper(){
        given(studentRepository.findStudents()).willReturn(students());
        Map<String,List<StudentDto>> mapper = studentServiceImpl.genderMapper();
        System.out.println("1st test case");
        assertEquals(1, mapper.get("male").size());
        assertEquals(1, mapper.get("female").size());
    }

    private List<StudentDto> students(){
        return Arrays.asList(new StudentDto("shree","krishna","poudel", LocalDate.now(),"male"),
                new StudentDto("sita","kumari","jha", LocalDate.now(),"female"));
    }

   @Test
    public void whenTextGiven_ThenReturnCountMapper(){
       System.out.println("2nd test case");
        Map<String,Integer> mapper=studentServiceImpl.countMapper(text());
        assertEquals(Integer.valueOf(2),mapper.get("is"));
    }
    private String text(){
        return  "This is a text where you could count the occurrance of each word. This is an example of data structure";
    }
    @After
    public void afterMethod(){

        System.out.println("this method is called after each test case");

    }
}
