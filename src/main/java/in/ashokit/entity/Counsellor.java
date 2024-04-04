package in.ashokit.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Counsellor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer counsellorId;
	private String counsellorName;
	private String counsellorEmail;
	private Long counsellorPhone;
	private String counsellorPassword;
	
	@OneToMany(mappedBy = "counsellor", cascade = CascadeType.ALL)
	private List<Student> student;

	public Integer getCounsellorId() {
		return counsellorId;
	}

	public void setCounsellorId(Integer counsellorId) {
		this.counsellorId = counsellorId;
	}

	public String getCounsellorName() {
		return counsellorName;
	}

	public void setCounsellorName(String counsellorName) {
		this.counsellorName = counsellorName;
	}

	public String getCounsellorEmail() {
		return counsellorEmail;
	}

	public void setCounsellorEmail(String counsellorEmail) {
		this.counsellorEmail = counsellorEmail;
	}

	public Long getCounsellorPhone() {
		return counsellorPhone;
	}

	public void setCounsellorPhone(Long counsellorPhone) {
		this.counsellorPhone = counsellorPhone;
	}

	public String getCounsellorPassword() {
		return counsellorPassword;
	}

	public void setCounsellorPassword(String counsellorPassword) {
		this.counsellorPassword = counsellorPassword;
	}

	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}
}
