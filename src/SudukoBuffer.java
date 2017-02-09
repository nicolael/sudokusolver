import java.util.ArrayList;

public class SudukoBuffer {
	int current = 0;
	int teller = 0;
	int counter = 0;
	int [][]bufferLosning = new int[500][];

	//Denne metoden setter inn løsninger i en array(buffer), bufferen har en grense på 500 løsninger;
	public void settInn(int[] losning){

		if(teller < 500){

			bufferLosning[teller] = losning;

		}
		teller++; //oppdaterer telleren i bufferen

	}
	//Denne metoden returnerer en array med losninger
	public int [] get(){
	
			return bufferLosning[current++];
	
	}
	// returner et tall, som er antallet på løsninger
	public int hentAntallLosninger(){

		return teller;
	}
}
