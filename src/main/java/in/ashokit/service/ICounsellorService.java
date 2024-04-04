package in.ashokit.service;

import java.util.Optional;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.entity.Counsellor;

public interface ICounsellorService {
	
	public boolean saveCounsellor(Counsellor counsellor);
	
	public Counsellor loginCheck(Counsellor counsellor);
	
	public Counsellor findByEmail(String email);
	
	public DashboardResponse buildDashboard(Integer id);
	
	public Optional<Counsellor> getById(Integer id);
	
	public boolean sendForgetPasswordEmail(Counsellor counsellor);
}
