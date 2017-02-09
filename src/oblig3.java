import java.io.*;
import java.util.Scanner;

import javax.swing.JFileChooser;
public class oblig3 {

	public static void main (String[] args){
		File fil = new File("");
		boolean brukGUI = true;
		//hvis ingen argumenter, JFileChooser blir kalt
		if(args.length>0){
			fil= new File(args[0]);
			if(args.length==2){
				brukGUI = false;
			}
		}
		else{
			fil = velgFil();
		}
		
		LeseSkriveBrett leseSkrivebrett = new LeseSkriveBrett();
		
		//oppretter brett utifra filen som blir lest;
		Brett brett = leseSkrivebrett.leseFraFil(fil);
	
		//Hvis ingen argumenter Så vil GUI starte,
		if(brukGUI){
			
			SudokuGUI gui = new SudokuGUI(brett.dimensjon,brett.rad,brett.kolonne);
			gui.board=brett;
			gui.settTall(leseSkrivebrett.tall);
			long timer = System.nanoTime();
			brett.brett[0][0].settTallMegOgResten();
			long nytime = System.nanoTime();
			double ferdig = (double)(nytime-timer)/1000000000;
			System.out.println("time elapsed : " + ferdig);
			gui.lagEtikket();
			brett.antallLosninger();
					
		}else { 
			long timer = System.nanoTime();
			brett.brett[0][0].settTallMegOgResten();
			long nytime = System.nanoTime();
			double ferdig = (double)(nytime-timer)/1000000000;
			System.out.println("time elapsed : " + ferdig);
			
			brett.antallLosninger();
			brett.SkriveBrett(args[1]);
			
		}
	}
	//Denne metoden starter JFileChooser
	public static File velgFil(){
		JFileChooser velg = new JFileChooser();
		int fil = velg.showOpenDialog(null);
		if(fil == JFileChooser.APPROVE_OPTION){
			return new File(velg.getSelectedFile().getPath());
		}else{
			return null;
		}
	}

}
