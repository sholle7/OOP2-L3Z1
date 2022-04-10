package sladoledzinica;

import java.util.HashMap;
import java.util.Map;

public class Sladoled {
	
	private HashMap<Ukus,Integer> ukusi = new HashMap<>();
	private int velicinaCase,kolicina;
	
	public Sladoled(int velicinaCase){
		this.velicinaCase=velicinaCase;
	}
	//dohvatanje trenutne kolicine case
	public int dohvKolicinu() { return kolicina; }
	
	//dodavanje zadatog ukusa sa zadatom kolicinom u casu sa sladoledom
	public void dodaj(Ukus u,int kol) {		
		//ako dodavanjem zadate kolicine ne prelazimo velicinu case
		if(kolicina+kol<velicinaCase) {
			
			//ako ukus ne postoji u casi
			if(!(ukusi.containsKey(u)))ukusi.put(u,kol);
		
			//ako ukus postoji u casi, trazimo gde se nalazi i povecavamo mu vrednost
			else {
				for (Map.Entry<Ukus, Integer> ulaz : ukusi.entrySet()) {
		            if (ulaz.getKey().equals(u)) {
		                int i=ulaz.getValue();
		                ulaz.setValue(i+kol);
		            }
		        }
			}
			//azuriranje trenutne kolicine
			kolicina+=kol;
		}
		
		//ako dodavanjem zadate kolicine prelazimo velicinu case
		else {
			int k=velicinaCase-kolicina;
			
			//ako ukus ne postoji u casi
			if(!(ukusi.containsKey(u)))ukusi.put(u, k);
			
			//ako ukus postoji u casi, trazimo gde se nalazi i povecavamo mu vrednost
			else {
				for (Map.Entry<Ukus, Integer> ulaz : ukusi.entrySet()) {
		            if (ulaz.getKey().equals(u)) {
		                int i=ulaz.getValue();
		                ulaz.setValue(i+k);
		            }
		        }
			}
			//azuriranje trenutne kolicine
			kolicina+=k;
		}
	}
	
	//tekstualni opis case sladoleda
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		//prolazak kroz hash mapu uz pomoc for each petlje
		for(Map.Entry<Ukus, Integer>u:ukusi.entrySet()) {
			sb.append(u.getValue()).append("ml").append(u.getKey()).append("\n");
		}
		return sb.toString();
	}
	public void reset() {
		ukusi.clear();
		kolicina=0;
	}
}
