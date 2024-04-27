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

import silveira.carmo.guilherme.SpringBanco.model.ContaPoupanca;
import silveira.carmo.guilherme.SpringBanco.persistence.ContaPoupancaDao;

@Controller
public class ContaPoupancaController {
	
	@Autowired
	private ContaPoupancaDao cpDao;
	
	
	@RequestMapping(name = "contaPoupanca", value = "/contaPoupanca", 
			method = RequestMethod.GET)
	public ModelAndView ContaPoupancaGet(
			@RequestParam Map<String, String> param, ModelMap model ) {
		
		return new ModelAndView("contaPoupanca");
		
	}

	@RequestMapping(name = "contaPoupanca", value = "/contaPoupanca", 
				method = RequestMethod.POST)
		public ModelAndView ContaPoupancaPost(
				@RequestParam Map<String, String> param, ModelMap model ) {
		
		String numConta = param.get("numConta");
		String cmd = param.get("botao");
		String nomeCliente = param.get("nomeCliente");
		String valor = param.get("valor");
		String diaRendimento = param.get("diaRendimento");
		String erro = "";
		String saida = "";
		
		List<ContaPoupanca> contas = new ArrayList<>();
		ContaPoupanca cp = new ContaPoupanca();
		
		if(!cmd.equalsIgnoreCase("LISTAR") && !numConta.trim().isEmpty()) {
			cp.setNumConta(Integer.parseInt(numConta));
		}
		
		if((cmd.equalsIgnoreCase("CADASTRAR") || cmd.equalsIgnoreCase("ALTERAR")) 
			&& !nomeCliente.trim().isEmpty() && !diaRendimento.trim().isEmpty()) {
			cp.setNomeCliente(nomeCliente);
			cp.setDiaRendimento(Integer.parseInt(diaRendimento));
		}
		
		try {
			if (cmd.equalsIgnoreCase("CADASTRAR")) {
				cpDao.inserir(cp);
				saida = "Conta criada com sucesso";
				cp = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("ALTERAR")) {
				cpDao.atualizar(cp);
				saida = "Conta atualizada com sucesso";
				cp = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("EXCLUIR")) {
				cpDao.excluir(cp);
				saida = "Cliente excluido com sucesso";
				cp = new ContaPoupanca();
			}
			if (cmd.equalsIgnoreCase("BUSCAR")) {
				cp = cpDao.buscar(cp);
			}
			if (cmd.equalsIgnoreCase("LISTAR")) {
				contas = cpDao.listar();
				cp = new ContaPoupanca();
			}
			if(cmd.equalsIgnoreCase("SACAR") && !valor.trim().isEmpty()) {
				cpDao.sacar(cp, Float.parseFloat(valor));
				cpDao.buscar(cp);
				saida = "O saldo Atualizado é: R$" + cp.getSaldo();
				cp = new ContaPoupanca();
			}
			if(cmd.equalsIgnoreCase("DEPOSITAR") && !valor.trim().isEmpty()) {
				cpDao.depositar(cp, Float.parseFloat(valor));
				cpDao.buscar(cp);
				saida = "O saldo Atualizado é: R$" + cp.getSaldo();
				cp = new ContaPoupanca();
			}
			if(cmd.equalsIgnoreCase("CALCULAR RENDIMENTO")) {
				cpDao.calcularNovoSaldo(cp);
				cpDao.buscar(cp);
				saida = "O saldo Atualizado é: R$" + cp.getSaldo();
				cp = new ContaPoupanca();
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally { // RETORNO
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("contaPoupanca", cp);
			model.addAttribute("contas", contas);
		}
		
		
		return new ModelAndView("contaPoupanca");
		
	}
}
