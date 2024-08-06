package mentoria_java_servicos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runners.MethodSorters;

import mentoria_java_entidades.Aluno;
import mentoria_java_entidades.CadastroAluno;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlunoServiceTeste {

	public CadastroAluno cadastro;
	public AlunoService alunoService;
	public Aluno aluno;
	public DateTimeFormatter formatter;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Before
	public void cadastroAlunoBefore() {
		cadastro = new CadastroAluno();
		formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		aluno = new Aluno("Rodrigo", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 8, "A");
		alunoService = new AlunoService();
	}

	@Test
	public void teste1_CadastroNomeAluno() {

		// Pegar o nome do aluno criado
		String nomeAluno = aluno.getNomeAluno();
		// Cadastrar o nome do aluno
		String nomeAlunoTeste = CadastroAluno.cadastrarNomeAluno(nomeAluno);
		// Verificar se o nome retornado é igual ao nome esperado
		// Assert.assertEquals("Rodrigo", nomeAlunoTeste);

		// Usar dessa forma para testar
		// Assert.assertThat(nomeAlunoTeste, CoreMatchers.is("Rodrigo"));

		// Usar o Erro Collector
		error.checkThat(nomeAlunoTeste, CoreMatchers.is("Rodrigo"));

		// Verificar se o nome contém apenas letras e espaços
		Assert.assertTrue(nomeAluno.matches("[A-Za-zÀ-ú ]+"));

		// Verificar se o nome não é nulo
		Assert.assertNotNull(nomeAlunoTeste);
		// Verificar se o nome não é Vazio
		Assert.assertNotEquals("", nomeAlunoTeste);
	}

	@Test
	public void teste2_CadastroDataNascimento() {
		// Pegando o valor da data de nascimento
		LocalDate dataNascimento = aluno.getDataNascimento();
		// Formando o valor da data para o formato dd/MM/yyyy
		String dataFormatada = dataNascimento.format(formatter);

		// Cadastrar a data de nascimento no formato correto
		LocalDate dataNascimentoAluno = CadastroAluno.cadastrarDataNascimento(dataFormatada);

		// Verificar se a data não é nulo
		Assert.assertNotNull(dataNascimentoAluno);
		// Verificar se o data não é vazia
		Assert.assertNotEquals("", dataNascimentoAluno);

		// Usar o Erro Collector
		error.checkThat(dataNascimentoAluno, CoreMatchers.is(LocalDate.parse("08/02/1986", formatter)));
	}

	@Test
	public void teste3_CadastroNotaAluno() {
		// Pegando o valor da nota do Aluno
		double notaAluno = aluno.getNota1();
		String pergunta = "Digite a nota 1 (entre 0 e 10): ";

		// Cadastrar a data de nascimento no formato correto
		double notaAlunoTeste = CadastroAluno.cadastrarNota(pergunta, notaAluno);

		Assert.assertEquals(notaAluno, notaAlunoTeste, 0.0001);
		Assert.assertNotSame("", notaAlunoTeste);
	}

	@Test
	public void teste4_CadastrarClasse() {
		// Pegando o valor da classe do Aluno
		int classeAlunoInt = aluno.getClasse();
		// Convertendo de int pra string
		String classeAluno = Integer.toString(classeAlunoInt);

		// Cadastrar a classe do aluno
		int classeAlunoConvertido = CadastroAluno.cadastrarClasse(classeAluno);

		// Verificação com Assert se o valor não e nulo;
		Assert.assertNotNull(classeAlunoConvertido);
		// Verificação com Assert se o valor vem diferente;
		Assert.assertNotSame(7, classeAlunoConvertido);
		// Verificação com Assert se o valor esperado e atual;
		Assert.assertSame(8, classeAlunoConvertido);
	}

	@Test
	public void teste5_CadastrarTurma() {
		// Pegando o valor da Turma do Aluno
		String turmaAluno = aluno.getTurma();

		// Cadastrar a turma do Aluno
		String turmaAlunoTeste = CadastroAluno.cadastrarTurma(turmaAluno);

		// Verificação com Assert se o valor não e nulo;
		Assert.assertNotNull(turmaAlunoTeste);
		// Verificação com Assert se o valor vem diferente;
		Assert.assertNotSame("G", turmaAlunoTeste);
		// Verificação com Assert se o valor esperado e atual;
		Assert.assertSame("A", turmaAlunoTeste);
	}

	@Test
	public void teste6_CalculaMedia() {
		// Pegando o valor das notas passada e fazendo o calculo do valor da média.
		double media = aluno.calcularMedia(aluno.getNota1(), aluno.getNota2());
		Assert.assertNotNull(media);
		Assert.assertEquals(8.0, media, 0.01);
	}

	@Test
	public void teste7_DeterminarSituacao() {
		// Adicionando o valor da média esperada
		double media = 8.0;
		String situacao = aluno.determinarSituacao(aluno.getSituacao(), media);
		Assert.assertNotNull(situacao);
		Assert.assertEquals("Aprovado", situacao);
	}

	@Test
	public void teste8_ConsultarAlunoPorNome() {
		// Cadastrar alunos
		Aluno aluno1 = new Aluno("Maria", LocalDate.parse("15/03/1990", formatter), 9d, 7d, 7, "B");
		Aluno aluno2 = new Aluno("Rodrigo", LocalDate.parse("22/07/1988", formatter), 7d, 8d, 6, "C");

		alunoService.cadastrarAluno(aluno);
		alunoService.cadastrarAluno(aluno1);
		alunoService.cadastrarAluno(aluno2);

		// Consultar alunos por nome
		List<Aluno> resultadoRodrigo = alunoService.consultarAlunoPorNome("Rodrigo");
		List<Aluno> resultadoMaria = alunoService.consultarAlunoPorNome("Maria");
		List<Aluno> resultadoNaoExistente = alunoService.consultarAlunoPorNome("João");

		// Verificar se os alunos retornados são os esperados
		Assert.assertEquals(2, resultadoRodrigo.size());
		Assert.assertTrue(resultadoRodrigo.contains(aluno));
		Assert.assertTrue(resultadoRodrigo.contains(aluno2));

		Assert.assertEquals(1, resultadoMaria.size());
		Assert.assertTrue(resultadoMaria.contains(aluno1));

		Assert.assertEquals(0, resultadoNaoExistente.size());
	}

	@Test
	public void teste9_ConsultarAlunoPorMatricula() {
		// Cadastrar alunos
		aluno.setMatricula(1);
		Aluno aluno1 = new Aluno("Maria", LocalDate.parse("15/03/1990", formatter), 9d, 7d, 7, "B");
		aluno1.setMatricula(2);
		Aluno aluno2 = new Aluno("João", LocalDate.parse("22/07/1988", formatter), 7d, 8d, 6, "C");
		aluno2.setMatricula(3);

		alunoService.cadastrarAluno(aluno);
		alunoService.cadastrarAluno(aluno1);
		alunoService.cadastrarAluno(aluno2);

		Aluno resultado1 = alunoService.consultarAlunoPorMatricula(1);
		Aluno resultado2 = alunoService.consultarAlunoPorMatricula(2);
		Aluno resultadoNaoExistente = alunoService.consultarAlunoPorMatricula(999);

		Assert.assertNotNull(resultado1);
		Assert.assertEquals(aluno, resultado1);

		Assert.assertNotNull(resultado2);
		Assert.assertEquals(aluno1, resultado2);

		Assert.assertNull(resultadoNaoExistente);
	}

	@Test
	public void teste10_CadastrarAluno() {
		// Cadastrar aluno
		alunoService.cadastrarAluno(aluno);
		// Consultar aluno por matrícula
		Aluno resultado = alunoService.consultarAlunoPorMatricula(aluno.getMatricula());

		// Verificar se o aluno cadastrado é retornado corretamente
		Assert.assertNotNull(resultado);
		Assert.assertEquals(aluno, resultado);
		// Verificar se o aluno está na lista de todos os alunos
		List<Aluno> todosAlunos = alunoService.consultarAlunos();
		Assert.assertTrue(todosAlunos.contains(aluno));
	}

	@Test
	public void teste11_VerificarAlunoCadastrado() {
		// Cadastrar alunos
		alunoService.cadastrarAluno(aluno);
		// Consultar aluno por matrícula
		boolean resultado = alunoService.verificarAlunoCadastrado(aluno.getNomeAluno(), aluno.getDataNascimento());

		// Verificar se o aluno cadastrado é retornado não e Nulo
		Assert.assertNotNull(resultado);
		// Verificar se o retorno e TRUE
		Assert.assertTrue(resultado);

		// Verificar se o aluno está na lista de todos os alunos
		List<Aluno> todosAlunos = alunoService.consultarAlunos();
		Assert.assertTrue(todosAlunos.contains(aluno));
		// Verificar um aluno se e cadastrado
		boolean alunoNaoCadastrado = alunoService.verificarAlunoCadastrado("João",
				LocalDate.parse("01/01/2000", formatter));
		// Verificar se o aluno que esta passado e falso
		Assert.assertFalse(alunoNaoCadastrado);
	}

	@Test
	public void teste12_AlteraAlunoCadastrado() {
		// Cadastra Aluno
		alunoService.cadastrarAluno(aluno);
		aluno.setMatricula(1);

		// Consultar aluno por matrícula
		boolean resultado = alunoService.verificarAlunoCadastrado(aluno.getNomeAluno(), aluno.getDataNascimento());
		// Verificar se o retorno e TRUE
		Assert.assertTrue(resultado);

		// Criar aluno alterado
		Aluno alunoAlterado = new Aluno("Rodrigo Gomes", LocalDate.parse("08/02/1986", formatter), 8d, 8d, 9, "D");
		// Cadastrar Aluno Alterado
		alunoService.alterarAluno(alunoAlterado);
		alunoAlterado.setMatricula(1); // Mantendo a mesma matrícula
		// Verificar um aluno se e cadastrado
		boolean resultadoAlteracao = alunoService.alterarAluno(alunoAlterado);
		// Verificar se o aluno que esta passado e TRUE
		Assert.assertTrue(resultadoAlteracao);

		// Imprimir o aluno Original e o aluno Alterado
		// System.out.println("Aluno Original: " + aluno + "\n" + "Aluno Alterado: " +
		// alunoAlterado);

		// Pegando o aluno com o matricula que foi cadastrada
		Aluno validacaoAlunoAlterado = alunoService.consultarAlunoPorMatricula(1);
		// Validando os campos do cadastro
		Assert.assertNotNull(validacaoAlunoAlterado);
		Assert.assertEquals(alunoAlterado.getNomeAluno(), validacaoAlunoAlterado.getNomeAluno());
		Assert.assertEquals(alunoAlterado.getNota1(), validacaoAlunoAlterado.getNota1(), 0.01);
		Assert.assertEquals(alunoAlterado.getNota2(), validacaoAlunoAlterado.getNota2(), 0.01);
		Assert.assertEquals(alunoAlterado.getClasse(), validacaoAlunoAlterado.getClasse());
		Assert.assertEquals(alunoAlterado.getTurma(), validacaoAlunoAlterado.getTurma());
	}

	@Test
	public void teste13_ExcluirAlunoCadastro() {

		alunoService.cadastrarAluno(aluno);
		aluno.setMatricula(1);
		// Criar e cadastrar alunos extras
		Aluno aluno1 = new Aluno("Maria", LocalDate.parse("15/03/1990", formatter), 9d, 7d, 7, "B");
		aluno1.setMatricula(2);
		alunoService.cadastrarAluno(aluno1);

		// Verificar que os alunos estão cadastrados
		Assert.assertNotNull(alunoService.consultarAlunoPorMatricula(1));
		Assert.assertNotNull(alunoService.consultarAlunoPorMatricula(2));

		// Excluir um aluno
		boolean resultadoExclusao = alunoService.excluirAluno(1);

		// Verificar se a exclusão foi bem-sucedida
		Assert.assertTrue(resultadoExclusao);

		// Verificar que o aluno foi excluído
		Assert.assertNull(alunoService.consultarAlunoPorMatricula(1));
		// Verificar que o outro aluno ainda está cadastrado
		Assert.assertNotNull(alunoService.consultarAlunoPorMatricula(2));

		// Tentar excluir um aluno que não existe
		boolean resultadoExclusaoInexistente = alunoService.excluirAluno(999);

		// Verificar que a exclusão falhou
		Assert.assertFalse(resultadoExclusaoInexistente);
	}

	@Test
	public void teste14_ExportarAlunosParaArquivo() throws IOException {
	    // Cria alguns alunos e os cadastra
	    aluno.setMatricula(1);
	    alunoService.cadastrarAluno(aluno);

	    Aluno aluno1 = new Aluno("Maria", LocalDate.parse("15/03/1990", formatter), 9d, 7d, 7, "B");
	    aluno1.setMatricula(2);
	    alunoService.cadastrarAluno(aluno1);

	    // Define o caminho do diretório e do arquivo para exportação
	    String tempDir = System.getProperty("java.io.tmpdir");
	    String caminhoDiretorio = tempDir + File.separator + "CadastroAlunosTeste";
	    String caminhoArquivo = caminhoDiretorio + File.separator + "alunos_cadastrados.txt";

	    // Exporta os alunos para o arquivo
	    alunoService.exportarAlunosParaArquivo(null, caminhoDiretorio, null, null);

	    // Verifica se o arquivo foi criado
	    File arquivo = new File(caminhoArquivo);
	    Assert.assertTrue("Arquivo não encontrado: " + caminhoArquivo, arquivo.exists());

	    // Verifica o conteúdo do arquivo
	    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
	        String linha;
	        boolean encontrouRodrigo = false;
	        boolean encontrouMaria = false;

	        while ((linha = reader.readLine()) != null) {
	            if (linha.contains("Rodrigo")) {
	                encontrouRodrigo = true;
	                // Lê a próxima linha e verifica a data de nascimento
	                String proximaLinha = reader.readLine();
	                Assert.assertNotNull("Linha esperada não encontrada após 'Rodrigo'.", proximaLinha);
	                Assert.assertTrue("A linha com 'Rodrigo' não contém a data correta.", proximaLinha.contains("08/02/1986"));
	            }
	            if (linha.contains("Maria")) {
	                encontrouMaria = true;
	                // Lê a próxima linha e verifica a data de nascimento
	                String proximaLinha = reader.readLine();
	                Assert.assertNotNull("Linha esperada não encontrada após 'Maria'.", proximaLinha);
	                Assert.assertTrue("A linha com 'Maria' não contém a data correta.", proximaLinha.contains("15/03/1990"));
	            }
	        }

	        Assert.assertTrue("Não encontrou a linha para 'Rodrigo'.", encontrouRodrigo);
	        Assert.assertTrue("Não encontrou a linha para 'Maria'.", encontrouMaria);
	    }

	    // Limpa o arquivo após o teste
	    new File(caminhoArquivo).delete();
	    new File(caminhoDiretorio).delete();
	}


}
