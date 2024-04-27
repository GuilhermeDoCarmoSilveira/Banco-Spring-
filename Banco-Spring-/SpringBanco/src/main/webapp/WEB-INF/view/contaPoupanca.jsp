<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href='<c:url value = "./resources/style2.css"/>'>
<title>Conta Poupanca</title>
</head>
<body>
	<nav id=menu>
		<ul>
			<li><a href="index">Home</a>
			<li><a href="contaPoupanca">Conta Poupanca</a>
			<li><a href="contaEspecial">Conta Especial</a>
		</ul>
	</nav>

	<div align="center" class="container">
		<form action="contaPoupanca" method="post">
			<p class="title">
				<b>Crud - Conta Poupanca</b>
			</p>
			<table>
				<tr>
					<td colspan="3"><input class="id_input_data" type="number"
						pattern="[0-9]*" id="numConta" name="numConta" placeholder="Numero da Conta"
						value='<c:out value="${contaPoupanca.numConta }"></c:out>'></td>
					<td><input type="submit" id="botao" name="botao"
						value="Buscar"></td>
				</tr>
				<tr>
					<td colspan="4"><input class="input_data" type="text"
						id="nomeCliente" name="nomeCliente" placeholder="Nome do Cliente"
						value='<c:out value="${contaPoupanca.nomeCliente }"></c:out>'>
					</td>
				</tr>
				
				<tr>
					<td colspan="3"><input class="id_input_data" type="number"
						pattern="[0-9]*" id="diaRendimento" name="diaRendimento" placeholder="Dia do Rendimento"
						value='<c:out value="${contaPoupanca.diaRendimento }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td><input type="submit" id="botao" name="botao"
						value="Cadastrar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Alterar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Excluir"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Listar"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div align="center" class="container">
		<form action="contaPoupanca" method="post">
			<p class="title">
				<b>Acoes (Depositar e sacar) - Conta Poupanca</b>
			</p>
			<table>
				<tr>
					<td colspan="3"><input class="id_input_data" type="number"
						pattern="[0-9]*" id="numConta" name="numConta" placeholder="Numero da Conta">
					</td>		
				</tr>
				
				<tr>
					<td colspan="3"><input class="id_input_data" type="text"
						pattern="[0-9.,]*" id="valor" name="valor" placeholder="valor">
					</td>
				</tr>
				<tr>
					<td><input type="submit" id="botao" name="botao"
						value="Sacar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Depositar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Calcular Rendimento"></td>
				</tr>
			</table>
		</form>
	</div>

	<div align="center">
		<c:if test="${not empty erro }">
			<h2>
				<b> <c:out value="${erro }" />
				</b>
			</h2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida }">
			<h3>
				<b> <c:out value="${saida }" />
				</b>
			</h3>
		</c:if>
	</div>
	<br />
	<br />
	<br />
	<div align="center">
		<c:if test="${not empty contas }">
			<table class="table_round">
				<thead>
					<tr>
						<th class="lista">Nome Cliente</th>
						<th class="lista">Numero conta</th>
						<th class="lista_ultimoelemento">Dia do Rendimento</th>
						<th class="lista_ultimoelemento">Saldo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${contas }">
						<tr>
							<td class="lista"><c:out value="${c.nomeCliente } " /></td>
							<td class="lista"><c:out value="${c.numConta } " /></td>
							<td class="lista"><c:out value="${c.diaRendimento } " /></td>
							<td class="lista"><c:out value="${c.saldo } " /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>