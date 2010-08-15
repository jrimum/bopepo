/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 16/09/2009 - 00:05:18
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a Licença Apache, Versão 2.0 ("LICENÇA"); você não pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÇA. Você pode obter uma 
 * cópia desta LICENÇA em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigência legal ou acordo por escrito, a distribuição de software sob esta 
 * LICENÇA se dará “COMO ESTÁ”, SEM GARANTIAS OU CONDIÇÕES DE QUALQUER TIPO, sejam 
 * expressas ou tácitas. Veja a LICENÇA para a redação específica a reger permissões 
 * e limitações sob esta LICENÇA.
 * 
 * Criado em: 16/09/2009 - 00:05:18
 * 
 */
package org.jrimum.bopepo.exemplo.banco;

import static org.jrimum.utilix.text.DateFormat.DDMMYYYY_B;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;
/**
 * 
 * <p>
 * Padroniza a instanciação dos objetos comuns e disponibiliza métodos abstratos necessários
 * para a geração de boletos diferenciados.
 * </p>
 * <p>
 * A subclasse deve implementar esses métodos de acordo com o banco e campo live usados
 * </p>
 * 
 * @author <a href="mailto:romulomail@gmail.com">Rômulo Augusto</a>
 * 
 * @version 0.2
 */
public abstract class AbstractBoletoExemplo {
	
	private static Collection<AbstractBoletoExemplo> boletosExemplo = new ArrayList<AbstractBoletoExemplo>();
	
	public static void newInstance(Class<? extends AbstractBoletoExemplo> exemploClass) {
		
		AbstractBoletoExemplo boletoExemplo = null;
		
		try {
			
			boletoExemplo = exemploClass.newInstance();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
			
			IllegalArgumentException iae = new IllegalArgumentException("A classe [" + exemploClass + "] não pode ser instaciada");
			iae.initCause(e);
			throw iae;
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			
			IllegalArgumentException iae = new IllegalArgumentException("A classe [" + exemploClass + "] não permite ser instanciada");
			iae.initCause(e);
			throw iae;
		}
		
		boletosExemplo.add(boletoExemplo);
	}

	protected abstract String getNossoNumero();
	
	protected abstract Carteira getCarteira();
	
	protected abstract BancosSuportados getBancoSuportado();
	
	/**
	 * <p>
	 * Por padrão tem o mesmo valor do nosso numero, podendo ser sobrescrito para mudar 
	 * este valor.
	 * </p>
	 * 
	 * @return
	 */
	protected String getNumeroDoDocumento() {
		return getNossoNumero();
	}
	
	public Boleto execute() {
		return execute(true);
	}

	public Boleto execute(boolean comSacadorAvalista) {
		
		Cedente cedente = crieCedente();
		Sacado sacado = crieSacado();
		SacadorAvalista sacadorAvalista = null;
		
		if (comSacadorAvalista) {
			sacadorAvalista = crieSacadorAvalista();
		}
		
		Titulo titulo = crieTitulo(cedente, sacado, sacadorAvalista);
		
		Boleto boleto = crieBoleto(titulo);
		
		return boleto;
	}

	private Boleto crieBoleto(Titulo titulo) {
		
		Boleto boleto = new Boleto(titulo);
		
		boleto.setLocalPagamento("Pagável preferencialmente na Rede X ou em qualquer Banco até o Vencimento.");
		
		boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor cobrado não é o esperado, aproveite o DESCONTÃO!");
		
		boleto.setInstrucao1("PARA PAGAMENTO 1 até Hoje não cobrar nada!");
		boleto.setInstrucao2("PARA PAGAMENTO 2 até Amanhã Não cobre!");
		boleto.setInstrucao3("PARA PAGAMENTO 3 até Depois de amanhã, OK, não cobre.");
		boleto.setInstrucao4("PARA PAGAMENTO 4 até 04/xx/xxxx de 4 dias atrás COBRAR O VALOR DE: R$ 01,00");
		boleto.setInstrucao5("PARA PAGAMENTO 5 até 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
		boleto.setInstrucao6("PARA PAGAMENTO 6 até 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
		boleto.setInstrucao7("PARA PAGAMENTO 7 até xx/xx/xxxx COBRAR O VALOR QUE VOCÊ QUISER!");
		boleto.setInstrucao8("APÓS o Vencimento, Pagável Somente na Rede X.");
		
		return boleto;
	}

	private Titulo crieTitulo(Cedente cedente, Sacado sacado, SacadorAvalista sacadorAvalista) {
		
		ContaBancaria contaBancariaDoCedente = cedente.getContasBancarias().iterator().next();
		Titulo titulo = null;
		
		if (sacadorAvalista != null) {
			titulo = new Titulo(contaBancariaDoCedente, sacado, cedente, sacadorAvalista);
			
		} else {
			titulo = new Titulo(contaBancariaDoCedente, sacado, cedente);
		}
		
		titulo.setNumeroDoDocumento(getNumeroDoDocumento());
		titulo.setNossoNumero(getNossoNumero());
		titulo.setDigitoDoNossoNumero("5");
		titulo.setValor(BigDecimal.TEN);
		titulo.setDataDoDocumento(DDMMYYYY_B.parse("19/09/2009"));
		titulo.setDataDoVencimento(DDMMYYYY_B.parse("19/09/2009"));
		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(EnumAceite.A);
		titulo.setDesconto(BigDecimal.ONE);
		
		return titulo;
	}

	private Cedente crieCedente() {
		
		Cedente cedente = new Cedente("PROJETO JRimum", "00.000.208/0001-00");
	
		ContaBancaria contaBancaria = new ContaBancaria(getBancoSuportado().create());
		
		Agencia agencia = new Agencia(1234, "1");
		contaBancaria.setAgencia(agencia);
		
		contaBancaria.setCarteira(getCarteira());
		
		NumeroDaConta numeroDaConta = new NumeroDaConta(6789, "0");
		contaBancaria.setNumeroDaConta(numeroDaConta);
		
		cedente.addContaBancaria(contaBancaria);
		
		return cedente;
	}

	private Sacado crieSacado() {
		
		Sacado sacado = new Sacado("JRimum Developer", "111.111.111-11");
		
		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.RN);
		endereco.setLocalidade("Natal");
		endereco.setCep("59064-120");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua JRimum");
		endereco.setNumero("2332");
		
		sacado.addEndereco(endereco);
		
		return sacado;
	}

	private SacadorAvalista crieSacadorAvalista() {
	
		SacadorAvalista sacadorAvalista = new SacadorAvalista("JavaRN", "00.000.000/0001-91");
		
		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.PB);
		endereco.setLocalidade("João Pessoa");
		endereco.setCep("59064-120");
		endereco.setBairro("Centro");
		endereco.setLogradouro("Rua JRimum Paraiba");
		endereco.setNumero("3223");
		
		sacadorAvalista.addEndereco(endereco);
		
		return sacadorAvalista;
	}

	public static Collection<AbstractBoletoExemplo> getBoletosExemplo() {
		return boletosExemplo;
	}
}
