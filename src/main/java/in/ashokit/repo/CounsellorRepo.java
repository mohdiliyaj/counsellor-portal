package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{
	
	public Counsellor findByCounsellorEmailAndCounsellorPassword(String email, String password);
	
	public Counsellor findByCounsellorEmail(String email);
}
