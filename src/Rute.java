import java.io.*;
import java.util.Scanner;

public class Rute {
	int tall;
	Kolonne kolonne;
	Rad rad;
	Boks boks;
	Rute neste;
	Brett brett;

	Rute(Brett brett){ 			// konstruktør for klassen rute
		this.brett = brett;
	}

	//hjelpe metode for å konvertere fra integer til string
	public String toString (){

		return Integer.toString(tall);
	}
	// Denne metoden løser et sudoku brett, først prøver den å sette alle tall i seg, hvis den lykkes
	// for et tall så kalles samme metode i neste rute
	public void settTallMegOgResten(){

		if(this instanceof ForhaandsUtfyltRute )
			if(neste != null){
				neste.settTallMegOgResten();
			}else{
				brett.lagreBuffer(); // kaller på metoden lagrebuffer i klassen brett som lagrer tallet i en array
			}
		else{
			for(int i = 1; i <= brett.dimensjon; i++){

				if(boks.lovlig(i) && rad.lovlig(i) && kolonne.lovlig(i)){

					tall=i;

					if (neste == null){

						brett.lagreBuffer(); // Hvis det er et lovlig tall så blir det lagret i en array

					}
					else neste.settTallMegOgResten();

				}
				tall = 0; //nullstiller
			}
		}
	}
}