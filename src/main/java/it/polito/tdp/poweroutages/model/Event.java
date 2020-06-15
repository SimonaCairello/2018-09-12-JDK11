package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event> {
	
	public enum EventType {
		INIZIO_GUASTO, FINE_GUASTO
	}
	
	private EventType type;
	private LocalDateTime time;
	private Nerc nerc;
	private Nerc donatore;
	private PowerOutage powerOutage;
	
	public Event(EventType type, LocalDateTime time, Nerc nerc, PowerOutage powerOutage) {
		super();
		this.type = type;
		this.time = time;
		this.nerc = nerc;
		this.powerOutage = powerOutage;
	}

	public EventType getType() {
		return type;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public Nerc getNerc() {
		return nerc;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}

	public Nerc getDonatore() {
		return donatore;
	}

	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}

	public PowerOutage getPowerOutage() {
		return powerOutage;
	}
	
	

}
