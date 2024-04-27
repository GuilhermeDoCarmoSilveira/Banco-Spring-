package silveira.carmo.guilherme.SpringBanco.persistence;

import java.sql.SQLException;

public interface IContaOp<T> {
	
	public void sacar (T t, float valor) throws SQLException, ClassNotFoundException;
	public void depositar (T t, float valor) throws SQLException, ClassNotFoundException;
}
