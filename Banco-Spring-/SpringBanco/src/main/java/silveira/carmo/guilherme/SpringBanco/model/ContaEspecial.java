package silveira.carmo.guilherme.SpringBanco.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContaEspecial extends ContaBancaria {
	private float limite;
}
