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
		Assert.assertEquals("Rodrigo", nomeAlunoTeste);
		
		// Usar dessa forma para testar
		Assert.assertThat(nomeAlunoTeste, CoreMatchers.is("Rodrigo"));
		
		//Usar o Erro Collector 
		error.checkThat(nomeAlunoTeste, CoreMatchers.is("Rodrigo"));
		

		// Verificar se o nome contém apenas letras e espaços
		//Assert.assertTrue(nomeAluno.matches("[A-Za-zÀ-ú ]+"));
		
		// Verificar se o nome não é nulo
		//Assert.assertNotNull(nomeAluno);
		// Verificar se o nome não é Vazio
		//Assert.assertNotEquals("", nomeAluno);
	}
	
	
}
