import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Brett {

	Rute[][] brett;
	int teller = 0;
	int dimensjon;
	int kolonne;
	int rad;
	int [][]tall;
	Rad [] rader;
	Kolonne [] kol;
	Boks[][] bokser;

	SudukoBuffer buffer = new SudukoBuffer();

	Brett(int [][] tall, int dimensjon, int kolonne, int rad){ // Konstruktør for brett klassen, intialiserer array, kolonner, rader, bokser
		this.dimensjon = dimensjon;
		this.kolonne = kolonne;
		this.rad = rad;
		this.tall = tall;
		brett = new Rute[this.dimensjon][this.dimensjon];
		rader = new Rad[this.dimensjon];
		kol = new Kolonne[this.dimensjon];
		bokser = new Boks[this.kolonne][this.rad];
		lageBrett();
		
	}
	//Lagrer løsninger i en array og sender dem til en buffer;
	public void lagreBuffer(){

		int []losning = new int[dimensjon* dimensjon];

		for(int i= 0; i<brett.length; i++){
			for(int j=0; j< brett[i].length; j++){
				losning[i*dimensjon+j] = brett[i][j].tall;
			}
		}
		buffer.settInn(losning);
	}
	// Denne metoden returner en String som består av lang linje med tall(løsninger på et brett)
	public String printLosning(){
		System.out.println("------------");
		String tilfil="";
		int[]losning = buffer.get();
		for(int a=0; a<losning.length; a++){
				tilfil+= Integer.toString(losning[a]);	
			}
	
		return tilfil;
	}
	
	//denne metoden returnerer antall løsninger et brett har.
	public String antallLosninger(){
		String antall = Integer.toString(buffer.hentAntallLosninger());
		System.out.println("Dette brettet har : " + antall + " løsninger");
		return "Dette brettet har " + antall + " løsninger";
	}
	
	//denne metoden returnerer en array med alle løsninger.
	public String[] tilBrettet(){
		System.out.println("**********");
		String [] bufferLosn = new String[dimensjon*dimensjon];

		int[]losning2 = buffer.get();
		if(losning2==null){
			return null;
		}
		for(int i =0; i<dimensjon*dimensjon; i++){
			bufferLosn[i]= Integer.toString(losning2[i]);
		}

		for(int k=0; k<dimensjon; k++){
			for(int l =0; l<dimensjon; l++){
				System.out.print(bufferLosn[k*dimensjon+l] + " ");
			}
			System.out.println();
		}
		return bufferLosn;
	} 
	// denne metoden oppretter brettet med rader, koloner og bokser
	// her opprettes objekter av klassen Boks, Kolonne og rad for hver rad, kolonne og boks på brettet
	public void lageBrett(){

		for( int k = 0; k < rader.length; k++){
			rader[k] = new Rad(dimensjon, this); // oppretter en rad
			rader[k].plass = k;
		}

		for(int t = 0; t < kol.length; t++){
			kol[t] = new Kolonne(dimensjon, this); // oppretter en kolonne 
			kol[t].plass = t;
		}
		for(int f = 0; f < kolonne; f++){
			for( int g= 0; g < rad; g++){

				bokser[f][g] = new Boks(dimensjon, this); // oppretter en boks
			}		
		}

		Rute forrige = null;
		for(int i = 0; i < tall.length; i++){
			for(int j= 0; j< tall[i].length; j++){
				if(tall[i][j] == 0){
				
					brett[i][j] = new Rute(this); // oppretter en rute hvis plassen er ledig

					if(forrige != null){

						forrige.neste = brett[i][j];
					}
					forrige = brett[i][j];

					brett[i][j].rad = rader[i];
					rader[i].ruter[j] = brett[i][j];

					brett[i][j].kolonne = kol[j];
					kol[j].ruter[i] = brett[i][j];

					brett[i][j].boks = bokser[i/rad][j/kolonne];
					bokser[i/rad][j/kolonne].add(brett[i][j]);
					rader[i].add(brett[i][j]);
					kol[j].add(brett[i][j]);
					brett[i][j].tall = tall[i][j];
					tall[i][j] = brett[i][j].tall;


				}else if(tall[i][j] != 0){

					brett[i][j] = new ForhaandsUtfyltRute(this); // oppretter en forhåndsutfyltrute hvis plassen er forskjellig fra null

					if(forrige != null){

						forrige.neste = brett[i][j];
					}
					forrige = brett[i][j];

					brett[i][j].rad = rader[i];
					rader[i].ruter[j] = brett[i][j];

					brett[i][j].kolonne = kol[j];
					kol[j].ruter[i] = brett[i][j];

					brett[i][j].boks = bokser[i/rad][j/kolonne];
					brett[i][j].boks = bokser[i/rad][j/kolonne];
					bokser[i/rad][j/kolonne].add(brett[i][j]);
					rader[i].add(brett[i][j]);
					kol[j].add(brett[i][j]);
					brett[i][j].tall = tall[i][j];

				}
			}
		}
	}
	//Denne metoden skriver til til;
	public void SkriveBrett(String s){
		buffer.current=0;

		File f = new File(s);
		PrintWriter pw = null;
		try{
			pw = new PrintWriter(s);
			for(int t=0;t < buffer.teller; t++) {
				int[] losning = buffer.get();

				for (int i = 0; i < losning.length; i++) {
					String temp = Integer.toString(losning[i]);

					if (i==0) {
						pw.print(temp);
					} else {
						if (i%(dimensjon) == 0) {
							pw.print("// " + temp);
						} else {
							pw.print(temp);
						}
					}
				}
				pw.println();
			}

		}catch(FileNotFoundException fnfe){
			System.err.printf("Could not open '%s':%n%s%n",
					s , fnfe.getMessage());
			System.exit(2);
		}
		buffer.current = 0;
		System.out.println("Løsninger har blitt skrevet til fil");
		pw.close(); // lukker fila
	}
}


