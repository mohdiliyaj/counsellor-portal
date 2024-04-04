package in.ashokit.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.entity.Counsellor;
import in.ashokit.entity.Student;
import in.ashokit.service.ICounsellorService;
import in.ashokit.service.IStudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

	private IStudentService studentService;
	private ICounsellorService counsellorService;

	public StudentController(IStudentService studentService, ICounsellorService counsellorService) {
		this.studentService = studentService;
		this.counsellorService = counsellorService;
	}

	@GetMapping("/add-enquiry")
	public String loadAddEnquiry(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return "redirect:/logout";
		}
		Integer cid = (Integer) session.getAttribute("counsellorId");
		if(cid==null) {
			return "redirect:/logout";
		}
		model.addAttribute("student", new Student());
		return "addEnquiry";
	}

	@PostMapping("/add-enquiry")
	public String addEnquiry(@ModelAttribute Student student, HttpServletRequest req, Model model) {
		Student studentByEmail = studentService.findStudentByEmail(student.getStudentEmail());
		if (studentByEmail != null) {
			model.addAttribute("errMsg", "Student already exits");
			return "addEnquiry";
		} else {
			HttpSession session = req.getSession(false);
			Integer cid = (Integer) session.getAttribute("counsellorId");
			Counsellor counsellor = counsellorService.getById(cid).get();
			student.setCounsellor(counsellor);
			boolean saveStudent = studentService.saveStudent(student);
			if (saveStudent) {
				model.addAttribute("succMsg", "Student details saved successfully");
				return "addEnquiry";
			} else {
				model.addAttribute("errMsg", "Failed to save the student detaulis");
				return "addEnquiry";
			}
		}
	}

	@GetMapping("/view-enquiry")
	public String viewEnquiries(@RequestParam(name = "page", defaultValue = "1") int page, 
			HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession(false);
		if (session == null) {
			return "redirect:/logout";
		}
		Integer cid = (Integer) session.getAttribute("counsellorId");
		if(cid==null) {
			return "redirect:/logout";
		}
		Page<Student> allStudents = studentService.getAllStudents(cid,new Student(), page);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", allStudents.getTotalPages());
		model.addAttribute("students", allStudents.getContent());
		int pageSize = 5;
		int startingNumber = Math.max((page - 1) * pageSize, 0);
		int endingNumber = (int) Math.min(startingNumber + pageSize, allStudents.getTotalElements());
		model.addAttribute("startingNumber", startingNumber+1);
		model.addAttribute("endingNumber", endingNumber);
		model.addAttribute("totalRecords", allStudents.getTotalElements());
		model.addAttribute("student", new Student());
		return "viewEnquiries";
	}
	
	@PostMapping("/filtered-enquiries")
	public String filterEnquirie(@RequestParam(name = "page", defaultValue = "1") int page,
			@ModelAttribute("student") Student s,HttpServletRequest req,Model model) {
		
	    HttpSession session = req.getSession(false);
	    Integer cid = (Integer) session.getAttribute("counsellorId");
	    Page<Student> allStudents = studentService.getAllStudents(cid, s, page);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", allStudents.getTotalPages());
	    model.addAttribute("students", allStudents.getContent());
	    int size =5;
	    int startingNumber = Math.max((page - 1) * size, 0);
	    int endingNumber = (int) Math.min(startingNumber + size, allStudents.getTotalElements());
	    model.addAttribute("startingNumber", startingNumber + 1);
	    model.addAttribute("endingNumber", endingNumber);
	    model.addAttribute("totalRecords", allStudents.getTotalElements());
	    return "filteredEnquiries";
	}
	
	@GetMapping("/edit-enquiry")
	public String editEnquiry(@RequestParam(name="enqId") Integer id, HttpServletRequest req ,Model model) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return "redirect:/logout";
		}
		Integer cid = (Integer) session.getAttribute("counsellorId");
		if(cid==null) {
			return "redirect:/logout";
		}
		Student student = studentService.getStudent(id);
		model.addAttribute("student", student);
		return "editEnquiry";
	}
	
	@PostMapping("/edit-enquiry")
	public String editEnquiry(@ModelAttribute("student") Student s,HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("counsellorId");
		Counsellor c = new Counsellor();
		c.setCounsellorId(cid);
		s.setCounsellor(c);
		boolean saveStudent = studentService.saveStudent(s);
		if(saveStudent) {
			model.addAttribute("succMsg", "Student details updated successfully");
		}else {
			model.addAttribute("errMsg", "Failed to updated student details");
		}
		return "editEnquiry";
	}
	
}
