package in.ashokit.service;

import org.springframework.data.domain.Page;

import in.ashokit.entity.Student;

public interface IStudentService {
	
	public Student findStudentByEmail(String email);
	
	public boolean saveStudent(Student student);
			
	public Page<Student> getAllStudents(Integer cid, Student s, int page);
	
	public Student getStudent(Integer id);
}
