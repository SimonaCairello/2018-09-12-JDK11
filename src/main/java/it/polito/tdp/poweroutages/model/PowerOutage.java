package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	
	private Integer id;
	private Integer nercId;
	private LocalDateTime dateBegan;
	private LocalDateTime dateFinished;
	
	public PowerOutage(Integer id, Integer nercId, LocalDateTime dateBegan, LocalDateTime dateFinished) {
		this.id = id;
		this.nercId = nercId;
		this.dateBegan = dateBegan;
		this.dateFinished = dateFinished;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNercId() {
		return nercId;
	}

	public void setNercId(Integer nercId) {
		this.nercId = nercId;
	}

	public LocalDateTime getDateBegan() {
		return dateBegan;
	}

	public void setDateBegan(LocalDateTime dateBegan) {
		this.dateBegan = dateBegan;
	}

	public LocalDateTime getDateFinished() {
		return dateFinished;
	}

	public void setDateFinished(LocalDateTime dateFinished) {
		this.dateFinished = dateFinished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PowerOutage other = (PowerOutage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerOutage [id=" + id + ", nercId=" + nercId + ", dateBegan=" + dateBegan + ", dateFinished="
				+ dateFinished + "]";
	}

}
