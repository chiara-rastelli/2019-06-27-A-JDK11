package it.polito.tdp.crimes.model;

public class Coppia {

	private String tipologia1;
	private String tipologia2;
	private int peso;
	
	public Coppia(String a, String b, int c) {
		this.tipologia1 = a;
		this.tipologia2 = b;
		this.peso = c;
	}

	public String getTipologia1() {
		return tipologia1;
	}

	public void setTipologia1(String tipologia1) {
		this.tipologia1 = tipologia1;
	}

	public String getTipologia2() {
		return tipologia2;
	}

	public void setTipologia2(String tipologia2) {
		this.tipologia2 = tipologia2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Prima tipologia di reato = " + tipologia1 + "; seconda tipologia di reato = " + tipologia2 + "; peso= " + peso;
	}
	
}
