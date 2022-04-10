package sladoledzinica;

import java.awt.Color;

public class Ukus {
	private String naziv;
	private Color boja;

	public Ukus(String naziv, Color boja){
		this.naziv=naziv;
		this.boja=boja;
	}
	
	//dohvatanje boje i naziva
	Color dohvBoja() { return boja; }
	String dohvNaziv() { return naziv; }
	
	//Ispitivanje jednakosti dva ukusa(jednaka su ako imaju ista imena)
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Ukus))return false;
		Ukus u=(Ukus)obj;
		return naziv.equals(u.dohvNaziv());
	}
	
	@Override
	public String toString() {
		return "["+ naziv +"]";
	}
}
