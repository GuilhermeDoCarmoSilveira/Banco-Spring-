package silveira.carmo.guilherme.SpringBanco.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import silveira.carmo.guilherme.SpringBanco.model.ContaPoupanca;

@Repository
public class ContaPoupancaDao implements ICrud<ContaPoupanca>, IContaOp<ContaPoupanca> {

	@Autowired
	private GenericDao gDao;

	@Override
	public void inserir(ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO contaBancaria VALUES (?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cp.getNomeCliente());
		ps.setInt(2, cp.getNumConta());
		ps.setFloat(3, 0);
		ps.execute();
		
		sql = "INSERT INTO contaPoupanca VALUES (?, ?)";
		ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNumConta());
		ps.setInt(2, cp.getDiaRendimento());
		ps.execute();
		
		ps.close();
		c.close();
	}

	@Override
	public void atualizar(ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE contaBancaria SET nomeCliente = ? where numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cp.getNomeCliente());
		ps.setInt(2, cp.getNumConta());
		ps.execute();
		
		sql = "UPDATE contaPoupanca SET diaRendimento = ? where numConta = ?";
		ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getDiaRendimento());
		ps.setInt(2, cp.getNumConta());
		ps.execute();
		
		ps.close();
		c.close();
		
	}

	@Override
	public void excluir(ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE contaPoupanca WHERE numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNumConta());
		ps.execute();
		
		sql = "DELETE contaBancaria WHERE numConta = ?";
		ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNumConta());
		ps.execute();
		
		ps.close();
		c.close();
	}

	@Override
	public ContaPoupanca buscar(ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = """
				SELECT * 
				FROM contaPoupanca cp, contaBancaria cb 
				WHERE cp.numConta = cb.numConta 
				and cb.numConta = ?
				""";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getNumConta());
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			cp.setNumConta(rs.getInt("numConta"));
			cp.setNomeCliente(rs.getString("nomeCliente"));
			cp.setSaldo(rs.getFloat("saldo"));
			cp.setDiaRendimento(rs.getInt("diaRendimento"));
		}
		
		rs.close();
		ps.close();
		c.close();
		
		return cp;
	}

	@Override
	public List<ContaPoupanca> listar() throws SQLException, ClassNotFoundException {
		
		List<ContaPoupanca> contas = new ArrayList<>();
		
		Connection c = gDao.getConnection();
		String sql = """
				SELECT * 
				FROM contaPoupanca cp, contaBancaria cb 
				WHERE cp.numConta = cb.numConta
				""";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			ContaPoupanca cp = new ContaPoupanca();
			cp.setNumConta(rs.getInt("numConta"));
			cp.setNomeCliente(rs.getString("nomeCliente"));
			cp.setSaldo(rs.getFloat("saldo"));
			cp.setDiaRendimento(rs.getInt("diaRendimento"));
			contas.add(cp);
		}
		rs.close();
		ps.close();
		c.close();
		
		return contas;
	}

	@Override
	public void sacar(ContaPoupanca cp, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE contaBancaria SET saldo = saldo - ? where numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, cp.getNumConta());
		ps.execute();
		
		c.close();
		ps.close();
	}

	@Override
	public void depositar(ContaPoupanca cp, float valor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE contaBancaria SET saldo = saldo + ? where numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setFloat(1, valor);
		ps.setInt(2, cp.getNumConta());
		ps.execute();
		
		c.close();
		ps.close();
	}
	
	public void calcularNovoSaldo (ContaPoupanca cp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE contaBancaria SET saldo = saldo + (saldo * ?) where numConta = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setDouble(1, 0.2);
		ps.setInt(2, cp.getNumConta());
		ps.execute();
		
		c.close();
		ps.close();
	}




}
