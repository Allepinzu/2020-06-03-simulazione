package it.polito.tdp.PremierLeague.model;

public class Arco {

	private Player p1;
	private Player p2;
	private int somma1;
	private int somma2;
	private double peso;
	public Arco(Player p1, Player p2, int somma1, int somma2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.somma1 = somma1;
		this.somma2 = somma2;
		this.peso = peso;
	}
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	public int getSomma1() {
		return somma1;
	}
	public void setSomma1(int somma1) {
		this.somma1 = somma1;
	}
	public int getSomma2() {
		return somma2;
	}
	public void setSomma2(int somma2) {
		this.somma2 = somma2;
	}
	public double getPeso() {
		return (double) Math.abs(this.getSomma1()-this.getSomma2());
	}
	public void setPeso(double peso) {
		this.peso = Math.abs(this.getSomma1()-this.getSomma2());
	}
	
}
