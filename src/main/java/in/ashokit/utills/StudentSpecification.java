package in.ashokit.utills;

import org.springframework.data.jpa.domain.Specification;

import in.ashokit.entity.Counsellor;
import in.ashokit.entity.Student;

public class StudentSpecification {
	
	public static Specification<Student> includeId(Counsellor c){
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("counsellor"), c);
	}
	
	public static Specification<Student> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("studentName"), "%" + name + "%");
    }

    public static Specification<Student> courseEqualTo(String courseName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courseName"), courseName);
    }

    public static Specification<Student> courseModeEqual(String courseMode) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courseMode"), courseMode);
    }
    
    public static Specification<Student> courseStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
