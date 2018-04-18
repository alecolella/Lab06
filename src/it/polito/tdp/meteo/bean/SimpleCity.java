package it.polito.tdp.meteo.bean;

import java.util.List;

public class SimpleCity {

	private String nome;
	private int costo;
	private int counter = 0;
	private List<Rilevamento> rilevamenti;
	
	public SimpleCity(String nome) {
		this.nome = nome;
	}
	
	public SimpleCity(String nome, int costo) {
		this.nome = nome;
		this.costo = costo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getCosto(int step) {
		return rilevamenti.get(step).getUmidita();
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}
	
	public void setRilevamenti(List<Rilevamento> rilevamenti) {
		this.rilevamenti = rilevamenti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleCity other = (SimpleCity) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
	public int getCounter() {
		return counter;
	}

	public void increaseCounter() {
		this.counter += 1;
	}

	public void descreaseCounter() {
		this.counter -= 1;
		
	}
	
}
