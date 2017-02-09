import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/** 
 * Tegner ut et Sudoku-brett.
 * @author Christian Tryti
 * @author Stein Gjessing
 */

public class SudokuGUI extends JFrame {

	private final int RUTE_STRELSE = 50;	/* St�rrelsen p� hver rute */
	private final int PLASS_TOPP = 50;	/* Plass p� toppen av brettet */

	private JTextField[][] brett;   /* for � tegne ut alle rutene */
	private int dimensjon;		/* st�rrelsen p� brettet (n) */
	private int vertikalAntall;	/* antall ruter i en boks loddrett */
	private int horisontalAntall;	/* antall ruter i en boks vannrett */
	Brett board;
	SudukoBuffer buffer;
	LeseSkriveBrett fyllUt;
	String tilfil="losninger.txt";

	/** Lager et brett med knapper langs toppen. */
	public SudokuGUI(int dim, int hd, int br) {
		dimensjon = dim;
		vertikalAntall = hd;
		horisontalAntall = br;

		brett = new JTextField[dimensjon][dimensjon];

		setPreferredSize(new Dimension(dimensjon * RUTE_STRELSE, 
				dimensjon  * RUTE_STRELSE + PLASS_TOPP));
		setTitle("Sudoku " + dimensjon +" x "+ dimensjon );
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel knappePanel = lagKnapper();
		JPanel brettPanel = lagBrettet();


		getContentPane().add(knappePanel,BorderLayout.SOUTH);
		getContentPane().add(brettPanel,BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	/** 
	 * Lager et panel med alle rutene. 
	 * @return en peker til panelet som er laget.
	 */
	private JPanel lagBrettet() {

		int topp, venstre;
		JPanel brettPanel = new JPanel();

		brettPanel.setLayout(new GridLayout(dimensjon,dimensjon));
		brettPanel.setAlignmentX(CENTER_ALIGNMENT);
		brettPanel.setAlignmentY(CENTER_ALIGNMENT);
		setPreferredSize(new Dimension(new Dimension(dimensjon * RUTE_STRELSE, 
				dimensjon * RUTE_STRELSE )));		
		for(int i = 0; i < dimensjon; i++) {
			/* finn ut om denne raden trenger en tykker linje p� toppen: */
			topp = (i % vertikalAntall == 0 && i != 0) ? 4 : 1;
			for(int j = 0; j < dimensjon; j++) {
				/* finn ut om denne ruten er en del av en kolonne 
				   som skal ha en tykkere linje til venstre:       */
				venstre = (j % horisontalAntall == 0 && j != 0) ? 4 : 1;

				JTextField ruten = new JTextField();
				ruten.setBorder(BorderFactory.createMatteBorder
						(topp,venstre,1,1, Color.black));
				ruten.setHorizontalAlignment(SwingConstants.CENTER);
				ruten.setPreferredSize(new Dimension(RUTE_STRELSE, RUTE_STRELSE));
				ruten.setText("A");
				brett[i][j] = ruten;
				brettPanel.add(ruten);
			}
		}
		return brettPanel;
	}

	/** 
	 * Lager et panel med noen knapper. 
	 * @return en peker til panelet som er laget.
	 */
	private JPanel lagKnapper() {

		JPanel knappPanel = new JPanel();
		knappPanel.setLayout(new BoxLayout(knappPanel, BoxLayout.X_AXIS));

		JButton nesteKnapp = new JButton("Neste løsning");
		JButton tilfilKnapp = new JButton("Skriv løsning(er) til fil");

		knappPanel.add(nesteKnapp);
		knappPanel.add(tilfilKnapp);
		tilfilKnapp.addActionListener(new ActionListener(){			
			public void actionPerformed(ActionEvent a){
				board.SkriveBrett(tilfil);
			}
		});
		//nesteKnapp fyller ut brettet med løsningen
		nesteKnapp.addActionListener(new ActionListener(){			
			public void actionPerformed(ActionEvent a){
				System.out.println("working");
				String[] b = board.tilBrettet();
				if(b == null){
					System.out.println("INGEN FLERE LØSNINGER!!");
					return;
				}
				//String minetall = board.printLosning();
				int []tall = new int[dimensjon*dimensjon];
				for (int d =0; d<tall.length; d++){
					tall[d]= Integer.parseInt(b[d]);
				}	
				
				for(int i=0; i<brett.length; i++){
					for(int k=0; k<brett[i].length; k++){

						if(b[i]!= null){
							if (tall[i*board.dimensjon+k] > 9) {
								if (tall[i*board.dimensjon+k] >= 10 && tall[i*board.dimensjon+k] <= 35) {
									char c = (char)(tall[i*board.dimensjon+k] + 55);
									brett[i][k].setText("" + c);
								} else if (tall[i*board.dimensjon+k] == 36) {
									char c = (char)(tall[i*board.dimensjon+k] + 28);
									brett[i][k].setText("" + c);
								} else {
									brett[i][k].setText("" + '?');
								}
							} else {
								brett[i][k].setText("" + tall[i*board.dimensjon+k]);
							}
						}
					}
				}
			}
		});
		return knappPanel;
	}
	//Denne metoden lager en label. label inneholder en tekst som forteller hvor mange løsninger et brett har
	public void lagEtikket(){
		JLabel label = new JLabel();
		label.setText(board.antallLosninger());
		getContentPane().add(label,BorderLayout.NORTH);
		pack();
	}
	//Denne metoden setter inn start tallene for et brett, den konverterer også tall til characters
	//hvis det trengs
	public void settTall(int[][] tall){
		for(int i=0; i<tall.length; i++){
			for(int j=0; j<tall[i].length; j++){
				if(tall[i][j]!= 0){
					if (tall[i][j] > 9) {
						if (tall[i][j] >= 10 && tall[i][j] <= 35) {
							char c = (char)(tall[i][j] + 55);
							brett[i][j].setText("" + c);
						} else if (tall[i][j] == 36) {
							char c = (char)(tall[i][j] + 28);
							brett[i][j].setText("" + c);
						} else {
							brett[i][j].setText("" + '?');
						}
					} else {
						brett[i][j].setText("" + tall[i][j]);
					}
				}else{
					brett[i][j].setText("");
				}
			}
		}
	}
}



