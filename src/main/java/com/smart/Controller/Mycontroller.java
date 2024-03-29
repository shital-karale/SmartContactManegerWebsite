package com.smart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepo;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Mycontroller {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userrepo;

	@RequestMapping("/")
	// @ResponseBody
	public String home(Model model) {

		model.addAttribute("title", "Home-smart contact manager");
		return "home";

	}

	
	@RequestMapping("/signup")

	public String singup(Model model) {

		model.addAttribute("title", "Register-smart contact manager");
		model.addAttribute("user", new User());
		return "signup";

	}
	
	//handler for registering
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result,

			@RequestParam(value="agreement",defaultValue="false") boolean agreement,
			HttpSession session,
			Model model) {
		try {
			
			if(!agreement)
			{
				System.out.println("Yuo haave no agee the terms and condition");
				throw new Exception("Yuo haave no agee the terms and condition");
			}
			
			if(result.hasErrors())
			{
				System.out.println("Result: "+result.toString());
				model.addAttribute("user",user);
				return "signup";
				
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("agrre: "+agreement);
			System.out.println("user: "+user);
			
			User res=this.userrepo.save(user);
			model.addAttribute("user",new User());
			session.setAttribute("message",new Message("Successfuly Resgister!!", "alert-Success"));
			return "signup";
			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message",new Message("Somthin went Wrong!!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
		
	}

	//Handler for custom login
	
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		return "login";
	}

}

