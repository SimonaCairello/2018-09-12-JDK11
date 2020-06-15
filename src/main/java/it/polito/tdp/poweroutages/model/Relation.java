package it.polito.tdp.poweroutages.model;

public class Relation {
	
	private Nerc nerc1;
	private Nerc nerc2;
	private Integer peso;
	
	
	public Relation(Nerc nerc1, Nerc nerc2, Integer peso) {
		super();
		this.nerc1 = nerc1;
		this.nerc2 = nerc2;
		this.peso = peso;
	}


	public Nerc getNerc1() {
		return nerc1;
	}


	public Nerc getNerc2() {
		return nerc2;
	}


	public Integer getPeso() {
		return peso;
	}
	
	
	
	

}
