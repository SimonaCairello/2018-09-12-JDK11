package it.polito.tdp.poweroutages.model;

public class NercNumero implements Comparable<NercNumero>{
	
	private Nerc nerc;
	private Integer num;
	
	public NercNumero(Nerc nerc, Integer num) {
		this.nerc = nerc;
		this.num = num;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	public void incrementa(Integer i) {
		this.num += i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nerc == null) ? 0 : nerc.hashCode());
		result = prime * result + ((num == null) ? 0 : num.hashCode());
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
		NercNumero other = (NercNumero) obj;
		if (nerc == null) {
			if (other.nerc != null)
				return false;
		} else if (!nerc.equals(other.nerc))
			return false;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nerc + ", " + num;
	}
	
	@Override
	public int compareTo(NercNumero o) {
		return - (this.num-o.getNum());
	}

}
