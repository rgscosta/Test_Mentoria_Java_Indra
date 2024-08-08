package mentoria_java_entidades;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import mentoria_java_servicos.AlunoService;

public class CadastroAluno {

	static Scanner scanner = new Scanner(System.in);
	static AlunoService alunoService = new AlunoService();

	// Criando variavel para teste Unitarios
	static String nomeAlunoTeste;
	static String dataNascimentoStr;
	static double notaDigitadaAluno;
	static String pergunta;
	static String classeAluno;
	static String turmaAluno;
	static double d;
	static double e;
	static double mediaAluno;
	static String situacao;
	static double media;

	public static void main(String[] args) {

		int opcao;

		while (true) {
			System.out.print("************************** Menu de Cadastros **************************\n\n");
			System.out.println(
					"- Digite (1) para cadastro de Aluno \n- Digite (2) para consulta de Aluno \n- Digite (3) para alterar cadastro de Aluno \n- Digite (4) para excluir cadastro de Aluno \n- Digite (0) para sair ");
			System.out.print("=> ");
			try {
				opcao = scanner.nextInt();
				System.out.print("\n");
				scanner.nextLine(); // Consumir a nova linha

				switch (opcao) {
				case 1:
					cadastro();
					break;
				case 2:
					consulta();
					break;
				case 3:
					alterar();
					break;
				case 4:
					excluir();
					break;
				case 0:
					System.out.println("Saindo do menu de cadastro!");
					scanner.close();
					return;
				default:
					System.out.println("Opção inválida. Por favor, digite uma opção válida.");
				}
			} catch (Exception e) {
				System.out.println("Entrada inválida. Por favor, digite um número.");
				scanner.nextLine(); // Consumir a entrada inválida
			}
		}
	}

	public static void cadastro() {

		String nomeAluno = "";
		LocalDate dataNascimento;

		while (true) {
			// Cadastrar o Nome do Aluno Novo;
			nomeAluno = cadastrarNomeAluno(nomeAlunoTeste);
			// Cadastrar a Data de Nascimento do Aluno Novo;
			dataNascimento = cadastrarDataNascimento(dataNascimentoStr);

			// Verificar duplicidade de aluno com base no nome e data de nascimento
			if (alunoService.verificarAlunoCadastrado(nomeAluno, dataNascimento)) {
				System.out.println("Nome e data de nascimento já cadastrado na base.\n");
			} else {
				break;
			}
		}

		// Cadastrar as Notas
		double nota1 = cadastrarNota("Digite a nota 1 (entre 0 e 10): ", notaDigitadaAluno);
		double nota2 = cadastrarNota("Digite a nota 2 (entre 0 e 10): ", notaDigitadaAluno);
		// Cadastrar a Classe
		Integer classe = cadastrarClasse(classeAluno);
		// Cadastrar a Turma
		String turma = cadastrarTurma(turmaAluno);
		// Construtor com recebendo os dados
		Aluno aluno = new Aluno(nomeAluno, dataNascimento, nota1, nota2, classe, turma);
		// Calculando a Média
		// aluno.calcularMedia();
		aluno.calcularMedia(d, e);
		// Informando a Situação
		aluno.determinarSituacao(situacao, media);
		// Adicionando Aluno no cadastro
		alunoService.cadastrarAluno(aluno);

		System.out.println("Matrícula: " + aluno.getMatricula() + "\n");
		System.out.println("Aluno cadastrado com Sucesso.\n");
	}

	public static String cadastrarNomeAluno(String nomeAluno) {
		boolean teste = true;
		while (teste) {
			try {
				if (nomeAluno.matches("[A-Za-zÀ-ú ]+")) {
					return nomeAluno;
				} else {
					teste = false;
					throw new NomeInvalidoException(
							"\nNome inválido. Por favor, digite um nome que contenha apenas letras. \n");

				}
			} catch (NomeInvalidoException e) {
				System.out.println(e.getMessage());
			}
		}
		return nomeAluno;
	}

	public static LocalDate cadastrarDataNascimento(String dataNascimentoStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Pattern datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");

		while (true) {
			if (datePattern.matcher(dataNascimentoStr).matches()) {
				try {
					LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
					if (dataNascimento.getYear() >= 1900) {
						return dataNascimento;
					} else {
						System.out.println("Ano inválido. Por favor, digite um ano a partir de 1900.");
					}
				} catch (DateTimeParseException e) {
					System.out.println("Data inválida. Por favor, digite no formato dd/MM/yyyy.");
				}
			} else {
				System.out.println("Data inválida. Por favor, digite no formato dd/MM/yyyy.");
			}
		}
	}

	public static double cadastrarNota(String pergunta, double notaDigitadaAluno) {
		while (true) {
			try {
				double nota = notaDigitadaAluno;
				if (nota >= 0 && nota <= 10) {
					return nota;
				} else {
					System.out.println("Nota inválida. Por favor, digite uma nota entre 0 e 10.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Por favor, digite um número.");
			}
		}
	}

	public static int cadastrarClasse(String classeAluno) {
		while (true) {
			try {
				// Remover o símbolo de grau, se presente
				classeAluno.replace("º", "").trim();
				int classe = Integer.parseInt(classeAluno);

				if (classe >= 1 && classe <= 9) {
					return classe;
				} else {
					System.out.println("Classe inválida. Por favor, digite uma classe entre 1º e 9º.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Por favor, digite um número válido.");
			}
		}
	}

	public static String cadastrarTurma(String turmaAluno) {
		while (true) {
			String turma = turmaAluno;
			try {
				if (turma.matches("[A-Za-zÀ-ú]") && turma.length() == 1) {
					// Transformar a letra em maiúscula
					turma = turma.toUpperCase();
					return turma;
				} else {
					throw new TurmaInvalidaException("Turma inválida. Por favor, digite apenas uma letra.");
				}
			} catch (TurmaInvalidaException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void consulta() {
		while (true) {
			System.out.print(
					"************************** Menu de Consulta de Cadastros *****************************\n\n");
			System.out.println("Digite (1) para consultar todos os alunos");
			System.out.println("Digite (2) para consultar por matrícula");
			System.out.println("Digite (3) para consultar por nome");
			System.out.println("Digite (4) para exportar todos os alunos para um arquivo");
			System.out.println("Digite (0) para finalizar consulta");
			System.out.print("=> ");
			int consultaOpcao = scanner.nextInt();
			scanner.nextLine(); // Consumir a nova linha

			switch (consultaOpcao) {
			case 1:
				List<Aluno> todosAlunos = alunoService.consultarAlunos();
				if (todosAlunos.isEmpty()) {
					System.out.println("\nNenhum aluno cadastrado na base de dados.\n");
				} else {
					for (Aluno aluno : todosAlunos) {
						System.out.println(aluno);
					}
				}
				break;
			case 2:
				System.out.print("Digite a matrícula do aluno: ");
				int matricula = scanner.nextInt();
				scanner.nextLine(); // Consumir a nova linha
				Aluno alunoPorMatricula = alunoService.consultarAlunoPorMatricula(matricula);
				if (alunoPorMatricula == null) {
					System.out.println("\nAluno não encontrado.\n");
				} else {
					System.out.println(alunoPorMatricula);
				}
				break;
			case 3:
				System.out.print("Digite o nome do aluno: ");
				String nome = scanner.nextLine();
				List<Aluno> alunosPorNome = alunoService.consultarAlunoPorNome(nome);
				if (alunosPorNome.isEmpty()) {
					System.out.println("\nAluno não encontrado.\n");
				} else {
					for (Aluno aluno : alunosPorNome) {
						System.out.println(aluno);
					}
				}
				break;
			case 4:
				String userHome = System.getProperty("user.home");
				String pastaCadastro = "Cadastro de Alunos";
				String caminhoDiretorio = userHome + File.separator + "Desktop" + File.separator + pastaCadastro;
				String caminhoArquivo = caminhoDiretorio + File.separator + "alunos_cadastrados.txt";
				alunoService.exportarAlunosParaArquivo(caminhoArquivo, caminhoDiretorio, userHome, pastaCadastro);
				break;
			case 0:
				System.out.println("\nFinalizando consulta\n");
				return;
			default:
				System.out.println("\nOpção inválida. Por favor, tente novamente.\n");
			}
		}
	}

	public static void alterar() {
		System.out.print("************************** Menu Alterar Cadastro *****************************\n");
		System.out.print("\nDigite a matrícula do aluno que deseja alterar: ");
		int matricula = scanner.nextInt();
		scanner.nextLine(); // Consumir a nova linha

		Aluno alunoExistente = alunoService.consultarAlunoPorMatricula(matricula);
		if (alunoExistente == null) {
			System.out.println("\nAluno não encontrado.\n");
			return;
		}

		System.out.println("\nAlterando dados do cadastro do aluno: " + alunoExistente.getNomeAluno());

		String nomeAluno = alunoExistente.getNomeAluno();
		LocalDate dataNascimento = alunoExistente.getDataNascimento();
		double nota1 = alunoExistente.getNota1();
		double nota2 = alunoExistente.getNota2();
		int classe = alunoExistente.getClasse();
		String turma = alunoExistente.getTurma();

		while (true) {
			System.out.println("\nDigite a opção para alteração de cadastro:");
			System.out.println("- Digite (1) Alterar o Nome");
			System.out.println("- Digite (2) Alterar Data de Nascimento");
			System.out.println("- Digite (3) Alterar Nota 1");
			System.out.println("- Digite (4) Alterar Nota 2");
			System.out.println("- Digite (5) Alterar Classe");
			System.out.println("- Digite (6) Alterar Turma");
			System.out.println("- Digite (0) Finalizar alteração");
			System.out.print("=> ");
			int opcao = scanner.nextInt();
			scanner.nextLine(); // Consumir a nova linha

			String mensagemAlteracao = ""; // Variável para armazenar a mensagem de alteração genérica

			switch (opcao) {
			case 1:
				nomeAluno = cadastrarNomeAluno(nomeAlunoTeste);
				mensagemAlteracao = "\nNome do aluno alterado com sucesso.\n";
				break;
			case 2:
				dataNascimento = cadastrarDataNascimento(dataNascimentoStr);
				mensagemAlteracao = "\nData de nascimento alterada com sucesso.\n";
				break;
			case 3:
				nota1 = cadastrarNota("\nDigite a nova nota 1 (entre 0 e 10): ", notaDigitadaAluno);
				mensagemAlteracao = "Nota 1 alterada com sucesso.";
				break;
			case 4:
				nota2 = cadastrarNota("\nDigite a nova nota 2 (entre 0 e 10): ", notaDigitadaAluno);
				mensagemAlteracao = "Nota 2 alterada com sucesso.";
				break;
			case 5:
				classe = cadastrarClasse(classeAluno);
				mensagemAlteracao = "\nClasse alterada com sucesso.\n";
				break;
			case 6:
				turma = cadastrarTurma(turmaAluno);
				mensagemAlteracao = "\nTurma alterada com sucesso.\n";
				break;
			case 0:
				System.out.println("\nSaindo do menu de alterações \n");
				return;
			default:
				System.out.println("\nOpção inválida. Por favor, tente novamente.");
				continue;
			}

			Aluno alunoAlterado = new Aluno(nomeAluno, dataNascimento, nota1, nota2, classe, turma);
			alunoAlterado.calcularMedia(nota1, nota2);
			alunoAlterado.determinarSituacao(situacao, media);
			alunoAlterado.setMatricula(matricula);

			if (alunoService.alterarAluno(alunoAlterado)) {
				System.out.println(mensagemAlteracao);
			} else {
				System.out.println("Erro ao alterar cadastro.");
			}
		}
	}

	public static void excluir() {
		System.out.print("************************** Menu Exclusão de Cadastros *******************************\n");
		System.out.print("\nDigite a matrícula do aluno que deseja excluir: ");
		int matricula = scanner.nextInt();
		scanner.nextLine(); // Consumir a nova linha

		Aluno alunoExiste = alunoService.consultarAlunoPorMatricula(matricula);
		if (alunoExiste != null) {
			String nomeAluno = alunoExiste.getNomeAluno();
			Integer maticulaAluno = alunoExiste.getMatricula();
			String turma = alunoExiste.getTurma();
			Integer classe = alunoExiste.getClasse();

			System.out.println("\nDeseja excluir o Aluno:" + nomeAluno + " - Matricula:" + maticulaAluno + " - Classe:"
					+ classe + "º Ano" + " - Turma:" + turma);
			System.out.println("\nDigite 'S' para Sim e 'N' para Não");
			System.out.print("=> ");
			String escolha = scanner.next();

			if (escolha.equalsIgnoreCase("S")) {
				if (alunoService.excluirAluno(matricula)) {
					System.out.println("\nAluno excluído com sucesso.\n");
				} else {
					System.out.println("\nErro ao excluir o aluno.\n");
				}
			} else {
				System.out.println("\nOperação de exclusão cancelada.\n");
			}
		} else {
			System.out.println("\nAluno não encontrado.\n");
		}
	}
}
