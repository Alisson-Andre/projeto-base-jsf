package br.com.projetobasejsf.pesistence.model;

import javax.persistence.Entity;

@Entity
public class Projeto extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private String nome;

	public String getNome() {
		return nome;

	}

	public void setNome(String nome) {
		this.nome = nome;

	}
}
