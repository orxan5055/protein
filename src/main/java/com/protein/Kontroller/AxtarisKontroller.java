package com.protein.Kontroller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.protein.domain.Protein;
import com.protein.domain.User;
import com.protein.servis.ProteinServis;
import com.protein.servis.UserService;

@Controller
public class AxtarisKontroller {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProteinServis proteinServis;

	@RequestMapping("/searchByKategoriya")
	public String searchByKategoriya(
			@RequestParam("kategoriya") String kategoriya,
			Model model, Principal principal
			){
		if(principal!=null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		String classActiveKategoriya = "active"+kategoriya;
		classActiveKategoriya = classActiveKategoriya.replaceAll("\\s+", "");
		classActiveKategoriya= classActiveKategoriya.replaceAll("&", "");
		model.addAttribute(classActiveKategoriya, true);
		
		List<Protein> proteinList = proteinServis.findByKategoriya(kategoriya);
		
		if (proteinList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "proteinTemplate";
		}
		
		model.addAttribute("proteinList", proteinList);
		
		return "proteinTemplate";
	}
	@RequestMapping("/searchItem")
	public String searchItem(
			@ModelAttribute("basliq") String basliq,
			Principal principal,Model model
			)
	{
		if(principal!=null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		List<Protein> proteinList = proteinServis.Search(basliq);
		
		if (proteinList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "proteinTemplate";
		}
		model.addAttribute("proteinList",proteinList);
		
		return "proteinTemplate";
	}
	
	
}
