package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Donazione {
	
	private Nerc nerc;
	private LocalDateTime time;
	
	public Donazione(Nerc nerc, LocalDateTime time) {
		super();
		this.nerc = nerc;
		this.time = time;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public LocalDateTime getTime() {
		return time;
	}
	
	

}
