package sladoledzinica;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AparatZaTocenje extends Panel{
	
	private MestoZaTocenje mestoZaTocenje;
	private Panel panelUkusi = new Panel(new GridLayout(0,2));
	private Button prodaja=new Button("Prodaja");
	private Label prikazSladoleda=new Label("");
	private Panel p = new Panel(new GridLayout(2,1));
	private List<Ukus> uk=new ArrayList<>();
	
	public AparatZaTocenje() {
		//osluskivac za prodaju
		prodaja.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {		
				mestoZaTocenje.stani();
				mestoZaTocenje.obrisiCrtez();
				prikazSladoleda.setText("");
				System.out.println(mestoZaTocenje.dohvLabelu().getText());
				mestoZaTocenje.dohvLabelu().setText("");
				prodaja.setEnabled(false);
			}
		});
		setLayout(new GridLayout(0,2));
		prodaja.setEnabled(false);
	}
	public Panel dohvPanelUkusi() {return panelUkusi; }
	public int dohvBrojUkusa() { return uk.size(); }
	public Button dohvButtonProdaja() { return prodaja; }
	public void postaviMestoZaTocenje(MestoZaTocenje mp) {
		mestoZaTocenje=mp; 
		p.add(prodaja);
		p.add(mestoZaTocenje);
		add(panelUkusi);
		add(p);
	}
	public void omoguciProdaju() {
		prodaja.setEnabled(true);
	}
	public MestoZaTocenje dohvMestoZaTocenje() { return mestoZaTocenje; }
	//dodavanje novog ukusa na panel
	public void dodajUkus(Ukus u) throws GUkus {
		//ispitivanje da li je data boja vec uneta
		for(Ukus q:uk) if(u.equals(q))throw new GUkus();
		Button b = new Button(u.dohvNaziv());
		//dodavanje ukusa u listu
		uk.add(u);	
		b.setBackground(u.dohvBoja());
		
		//osluskivac za button
		b.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mestoZaTocenje.stani();		
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//dodavanje pritisnutog ukusa ako sladoled nije veci od kapaciteta case
				if(mestoZaTocenje.dohvSladoled().dohvKolicinu()!=200) {
				mestoZaTocenje.postaviBrojac();
				for(Ukus q:uk) if(q.dohvNaziv().equals(b.getLabel()))mestoZaTocenje.postTrentniUkus(q);
				mestoZaTocenje.nastavi();
				}
			}
			});
		//dodavanje novog buttona na panel
		panelUkusi.add(b);
	}
	public String dohvTekst() {return prikazSladoleda.getText();}
	
	//postavljanje zadatog teksta labeli
	public void postaviTekst(String tekst) { prikazSladoleda.setText(tekst); revalidate();}
	
}
