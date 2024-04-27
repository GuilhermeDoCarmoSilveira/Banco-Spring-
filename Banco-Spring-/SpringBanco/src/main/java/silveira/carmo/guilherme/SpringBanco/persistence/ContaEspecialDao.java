package silveira.carmo.guilherme.SpringBanco.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import silveira.carmo.guilherme.SpringBanco.model.ContaEspecial;

@Repository
public class ContaEspecialDao implements IContaOp<ContaEspecial>, ICrud<ContaEspecial> {

	@Autowired
	private GenericDao gDao;

	@Override
	public void inserir(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO contaBancaria VALUES (?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, ce.getNomeCliente());
		ps.setInt(2, ce.getNumConta());
		ps.setFloat(3, 0);
		ps.execute();

		sql = "INSERT INTO contaEspecial VALUES (?, ?)";
		ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNumConta());
		ps.setFloat(2, ce.getLimite());
		ps.execute();

		ps.close();
		c.close();

	}

	@Override
	public void atualizar(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE contaBancaria SET nomeCliente = ? where numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, ce.getNomeCliente());
		ps.setInt(2, ce.getNumConta());
		ps.execute();

		sql = "UPDATE contaEspecial SET limite = ? where numConta = ?";
		ps = c.prepareStatement(sql);
		ps.setFloat(1, ce.getLimite());
		ps.setInt(2, ce.getNumConta());
		ps.execute();

		ps.close();
		c.close();

	}

	@Override
	public void excluir(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE contaEspecial WHERE numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNumConta());
		ps.execute();

		sql = "DELETE contaBancaria WHERE numConta = ?";
		ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNumConta());
		ps.execute();

		ps.close();
		c.close();

	}

	@Override
	public ContaEspecial buscar(ContaEspecial ce) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = """
				SELECT *
				FROM contaEspecial ce, contaBancaria cb
				WHERE ce.numConta = cb.numConta
				and cb.numConta = ?
				""";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ce.getNumConta());
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			ce.setNumConta(rs.getInt("numConta"));
			ce.setNomeCliente(rs.getString("nomeCliente"));
			ce.setSaldo(rs.getFloat("saldo"));
			ce.setLimite(rs.getFloat("limite"));
		}

		rs.close();
		ps.close();
		c.close();

		return ce;
	}

	@Override
	public List<ContaEspecial> listar() throws SQLException, ClassNotFoundException {
		List<ContaEspecial> contas = new ArrayList<>();

		Connection c = gDao.getConnection();
		String sql = """
				SELECT *
				FROM contaEspecial ce, contaBancaria cb
				WHERE ce.numConta = cb.numConta
				""";

		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ContaEspecial ce = new ContaEspecial();
			ce.setNumConta(rs.getInt("numConta"));
			ce.setNomeCliente(rs.getString("nomeCliente"));
			ce.setSaldo(rs.getFloat("saldo"));
			ce.setLimite(rs.getFloat("limite"));
			contas.add(ce);
		}
		rs.close();
		ps.close();
		c.close();

		return contas;
	}

	@Override
	public void sacar(ContaEspecial ce, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE contaBancaria SET saldo = saldo - ? where numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, ce.getNumConta());
		ps.execute();
		
		c.close();
		ps.close();

	}

	@Override
	public void depositar(ContaEspecial ce, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE contaBancaria SET saldo = saldo + ? where numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, ce.getNumConta());
		ps.execute();
		
		c.close();
		ps.close();

	}

}
