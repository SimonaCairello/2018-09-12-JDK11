package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	
	private Integer id;
	private Nerc nerc;
	private LocalDateTime began;
	private LocalDateTime finished;
	
	public PowerOutage(Integer id, Nerc nerc, LocalDateTime began, LocalDateTime finished) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.began = began;
		this.finished = finished;
	}

	public Integer getId() {
		return id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public LocalDateTime getBegan() {
		return began;
	}

	public LocalDateTime getFinished() {
		return finished;
	}
	
	

}
