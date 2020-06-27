package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Donazione {
	
	private Nerc donatore;
	private Nerc ricevente;
	private LocalDateTime inizio;
	
	public Donazione(Nerc donatore, Nerc ricevente, LocalDateTime inizio) {
		this.donatore = donatore;
		this.ricevente = ricevente;
		this.inizio = inizio;
	}

	public Nerc getDonatore() {
		return donatore;
	}

	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}

	public Nerc getRicevente() {
		return ricevente;
	}

	public void setRicevente(Nerc ricevente) {
		this.ricevente = ricevente;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((donatore == null) ? 0 : donatore.hashCode());
		result = prime * result + ((inizio == null) ? 0 : inizio.hashCode());
		result = prime * result + ((ricevente == null) ? 0 : ricevente.hashCode());
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
		Donazione other = (Donazione) obj;
		if (donatore == null) {
			if (other.donatore != null)
				return false;
		} else if (!donatore.equals(other.donatore))
			return false;
		if (inizio == null) {
			if (other.inizio != null)
				return false;
		} else if (!inizio.equals(other.inizio))
			return false;
		if (ricevente == null) {
			if (other.ricevente != null)
				return false;
		} else if (!ricevente.equals(other.ricevente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Donazione [donatore=" + donatore + ", ricevente=" + ricevente + ", inizio=" + inizio + "]";
	}
}
