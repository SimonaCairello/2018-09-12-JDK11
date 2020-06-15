package it.polito.tdp.poweroutages.model;

import java.util.List;

public class Nerc {
	
	private int id;
	private String value;
	private boolean isDonatore;
	private List<Donazione> donazioni;
	private Integer bonus;

	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}

	public boolean isDonatore() {
		return isDonatore;
	}

	public void setDonatore(boolean isDonatore) {
		this.isDonatore = isDonatore;
	}

	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	
	public void increaseBonus(Integer bonus) {
		this.bonus += bonus;
	}

	public List<Donazione> getDonazioni() {
		return donazioni;
	}

	public void setDonazioni(List<Donazione> donazioni) {
		this.donazioni = donazioni;
	}
	
	public void addDonazione(Donazione donazione) {
		this.donazioni.add(donazione);
	}
}
