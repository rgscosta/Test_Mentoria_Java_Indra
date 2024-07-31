package mentoria_java_entidades;

import java.time.LocalDate;

public class Aluno {

	static int nextmatricula = 1;
	Integer matricula;
	String nomeAluno;
	String situacao;
	String turma;
	LocalDate dataNascimento;
	int classe;
	double nota1;
	double nota2;
	double media;

	public Aluno(String nomeAluno, LocalDate dataNascimento, Double nota1, Double nota2, Integer classe, String turma) {
		this.nomeAluno = nomeAluno;
		this.dataNascimento = dataNascimento;
		this.classe = classe;
		this.turma = turma;
		this.nota1 = nota1;
		this.nota2 = nota2;
		this.matricula = nextmatricula++;
	}

	public double calcularMedia(double d, double e) {
		return this.media = (nota1 + nota2) / 2.0;
	}

	public String determinarSituacao(String situação, Double media) {
		//return this.situacao = this.media >= 7 ? "Aprovado" : "Reprovado";
		return this.situacao  =  media >= 7 ? "Aprovado" : "Reprovado";
	}

	public static int getNextmatricula() {
		return nextmatricula;
	}

	public static void setNextmatricula(int nextmatricula) {
		Aluno.nextmatricula = nextmatricula;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public double getNota1() {
		return nota1;
	}

	public void setNota1(double nota1) {
		this.nota1 = nota1;
	}

	public double getNota2() {
		return nota2;
	}

	public void setNota2(double nota2) {
		this.nota2 = nota2;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	// Serve para imprimir o cadastro do aluno na tela
	@Override
	public String toString() {
		return "\nMatrícula: " + matricula + "\n" + "Nome: " + nomeAluno + "\n" + "Classe: " + classe + "º Ano" + "\n"
				+ "Turma: " + turma + "\n" + "Nota 1: " + nota1 + "\n" + "Nota 2: " + nota2 + "\n" + "Média: " + media
				+ "\n" + "Situação: " + situacao + "\n";
	}

}
