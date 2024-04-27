package silveira.carmo.guilherme.SpringBanco.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import silveira.carmo.guilherme.SpringBanco.model.ContaEspecial;
import silveira.carmo.guilherme.SpringBanco.persistence.ContaEspecialDao;

@Controller
public class ContaEspecialController {

	@Autowired
	private ContaEspecialDao ceDao;
	

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
		
		String numConta = param.get("numConta");
		String cmd = param.get("botao");
		String nomeCliente = param.get("nomeCliente");
		String valor = param.get("valor");
		String limite = param.get("limite");
		String erro = "";
		String saida = "";
		
		List<ContaEspecial> contas = new ArrayList<>();
		ContaEspecial ce = new ContaEspecial();
		
		if(!cmd.equalsIgnoreCase("LISTAR") && !numConta.trim().isEmpty()) {
			ce.setNumConta(Integer.parseInt(numConta));
		}
		
		if((cmd.equalsIgnoreCase("CADASTRAR") || cmd.equalsIgnoreCase("ALTERAR")) 
			&& !nomeCliente.trim().isEmpty() && !limite.trim().isEmpty()) {
			ce.setNomeCliente(nomeCliente);
			ce.setLimite(Float.parseFloat(limite));
		}
		
		try {
			if (cmd.equalsIgnoreCase("CADASTRAR")) {
				ceDao.inserir(ce);
				saida = "Conta criada com sucesso";
				ce = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("ALTERAR")) {
				ceDao.atualizar(ce);
				saida = "Conta atualizada com sucesso";
				ce = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("EXCLUIR")) {
				ceDao.excluir(ce);
				saida = "Cliente excluido com sucesso";
				ce = new ContaEspecial();
			}
			if (cmd.equalsIgnoreCase("BUSCAR")) {
				ce = ceDao.buscar(ce);
			}
			if (cmd.equalsIgnoreCase("LISTAR")) {
				contas = ceDao.listar();
				ce = new ContaEspecial();
			}
			if(cmd.equalsIgnoreCase("SACAR") && !valor.trim().isEmpty()) {
				ceDao.sacar(ce, Float.parseFloat(valor));
				ceDao.buscar(ce);
				saida = "O saldo Atualizado é: R$" + ce.getSaldo();
				ce = new ContaEspecial();
			}
			if(cmd.equalsIgnoreCase("DEPOSITAR") && !valor.trim().isEmpty()) {
				ceDao.depositar(ce, Float.parseFloat(valor));
				ceDao.buscar(ce);
				saida = "O saldo Atualizado é: R$" + ce.getSaldo();
				ce = new ContaEspecial();
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally { // RETORNO
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("contaEspecial", ce);
			model.addAttribute("contas", contas);
		}
		
		
		return new ModelAndView("contaEspecial");
		
	}
}
