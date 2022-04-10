package sladoledzinica;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;

public class Sladoledzinica extends Frame{
	
	private AparatZaTocenje at;
	private MestoZaTocenje mt;
	private Panel ukusi = new Panel(new FlowLayout(FlowLayout.LEFT));
	private Panel sladoled = new Panel(new FlowLayout(FlowLayout.LEFT));
	private Panel p = new Panel(new GridLayout(2,1));
	private Button dodajUkus = new Button("Dodaj ukus");
	private TextField poljeZaNaziv = new TextField();
	private TextField poljeZaBoju = new TextField();
	private Label labelaZaNaziv = new Label("Naziv:");
	private Label labelaZaBoju = new Label("Boja:");
	private Label labelaZaSladoled1;
	private Label labelaZaSladoled2;

	public Sladoledzinica() {
		
		super("Sladoledzinica");
		
		at = new AparatZaTocenje();
		mt = new MestoZaTocenje(at);
		at.postaviMestoZaTocenje(mt);
		
		//podesavanje zeljene sirine i visine
		poljeZaBoju.setPreferredSize(new Dimension(70, 20));
		poljeZaNaziv.setPreferredSize(new Dimension(70, 20));
		
		labelaZaSladoled1 = new Label("Sladoled: ");
		labelaZaSladoled2 = new Label("");
		//labelaZaSladoled1.setAlignment(0);
		mt.azurirajLab(labelaZaSladoled2);
		
		//pravljenje juznog panela
		ukusi.setBackground(Color.CYAN);
		labelaZaBoju.setFont(new Font("Tahoma",Font.BOLD,14));
		labelaZaNaziv.setFont(new Font("Tahoma",Font.BOLD,14));
		labelaZaSladoled1.setFont(new Font("Tahoma",Font.BOLD,14));
		
		ukusi.add(labelaZaNaziv);
		ukusi.add(poljeZaNaziv);
		ukusi.add(labelaZaBoju);
		ukusi.add(poljeZaBoju);
		ukusi.add(dodajUkus);

		sladoled.add(labelaZaSladoled1);
		sladoled.add(labelaZaSladoled2);
		sladoled.setBackground(Color.LIGHT_GRAY);
		
		p.add(sladoled);
		p.add(ukusi);
		
		add(at, BorderLayout.CENTER);
		add(p,BorderLayout.SOUTH);
		
		dodajOsluskivace();	
		
		setVisible(true);
		setSize(400,400);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	//dodavanje osluskivaca 
	private void dodajOsluskivace() {
		//osluskivac za gasenje programa
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				at.dohvMestoZaTocenje().zavrsi();
				dispose();
			}
		});
		
		//osluskivac za dugme za dodavanje ukusa
		dodajUkus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {		
	
					if(at.dohvBrojUkusa()<1) at.dohvPanelUkusi().setLayout(new GridLayout(1,1));
					else if(at.dohvBrojUkusa()==1)at.dohvPanelUkusi().setLayout(new GridLayout(2,1));
					else at.dohvPanelUkusi().setLayout(new GridLayout(0,2));
					
					Ukus u =new Ukus(poljeZaNaziv.getText(),Color.decode("#"+poljeZaBoju.getText()));
					at.dodajUkus(u);
					revalidate();
				} catch (GUkus e1) { System.out.println("GRESKA");}
			}
		});
	}
	public static void main(String[] args) {
		new Sladoledzinica();
	}

}
