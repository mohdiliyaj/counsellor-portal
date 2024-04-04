package in.ashokit.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import in.ashokit.entity.Counsellor;
import in.ashokit.entity.Student;
import in.ashokit.repo.StudentRepo;
import in.ashokit.utills.StudentSpecification;

@Service
public class StudentService implements IStudentService {

	private StudentRepo studentRepo;

	public StudentService(StudentRepo studentRepo) {
		this.studentRepo = studentRepo;
	}

	@Override
	public Student findStudentByEmail(String email) {
		return studentRepo.findByStudentEmail(email);
	}

	@Override
	public boolean saveStudent(Student student) {
		Student save = studentRepo.save(student);
		if (save.getStudentId() != null) {
			return true;
		}
		return false;
	}

	@Override
	public Page<Student> getAllStudents(Integer cid, Student s, int page) {
		Specification<Student> spec = Specification.where(null);
		
		Counsellor c = new Counsellor();
		c.setCounsellorId(cid);
		
		if(cid!=null) {
			spec = spec.and(StudentSpecification.includeId(c));
		}
		
		if (isNotBlank(s.getStudentName())) {
			spec = spec.and(StudentSpecification.nameLike(s.getStudentName()));
		}
		if (isNotBlank(s.getCourseName())) {
			spec = spec.and(StudentSpecification.courseEqualTo(s.getCourseName()));
		}
		if (isNotBlank(s.getCourseMode())) {
			spec = spec.and(StudentSpecification.courseModeEqual(s.getCourseMode()));
		}
		if (isNotBlank(s.getStatus())) {
			spec = spec.and(StudentSpecification.courseStatus(s.getStatus()));
		}
		
		PageRequest of = null;
		if (page > 0) {
			of = PageRequest.of(page - 1, 5);
		} else if (page == 1) {
			of = PageRequest.of(page-1, 5);
		}
		
		Page<Student> all = studentRepo.findAll(spec, of);
		return all;
	}

	private boolean isNotBlank(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	
	@Override
	public Student getStudent(Integer id) {
		Optional<Student> byId = studentRepo.findById(id);
		if(byId.isPresent()) {
			return byId.get();
		}
		return null;
	}

}
