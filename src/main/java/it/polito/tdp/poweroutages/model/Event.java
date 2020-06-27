package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		INIZIO, FINE
	}
	
	private EventType tipo;
	private LocalDateTime time;
	private Nerc nercAffetto;
	private Nerc nercAiutante;
	
	public Event(EventType tipo, LocalDateTime time, Nerc nercAffetto) {
		this.tipo = tipo;
		this.time = time;
		this.nercAffetto = nercAffetto;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Nerc getNercAffetto() {
		return nercAffetto;
	}

	public void setNercAffetto(Nerc nercAffetto) {
		this.nercAffetto = nercAffetto;
	}

	public Nerc getNercAiutante() {
		return nercAiutante;
	}

	public void setNercAiutante(Nerc nercAiutante) {
		this.nercAiutante = nercAiutante;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}
	

}
