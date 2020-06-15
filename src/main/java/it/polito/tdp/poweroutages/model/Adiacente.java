package it.polito.tdp.poweroutages.model;

public class Adiacente implements Comparable<Adiacente> {
	
	private Nerc nerc;
	private Integer peso;
	
	public Adiacente(Nerc nerc, Integer peso) {
		super();
		this.nerc = nerc;
		this.peso = peso;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public Integer getPeso() {
		return peso;
	}

	@Override
	public int compareTo(Adiacente o) {
		return -this.peso.compareTo(o.peso);
	}
	
	public String toString() {
		return nerc.toString()+" | "+peso;
	}

}
