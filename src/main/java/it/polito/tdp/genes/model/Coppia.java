package it.polito.tdp.genes.model;

import java.util.Objects;

public class Coppia {

	private Integer c1;
	private Integer c2;
	private double n;
	
	public Coppia(Integer c1, Integer c2, double n) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.n = n;
	}
	public Integer getC1() {
		return c1;
	}
	public void setC1(Integer c1) {
		this.c1 = c1;
	}
	public Integer getC2() {
		return c2;
	}
	public void setC2(Integer c2) {
		this.c2 = c2;
	}
	public double getN() {
		return n;
	}
	public void setN(double n) {
		this.n = n;
	}
	@Override
	public int hashCode() {
		return Objects.hash(c1, c2, n);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coppia other = (Coppia) obj;
		return Objects.equals(c1, other.c1) && Objects.equals(c2, other.c2)
				&& Double.doubleToLongBits(n) == Double.doubleToLongBits(other.n);
	}
	@Override
	public String toString() {
		return "Coppia [c1=" + c1 + ", c2=" + c2 + ", n=" + n + "]";
	}
	
}
