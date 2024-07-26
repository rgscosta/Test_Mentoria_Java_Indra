package Mentoria_Java.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {
	// Lista de Alunos.
	private List<Aluno> alunos;

	// Contrutor do Aluno Service
	public AlunoService() {
		this.alunos = new ArrayList<>();
	}

	// Cadastrando o Aluno
	public void cadastrarAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	// Consultar todos alunos.
	public List<Aluno> consultarAlunos() {
		return new ArrayList<>(alunos);
	}

	// Verificar se o aluno e cadastrado pela matricula.
	public Aluno consultarAlunoPorMatricula(int matricula) {
		for (Aluno aluno : alunos) {
			if (aluno.getMatricula() == matricula) {
				return aluno;
			}
		}
		return null;
	}

	// Consultar o aluno por nome.
	public List<Aluno> consultarAlunoPorNome(String nome) {
		List<Aluno> alunosEncontrados = new ArrayList<>();
		for (Aluno aluno : alunos) {
			if (aluno.getNomeAluno().equalsIgnoreCase(nome)) {
				alunosEncontrados.add(aluno);
			}
		}
		return alunosEncontrados;
	}

	// Alterar o cadastro do aluno.
	public boolean alterarAluno(Aluno alunoAlterado) {
		for (int i = 0; i < alunos.size(); i++) {
			if (alunos.get(i).getMatricula() == alunoAlterado.getMatricula()) {
				alunos.set(i, alunoAlterado);
				return true;
			}
		}
		return false;
	}

	// Excluir o aluno da base.
	public boolean excluirAluno(int matricula) {
		return alunos.removeIf(aluno -> aluno.getMatricula() == matricula);
	}
	
	// Exporta os cadastro para um arquivo TXT
	public void exportarAlunosParaArquivo(String caminhoArquivos, String caminhoDiretorio, String userHome, String pastaCadastro) {
		// Criar a pasta se não existir
		File pasta = new File(caminhoDiretorio);
		if (!pasta.exists()) {
			if (pasta.mkdirs()) {
				System.out.println("Pasta 'Cadastro de Alunos' criada com sucesso.");
			} else {
				System.out.println("Não foi possível criar a pasta 'Cadastro de Alunos'.");
				return;
			}
		}
		// Caminho do Arquivo vai ser salvo
		String caminhoArquivo = caminhoDiretorio + File.separator + "alunos_cadastrados.txt";
		try (PrintWriter writer = new PrintWriter(new File(caminhoArquivo))) {
			for (Aluno aluno : alunos) {
				writer.println(aluno);
			}
			System.out.println("Alunos exportados com sucesso para o arquivo '" + caminhoArquivo + "'.\n");
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao exportar alunos: " + e.getMessage());
		}
	}

	// Validar se o aluno cadastrado não já existe na base com o mesmo nome e data de nascimento.
	public boolean verificarAlunoCadastrado(String nomeAluno, LocalDate dataNascimento) {
		for (Aluno aluno : alunos) {
			if (aluno.getNomeAluno().equals(nomeAluno) && aluno.getDataNascimento().equals(dataNascimento)) {
				return true;
			}
		}
		return false;
	}
}
