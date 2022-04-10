package sladoledzinica;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.util.ArrayList;

public class MestoZaTocenje extends Canvas implements Runnable{
	
	private Thread nit = new Thread(this);;
	private boolean radi;
	private AparatZaTocenje aparatZaTocenje;
	private Sladoled sladoled;
	private Ukus trenutniUkus;
	private int procenat,ypr,ystaro;
	private int brojac;
	private Label prikazSladoleda = new Label("");
	//lista za cuvanje boja svih prethodnik pravougaonika
	private ArrayList<Color> boje = new ArrayList<>();
	//broji nacrtane pravougaonike
	private int br; 

	public MestoZaTocenje(AparatZaTocenje ap){
		aparatZaTocenje=ap;
		kreni();
	}
	public Label dohvLabelu() { return prikazSladoleda; }
	public Thread dohvNit() { return nit; }
	public Sladoled dohvSladoled() { return sladoled; }
	//koriscen brojac radi osiguravanja od viseputnog iscrtavanja pravougaonika
	public synchronized void postaviBrojac() { brojac=1; }
	
	@Override
	public void paint(Graphics g) {	
		super.paint(g);
		
		if(sladoled.dohvKolicinu()==200) return;
		if(ypr==0) {
			ypr = getHeight();
			procenat = ypr/10+1;
		}
		if(ypr > 0 && brojac == 1) {
			
			//dodavanje ukusa u sladoled
			if(sladoled.dohvKolicinu()<200){
				br++;
				sladoled.dodaj(trenutniUkus, 20);
				boje.add(trenutniUkus.dohvBoja());
				
				//postavljanje novog teksta
				aparatZaTocenje.postaviTekst(sladoled.toString());
				prikazSladoleda.setText(sladoled.toString());
				revalidate();
			
				int i;
				//iscrtavanje prethodnih pravougaonika
				int d=getHeight(),pom=getHeight(),duz1;
				for(i=0;i<br-1;i++) {
					g.setColor(boje.get(i));
					d -= procenat;
					duz1=pom-d;
					pom=d;
					g.fillRect(0, d, getWidth(), duz1);		
				}
				
				g.setColor(trenutniUkus.dohvBoja());
				
				ystaro = ypr;
				ypr -= procenat;
				int duz = ystaro - ypr;
				//iscrtavanje poslednjeg pravougaonika
				g.fillRect(0, ypr, getWidth(), duz);
				//ako je sladoled popunjen omogucava se prodaja
				if(ypr<=0) {
					aparatZaTocenje.omoguciProdaju();
				}
			}
			brojac = 0;
		}
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized (this) {
					while(!radi) wait();
				}
				this.repaint();
				revalidate();
				Thread.sleep(500);			
			} 
		} catch (InterruptedException e) {}
	}
	
	public void postTrentniUkus(Ukus u) { trenutniUkus=u; }
	public boolean uToku() { return radi; }
	public synchronized void stani() { radi = false; }
	public synchronized void zavrsi() { nit.interrupt();; }
	public synchronized void nastavi() { radi=true; notifyAll(); }
	public synchronized void kreni() { 
		sladoled = new Sladoled(200);
		radi = true; 
		nit.start();
		notifyAll();
	}
	//poziva se posle prodaje - resetuje sladoled
	public synchronized void obrisiCrtez() {
		Graphics g = getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		ypr = 0;
		br=0;
		boje.clear();
		sladoled.reset();
	}
	//azuriranje labele za sladoled
	public void azurirajLab(Label labelaZaSladoled2) {
		prikazSladoleda = labelaZaSladoled2;
	}	
}
