package silveira.carmo.guilherme.SpringBanco.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public class ContaEspecialController {

	@Autowired
	

	@RequestMapping(name = "contaEspecial", value = "/contaEspecial", 
			method = RequestMethod.GET)
	public ModelAndView ContaEspecialGet(
			@RequestParam Map<String, String> param, ModelMap model ) {
		
		return new ModelAndView("contaEspecial");
	}

	@RequestMapping(name = "contaEspecial", value = "/contaEspecial", 
				method = RequestMethod.POST)
		public ModelAndView ContaEspecialPost(
				@RequestParam Map<String, String> param, ModelMap model ) {
		
		return new ModelAndView("contaEspecial");
		
	}
}
