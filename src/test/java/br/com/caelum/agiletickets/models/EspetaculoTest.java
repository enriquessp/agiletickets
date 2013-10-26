package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;


import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.Vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(5, 3));
	}
	
	@Test
	public void DeveCriarSessaoDiariaMesmoInicioFim() {
		
		Espetaculo show = new Espetaculo();
		LocalDate inicio = new LocalDate(2013,10,20);
		LocalDate fim = new LocalDate(2013,10,20);
		LocalTime horario = new LocalTime(19,0);
		
		List<Sessao> sessoes = show.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		assertEquals(1, sessoes.size());
		assertEquals(inicio.toDateTime(horario), sessoes.get(0).getInicio());
		
	}
	
	@Test
	public void DeveCriarDuasSessoesDiariasHojeEAmanha() {
		
		Espetaculo show = new Espetaculo();
		LocalDate inicio = new LocalDate(2013,10,20);
		LocalDate fim = inicio.plusDays(1);
		LocalTime horario = new LocalTime(19,0);
		
		List<Sessao> sessoes = show.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		
		assertEquals(2, sessoes.size());
		assertEquals(inicio.toDateTime(horario), sessoes.get(0).getInicio());
		assertEquals(fim.toDateTime(horario), sessoes.get(1).getInicio());
		
	}
	
	@Test (expected = FimMenorQueInicioException.class)
	public void DeveLancarExceptionSeFimMenorQueInicio() {
		
		Espetaculo show = new Espetaculo();
		LocalDate inicio = new LocalDate(2013,10,20);
		LocalDate fim = inicio.minusDays(1);
		LocalTime horario = new LocalTime(19,0);
		
		show.criaSessoes(inicio, fim, horario, Periodicidade.DIARIA);
		
	}
	
//	@Test
//	public void DeveCriarUmaSessaoSemanalSeInicioEFimMenorQueUmaSemana() {
//		Espetaculo show = new Espetaculo();
//		LocalDate inicio = new LocalDate(2013,10,20);
//		LocalDate fim = inicio.plusDays(1);
//		LocalTime horario = new LocalTime(19,0);
//		
//		List<Sessao> sessoes = show.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
//		
//		assertEquals(1, sessoes.size());
//		assertEquals(inicio.toDateTime(horario), sessoes.get(0).getInicio());
//		assertEquals(fim.toDateTime(horario), sessoes.get(1).getInicio());		
//	}
//	
//	@Test
//	public void DeveCriarDuasSessoesSemanaisSeInicioEFimMaiorQueUmaSemana(){
//		Espetaculo show = new Espetaculo();
//		LocalDate inicio = new LocalDate(2013,10,20);
//		LocalDate fim = inicio.plusDays(7);
//		LocalTime horario = new LocalTime(19,0);
//		
//		List<Sessao> sessoes = show.criaSessoes(inicio, fim, horario, Periodicidade.SEMANAL);
//		
//		assertEquals(2, sessoes.size());
//		assertEquals(inicio.toDateTime(horario), sessoes.get(0).getInicio());
//		assertEquals(fim.toDateTime(horario), sessoes.get(1).getInicio());				
//	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
}
