package in.ashokit.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.entity.Counsellor;
import in.ashokit.entity.Student;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.utills.EmailUtil;

@Service
public class CounsellorService implements ICounsellorService {
	
	private CounsellorRepo counsellorRepo;
	private EmailUtil emailUtil;
	
	public CounsellorService(CounsellorRepo counsellorRepo, EmailUtil emailUtil) {
		this.counsellorRepo = counsellorRepo;
		this.emailUtil = emailUtil;
	}
	
	@Override
	public Counsellor findByEmail(String email) {
		return counsellorRepo.findByCounsellorEmail(email);
	}
	
	@Override
	public boolean saveCounsellor(Counsellor counsellor) {
		Counsellor save = counsellorRepo.save(counsellor);
		if(save.getCounsellorId() != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public Counsellor loginCheck(Counsellor counsellor) {
		return counsellorRepo.findByCounsellorEmailAndCounsellorPassword(counsellor.getCounsellorEmail(), counsellor.getCounsellorPassword());
	}
	
	@Override
	public Optional<Counsellor> getById(Integer id) {
		return counsellorRepo.findById(id);
	}
	
	@Override
	public DashboardResponse buildDashboard(Integer id) {
				
		if(id==null) {
			return null;
		}
		Optional<Counsellor> byId = counsellorRepo.findById(id);
		if(byId.isPresent()) {
			Counsellor counsellor = byId.get();
			List<Student> student = counsellor.getStudent();
			
			Map<String,Long> collect = student.stream()
					.collect(Collectors.groupingBy(Student::getStatus, Collectors.counting()));
			
			DashboardResponse dash = new DashboardResponse();
			dash.setTotalEnquiries(collect.values().stream().mapToLong(Long::longValue).sum());
			
			if(collect.get("New") == null) {
				collect.put("New", (long) 0);
				dash.setNewEnquiries(collect.get("New"));
			} else {
				dash.setNewEnquiries(collect.get("New"));
			}
			
			if(collect.get("Enrolled") == null) {
				collect.put("Enrolled", (long) 0);
				dash.setEnrolledEnquiries(collect.get("Enrolled"));
			} else {
				dash.setEnrolledEnquiries(collect.get("Enrolled"));
			}
			
			if(collect.get("Lost") == null) {
				collect.put("Lost", (long) 0);
				dash.setLostEnquiries(collect.get("Lost"));
			} else {
				dash.setLostEnquiries(collect.get("Lost"));
			}
			
			return dash;
		}
		
		return null;
	}
	
	@Override
	public boolean sendForgetPasswordEmail(Counsellor counsellor) {
		String subject = "Recover password request - AshokIT";
		String body = "<p> Hi " + counsellor.getCounsellorName() + ",</p>" 
	            + "<p>We received a request to reset the password for your account.</p>"
	            + "<p>Your password is: "+ counsellor.getCounsellorPassword()+ "</p>"
	            + "<p>Please use this password to log in to your account.</p>"
	            + "<p>If you didn't request a password reset, please ignore this email.</p>"
				+ "<p>Thank you,<br/>AshokIT</p>";

		return emailUtil.sendForgetMail(counsellor.getCounsellorEmail(), subject, body);		
	}
}
