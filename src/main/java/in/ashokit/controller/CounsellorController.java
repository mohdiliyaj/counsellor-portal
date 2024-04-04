package in.ashokit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.entity.Counsellor;
import in.ashokit.service.ICounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	private ICounsellorService counsellorService;
	
	public CounsellorController(ICounsellorService counsellorService) {
		this.counsellorService = counsellorService;
	}
	
	@GetMapping("/")
	public String loadLogin(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "index";
	}
	
	@PostMapping("/login")
	public String loginHandler(@ModelAttribute("counsellor") Counsellor counsellor, HttpServletRequest req, Model model) {
		Counsellor loginCheck = counsellorService.loginCheck(counsellor);
		if(loginCheck == null) {
			model.addAttribute("errMsg", "Invalid Credentials");
			return "index";
		}
		HttpSession session = req.getSession(true);
		session.setAttribute("counsellorId", loginCheck.getCounsellorId());
		return "redirect:dashboard";
	}
	
	@GetMapping("/register")
	public String loadRegister(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerHandler(@ModelAttribute Counsellor counsellor, Model model) {
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getCounsellorEmail());
		if(byEmail != null) {
			model.addAttribute("errMsg", "User already exits");
			return "register";
		}else {
			boolean saveCounsellor = counsellorService.saveCounsellor(counsellor);
			if(!saveCounsellor) {
				model.addAttribute("errMsg", "Registration failed..! Try again");
				return "register";
			}else {
				model.addAttribute("succMsg", "Registration Successfull");
				return "register";
			}
		}
	}
	
	@GetMapping("/dashboard")
	public String builDashboard(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return "redirect:/logout";
		}
		Integer cid = (Integer) session.getAttribute("counsellorId");
		if(cid==null) {
			return "redirect:/logout";
		}
		Integer id =(Integer) session.getAttribute("counsellorId");
		DashboardResponse dashboard = counsellorService.buildDashboard(id);
		model.addAttribute("dashboard", dashboard);
		return "dashboard";
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if(session != null) {
			session.invalidate();			
		}
		return "redirect:/";
	}
	
	@GetMapping("/forget-password")
	public String forgetPassword(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "forget.html";
	}
	
	@PostMapping("/forget-passwords")
	public String handleForgetPassword(@ModelAttribute("counsellor") Counsellor counsellor, Model model) {
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getCounsellorEmail());
		if(byEmail != null) {
			if(byEmail.getCounsellorId() != null) {
				boolean sendForgetPasswordEmail = counsellorService.sendForgetPasswordEmail(byEmail);
				if(sendForgetPasswordEmail) {					
					model.addAttribute("succMsg", "Password sent to the mail");			
				}else {
					model.addAttribute("errMsg", "Error occured while sending mail");
				}
			}
		}else {
			model.addAttribute("errMsg", "Invalid email");
		}
		return "forget";
	}
	
	
	
	
	
}
