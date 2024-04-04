package in.ashokit.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{
	
	public Student findByStudentEmail(String email);

	public Page<Student> findByCounsellor_CounsellorId(Integer counsellorId, PageRequest p);
	
	public Page<Student> findAll(Specification<Student> s, Pageable p);
}
