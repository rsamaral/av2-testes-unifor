package br.com.aula;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import br.com.aula.exception.ContaSemSaldoException;
import br.com.aula.exception.ValorNegativoException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ContaTest {

	@Test
	public void deveCreditar() {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 10, TipoConta.CORRENTE);

		// Ação
		c.creditar(5);

		// Verificação
		assertEquals(15, c.getSaldo());
		assertThat(c.getSaldo(), is(15));
	}

	@Test(expected = ContaSemSaldoException.class)
	public void naoDevePermitirPoupancaComSaldoNegativo() throws ContaSemSaldoException, ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Lucas");
		Conta contaPoupanca = new Conta(cliente, 212, 100, TipoConta.POUPANCA);

		// Ação
		contaPoupanca.debitar(110);

		// Verificação
		assertEquals(-10, contaPoupanca.getSaldo());
		assertThat(contaPoupanca.getSaldo(), is(-10));

		Assert.fail();
	}

}
