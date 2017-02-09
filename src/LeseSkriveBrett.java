import java.io.*;
import java.util.Scanner;

public class LeseSkriveBrett {
	Brett brett;
	SudukoBuffer losning;
	Rute[][] rute;
	int teller = 0;
	int [][] tall;
	char[]tegn;
	
	//Denne metoden leser fra en fil og returner et objekt av klassen brett
	public Brett leseFraFil(File s){

		int brett = 0;
		int kolonne = 0;
		int rad = 0;
		try {	
			Scanner scan = new Scanner (s);

			brett = Integer.parseInt(scan.nextLine());
			rad = Integer.parseInt(scan.nextLine());
			kolonne = Integer.parseInt(scan.nextLine());
			tall = new int[brett][brett];
			while(scan.hasNext()){
				String d = scan.next();
				
				tegn = d.toCharArray();
				for( int i = 0; i < tegn.length; i++){
					if(tegn[i] == '.'){

						tall[teller][i] = 0;

					}
					else if (tegn[i] >= '1' && tegn[i] <= '9'){

						tall[teller][i] = ((int)tegn[i] - 48);

					}else if(tegn[i] >= 'A' && tegn[i] <= 'z'){

						tall[teller][i] = ((int)tegn[i] - 55);
					}else if(tegn[i]== '@'){
						tall[teller][i] = ((int)tegn[i] - 28);
					}

					//System.out.println(tegn[i]);	

				}
				teller++;
			}
		} catch (FileNotFoundException fnfe){
			System.err.printf("Could not open '%s':%n%s%n",
					"tekstfil", fnfe.getMessage());
			System.exit(2);

		}

		return new Brett(tall, brett, kolonne, rad );
	}
}

