package mentoria_java_servicos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import mentoria_java_entidades.Aluno;
import mentoria_java_entidades.CadastroAluno;

public class AlunoServiceTeste {


	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Before
/*	public  {
		CadastroAluno cadastro = new CadastroAluno();
	}*/
	
	@Test
	public void testeCadastroNomeAluno() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Aluno aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");

		CadastroAluno cadastro = new CadastroAluno();

		String nomeAluno = aluno.getNomeAluno();

		// Cadastrar o nome do aluno
		String nomeAlunoTeste = cadastro.cadastrarNomeAluno(nomeAluno);

		// Verificar se o nome retornado é igual ao nome esperado
		//Assert.assertEquals("Rodrigo", nomeAlunoTeste);

		// Usar dessa forma para testar
		//Assert.assertThat(nomeAlunoTeste, CoreMatchers.is("Rodrigo"));

		//Usar o Erro Collector 
		error.checkThat(nomeAlunoTeste, CoreMatchers.is("Rodrigo"));
		

		// Verificar se o nome contém apenas letras e espaços
		Assert.assertTrue(nomeAluno.matches("[A-Za-zÀ-ú ]+"));
		
		// Verificar se o nome não é nulo
		Assert.assertNotNull(nomeAlunoTeste);
		// Verificar se o nome não é Vazio
		Assert.assertNotEquals("", nomeAlunoTeste);
	}

	@Test
	public void testeCadastroDataNascimento() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Aluno aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");

		CadastroAluno cadastro = new CadastroAluno();
		// Pegando o valor da data de nascimento
        LocalDate dataNascimento = aluno.getDataNascimento();
        // Formando o valor da data para o formato dd/MM/yyyy
        String dataFormatada = dataNascimento.format(formatter);

		// Cadastrar a data de nascimento no formato correto
		LocalDate dataNascimentoAluno = cadastro.cadastrarDataNascimento(dataFormatada);
		
		// Verificar se a data não é nulo
		Assert.assertNotNull(dataNascimentoAluno);
		// Verificar se o data não é vazia
		Assert.assertNotEquals("", dataNascimentoAluno);

		//Usar o Erro Collector 
		error.checkThat(dataNascimentoAluno, CoreMatchers.is(LocalDate.parse("08/02/1986", formatter)));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testeCadastroNotaAluno() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Aluno aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");

		CadastroAluno cadastro = new CadastroAluno();
		// Pegando o valor da nota do Aluno
        double notaAluno = aluno.getNota1();
        String pergunta = "Digite a nota 1 (entre 0 e 10): ";
      
		// Cadastrar a data de nascimento no formato correto
		double notaAlunoTeste = cadastro.cadastrarNota(pergunta, notaAluno);

		Assert.assertEquals(notaAluno, notaAlunoTeste, 0.0001);
		//Assert.assertNotSame("", notaAlunoTeste);
	}

	@Test
	public void testeCadastrarClasse() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Aluno aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");

		CadastroAluno cadastro = new CadastroAluno();
		// Pegando o valor da classe do Aluno
        int classeAlunoInt = aluno.getClasse();
        // Convertendo de int pra string
        String classeAluno = Integer.toString(classeAlunoInt);
		
		// Cadastrar a classe do aluno
		int classeAlunoConvertido = cadastro.cadastrarClasse(classeAluno);

		// Verificação com Assert se o valor não e nulo;
		Assert.assertNotNull(classeAlunoConvertido);
		// Verificação com Assert se o valor vem diferente;
		Assert.assertNotSame(7, classeAlunoConvertido);
		// Verificação com Assert se o valor esperado e atual;
		Assert.assertSame(8, classeAlunoConvertido);
	}

	@Test
	public void testeCadastrarTurma() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Aluno aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");

		CadastroAluno cadastro = new CadastroAluno();
		// Pegando o valor da Turma do Aluno
        String turmaAluno = aluno.getTurma();
       
		// Cadastrar a turma do Aluno
		String turmaAlunoTeste = cadastro.cadastrarTurma(turmaAluno);

		// Verificação com Assert se o valor não e nulo;
		Assert.assertNotNull(turmaAlunoTeste);
		// Verificação com Assert se o valor vem diferente;
		Assert.assertNotSame("G", turmaAlunoTeste);
		// Verificação com Assert se o valor esperado e atual;
		Assert.assertSame("A", turmaAlunoTeste);
	}

	@Test
	public void testeCalculaMedia() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Aluno aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");

		double media = aluno.calcularMedia(aluno.getNota1(), aluno.getNota2());
		Assert.assertNotNull(media);
		Assert.assertEquals(8.0, media, 0.01);

	}

	@Test
	public void testeDeterminarSituacao() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Aluno aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");

		double media = 8.0;
		String situacao = aluno.determinarSituacao(aluno.getSituacao(), media);
		
		System.out.println(situacao);
		
        Assert.assertNotNull(situacao);
        Assert.assertEquals("Aprovado", situacao);

	}

}
