import java.util.ArrayList;
//Super klassen til Rute, Boks, Kolonne, Rad
public class Losning {
	Rute[]ruter;
	Brett brett;
	int dimensjon;
	Rute neste;
	Rute rute;
	Rad rad;

	int cnt = 0;

	Losning(int dimensjon, Brett brett){ //Konstruktør for klassen losning.
		this.dimensjon = dimensjon;
		this.brett = brett;
		ruter = new Rute[dimensjon];
	}

	// Metode for å sjekke om et tall er "lovlig". metoden returner false hvis tallet er "ulovlig" å sette inn
	public boolean lovlig(int tall){
	

		for(Rute rute : ruter ){

			if(rute.tall == tall){

				return false;
			}
		}
		return true;
	}

	public void printRuter() {
	
		for(int i=0; i< ruter.length; i++) {
			System.out.print(ruter[i] + " ");
		}

		System.out.println();
	}
	//Denne metoden legger til en rute
	public void add(Rute r){
		ruter[cnt++] = r;
	}
}
