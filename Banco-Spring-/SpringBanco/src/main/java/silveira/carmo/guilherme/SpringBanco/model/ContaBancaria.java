package silveira.carmo.guilherme.SpringBanco.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContaBancaria {
	
	protected String nomeCliente;
	protected int numConta;
	protected float saldo;
	
}
