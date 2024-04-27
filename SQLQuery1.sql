create database BancoSpring

go

use BancoSpring

go

create table contaBancaria (
	nomeCliente	 varchar(150)	not null,
	numConta	 int			not null primary key,
	saldo		 decimal(7,2)	not null,
)

go

create table contaPoupanca(
	numConta int not null primary key references contaBancaria (numConta), 
	diaRendimento	int not null,


)

go

create table contaEspecial(
	numConta int not null primary key references contaBancaria (numConta), 
	limite	decimal(7,2) not null,
)

go

drop trigger t_controlaSaldo
create trigger t_controlaSaldo on contaBancaria
after update 
as
begin

	declare @numConta int,
			@limite	decimal(7,2),
			@novoSaldo decimal(7,2),
			@saque decimal(7,2)
			

	set @numConta = (select numConta from inserted)

	set @saque = (select saldo from deleted) - (select saldo from inserted)

	if((select numConta from contaEspecial where numConta = @numConta) is not null)
	begin
		
			set @limite = (select limite from contaEspecial where numConta = @numConta)


			if(@saque > (@limite + (select saldo from deleted)))
			begin
				rollback transaction 
				raiserror('Saque Negado, o valor solicitado esta ultrapassando o limite', 16, 1)
			end

	end
	else
	begin
			if((select saldo from inserted)< 0)
			begin
				rollback transaction 
				raiserror('Saque Negado, Saldo indisponivel', 16, 1)
			end
	end
end


select * from contaBancaria
select * from contaPoupanca