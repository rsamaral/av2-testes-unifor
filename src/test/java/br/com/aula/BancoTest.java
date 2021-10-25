package br.com.aula;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import br.com.aula.exception.*;
import org.junit.Assert;
import org.junit.Test;

public class BancoTest {

	@Test
	public void deveCadastrarConta() throws ContaJaExistenteException, NumeroContaInvalidoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta);

		// Verificação
		assertEquals(1, banco.obterContas().size());
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNumeroRepetido() throws ContaJaExistenteException, NumeroContaInvalidoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta conta2 = new Conta(cliente2, 123, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNomeClienteRepetido() throws ContaJaExistenteException, NumeroContaInvalidoException {

		// Cenario
		Cliente cliente = new Cliente("Felipe");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Felipe");
		Conta conta2 = new Conta(cliente2, 132, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test(expected = NumeroContaInvalidoException.class)
	public void naoDeveCadastrarContaNumeroInvalido() throws ContaJaExistenteException, NumeroContaInvalidoException {

		// Cenario
		Cliente cliente = new Cliente("Luís");
		Conta conta1 = new Conta(cliente, 342, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Pedro");
		Conta conta2 = new Conta(cliente2, 1010, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test
	public void deveEfetuarTransferenciaEntrePoupancaECorrente() throws ContaSemSaldoException, ContaNaoExistenteException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Paulo");
		Conta contaOrigem = new Conta(cliente, 782, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("José");
		Conta contaDestino = new Conta(cliente2, 999, 0, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(782, 999, 100);

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}

	@Test(expected = ContaNaoExistenteException.class)
	public void deveVerificarExistenciaContaOrigem() throws ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Paulo");
		Conta contaOrigem = new Conta(cliente, 782, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem));

		// Ação
		banco.isContaExiste(583);

		Assert.fail();
	}

	@Test(expected = ContaNaoExistenteException.class)
	public void deveVerificarExistenciaContaDestino() throws ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Francisco");
		Conta contaDestino = new Conta(cliente, 345, 0, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaDestino));

		// Ação
		banco.isContaExiste(777);

		Assert.fail();
	}

	@Test(expected = ValorNegativoException.class)
	public void naoDevePermitirTransferirValorNegativo() throws ContaSemSaldoException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Pedro");
		Conta contaOrigem = new Conta(cliente, 456, 200, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Junior");
		Conta contaDestino = new Conta(cliente2, 112, 10, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		contaOrigem.debitar(-100);
		contaDestino.creditar(-100);

		// Verificação
		Assert.fail();
	}

}
