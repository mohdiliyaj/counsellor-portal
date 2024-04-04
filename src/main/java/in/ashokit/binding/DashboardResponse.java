package in.ashokit.binding;

public class DashboardResponse {
	
	private Long totalEnquiries;
	private Long newEnquiries;
	private Long lostEnquiries;
	private Long enrolledEnquiries;
	
	public Long getTotalEnquiries() {
		return totalEnquiries;
	}
	public void setTotalEnquiries(Long totalEnquiries) {
		this.totalEnquiries = totalEnquiries;
	}
	public Long getNewEnquiries() {
		return newEnquiries;
	}
	public void setNewEnquiries(Long newEnquiries) {
		this.newEnquiries = newEnquiries;
	}
	public Long getLostEnquiries() {
		return lostEnquiries;
	}
	public void setLostEnquiries(Long lostEnquiries) {
		this.lostEnquiries = lostEnquiries;
	}
	public Long getEnrolledEnquiries() {
		return enrolledEnquiries;
	}
	public void setEnrolledEnquiries(Long enrolledEnquiries) {
		this.enrolledEnquiries = enrolledEnquiries;
	}
}
