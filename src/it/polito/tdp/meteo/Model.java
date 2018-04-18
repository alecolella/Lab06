package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO meteoDAO = new MeteoDAO();
	private String soluzioneString;
	private List<SimpleCity> soluzione;
	private List<SimpleCity> citta;
	private int giorniTotali = 0;
	private double bestPunteggio;

	public Model() {
 
		citta= new ArrayList<SimpleCity>();
		
		SimpleCity torinoSc = new SimpleCity("torino");
		SimpleCity milanoSc = new SimpleCity("milano");
		SimpleCity genovaSc = new SimpleCity("genova");
		
		citta.add(torinoSc);
		citta.add(milanoSc);
		citta.add(genovaSc);
	}

	public String getUmiditaMedia(int mese) {

		Citta torino = new Citta("torino");
		Citta milano = new Citta("milano");
		Citta genova = new Citta("genova");
		
		torino.setUmiditaMedia(meteoDAO.getAvgRilevamentiLocalitaMese(mese, torino.getNome()));
		genova.setUmiditaMedia(meteoDAO.getAvgRilevamentiLocalitaMese(mese, genova.getNome()));
		milano.setUmiditaMedia(meteoDAO.getAvgRilevamentiLocalitaMese(mese, milano.getNome()));
		
		return torino.getNome()+" "+torino.getUmiditaMedia()+"\n"+milano.getNome()+" "+milano.getUmiditaMedia()+"\n"+genova.getNome()+" "+genova.getUmiditaMedia()+"\n";
	}

	public String trovaSequenza(int mese) {
		//setRilevamenti(mese);
		soluzioneString ="";
		soluzione = new ArrayList<SimpleCity>();
		int step = 0;
		bestPunteggio = (double)System.nanoTime();
		
		
		List<SimpleCity> parziale = new ArrayList<SimpleCity>();
		//parziale.add(new SimpleCity("torino"));
		recursive(step,parziale,mese);
		
		
		
		
		for(SimpleCity sc : soluzione) {
			soluzioneString += sc.getNome()+"\n";
		}
		
		return soluzioneString;
	}

	private void recursive(int step, List<SimpleCity> parziale, int mese) {
		giorniTotali++;
		if(parziale.isEmpty()) {
		for(SimpleCity sc : citta) {
			sc.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, sc.getNome()));
			
			//System.out.println(sc.getNome()+" "+sc.getCosto(30));
		}
		//System.out.println("++++++++++++++++++++");
		}
		
		//condizione di terminazione
		if(giorniTotali > Model.NUMERO_GIORNI_TOTALI) {
			
			return;
		}
		
		//se parziale ha 15 giorni
		if(giorniTotali == Model.NUMERO_GIORNI_TOTALI) {
			
			//System.out.println("giorniTot = numero totali");
			boolean visitate = true;
			//Controllo che ogni città sia visitata almeno una volta
			for(SimpleCity sc : citta) {
				//System.out.println(sc.getNome()+" "+sc.getCounter());
				if(sc.getCounter()==0) {
					visitate = false;
					
				}
			}
		if(punteggioSoluzione(parziale,step) < bestPunteggio && visitate == true && this.controllaParziale(parziale)) {
				bestPunteggio = punteggioSoluzione(parziale,step);
				soluzione = new ArrayList<SimpleCity>(parziale);
				//System.out.println(giorniTotali+" "+bestPunteggio+"**"+parziale.toString());
			}
			
		}
		
		//Costruire parziale
		
//		for(SimpleCity sc : citta) {
//			System.out.println(sc.getCosto(5));
//		}
		
		for(SimpleCity sc : citta) {
			//if(parziale.size()>=3) {
		
				
			//}
				
			
			parziale.add(sc);
				
				
				sc.increaseCounter();
				//giorniTotali++;
				
				if(this.controllaParziale(parziale)) {
				recursive(step+1,parziale,mese);
				}
				parziale.remove(sc);
				sc.descreaseCounter();
				//giorniTotali--;
				
				}
	}
		//}
		
		
		//Aggiornare contatore giorni totali in città
		
	
	

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata, int step) {

		double score = 0.0;
		for(SimpleCity sc : soluzioneCandidata) {
			score += sc.getCosto(step);
			for(int i = 1; i<soluzioneCandidata.size(); i++) {
				if(!(sc.getNome().equals(soluzioneCandidata.get(i-1).getNome()))){
					score+=100;
				}
			}
		}
		
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		//Controllo che non trascorra in tutto più di max giorni in una città
		for(SimpleCity sc : citta) {
			if(sc.getCounter()>NUMERO_GIORNI_CITTA_MAX)
				return false;
		
		}
		//Deve sostare 3 giorni
		for(int i = 0; i<parziale.size()-2 && i>=3; i++) {
			if(!(parziale.get(i).getNome().equals(parziale.get(i+1).getNome()) && parziale.get(i).getNome().equals(parziale.get(i+2).getNome())))
			
					//if(!((sc.getNome().equals(parziale.get(i+1).getNome())) && sc.getNome().equals(parziale.get(i+2).getNome()))){
				
		return false;
	}
			
		
		return true;
	
	}
	
	//private void setRilevamenti(int mese) {
//		Citta torino = new Citta("torino");
//		Citta milano = new Citta("milano");
//		Citta genova = new Citta("genova");
//		SimpleCity torinoSc = new SimpleCity("torino");
//		SimpleCity milanoSc = new SimpleCity("milano");
//		SimpleCity genovaSc = new SimpleCity("milano");
//		
//		torino.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, torino.getNome()));
//		genova.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, genova.getNome()));
//		milano.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, milano.getNome()));
//		
//		torinoSc.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, torino.getNome()));
//		milanoSc.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, milano.getNome()));
//		genovaSc.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, genova.getNome()));
	//}
	

}
