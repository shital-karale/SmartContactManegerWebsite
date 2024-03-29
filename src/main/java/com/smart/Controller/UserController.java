package com.smart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.UserRepo;
import com.smart.entities.Contact;
import com.smart.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepo userrepo;
	
	//method for add common data
	@ModelAttribute
	public String addCommonData(Model model,  Principal principal) {
		String username=principal.getName();
		System.out.println("Uname: "+username);
		
		User user = userrepo.getUserNameByUserName(username);
		System.out.println("Uname: "+user);
		model.addAttribute("user",user);
		return "user/dashbord";
	}
	
	//dashbord home
	@RequestMapping("/index")
	public String userDashbord(Model model,  Principal principal)
	{
		model.addAttribute("title","User Dashbord");
		return "user/dashbord";
	}
	
	//handler to add contact form
	@GetMapping("/add-contact")
	public String addContactForm(Model model) {
		
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact",new Contact());
		return "user/add_contact";
	}

}
