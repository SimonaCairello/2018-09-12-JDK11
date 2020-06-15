package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.poweroutages.model.Event.EventType;

public class Simulator {
	
	//Coda degli eventi
	private PriorityQueue<Event> queue;
	
	//Parametri di simulazione
	private Model model;
	private Map<Integer, Nerc> nercMap;
	
	//Modello del mondo
	private Integer K;
	
	//Parametri da stimare
	private Integer catastrofi;
	
	
	public void init(Integer K, Model model, Map<Integer, Nerc> idMap) {
		this.model = model;
		this.K = K;
		this.nercMap = idMap;
		
		this.catastrofi = 0;
		this.queue = new PriorityQueue<Event>();
		
		for(PowerOutage p : this.model.getPowerOutage()) {
			this.queue.add(new Event(EventType.INIZIO_GUASTO, p.getBegan(), p.getNerc(), p));
		}
		
		for(Integer key : this.nercMap.keySet()) {
			this.nercMap.get(key).setDonatore(false);
			this.nercMap.get(key).setBonus(0);
			this.nercMap.get(key).setDonazioni(new ArrayList<Donazione>());
		}
	}
	
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}


	private void processEvent(Event e) {

		switch(e.getType()) {
		
		case FINE_GUASTO:
			Nerc donatore = e.getDonatore();
			donatore.increaseBonus((int) ChronoUnit.DAYS.between(e.getPowerOutage().getBegan(), e.getPowerOutage().getFinished()));
			donatore.setDonatore(false);
			break;
			
		case INIZIO_GUASTO:
			Nerc guasto = e.getNerc();
			List<Donazione> donazioni = guasto.getDonazioni();
			Nerc nercDonatore = null;
			
			Integer trovati = 0;
			for(Donazione d : donazioni) {
				if(d.getTime().isAfter(e.getTime().minusMonths(this.K))) {
					if(!d.getNerc().isDonatore()) {
						trovati++;
						nercDonatore = d.getNerc();
					}
				}
			}
			
			if(trovati > 1 || trovati == 0) {
				nercDonatore = null;
				List<Adiacente> vicini = this.model.getVicini(e.getNerc());
				Collections.reverse(vicini);
				for(Adiacente a : vicini) {
					if(!a.getNerc().isDonatore()) {
						nercDonatore = a.getNerc();
						break;
					}
				}
			}
			
			if(nercDonatore != null) {
				nercDonatore.setDonatore(true);
				nercDonatore.addDonazione(new Donazione(guasto, e.getTime()));
				Event event = new Event(EventType.FINE_GUASTO, e.getPowerOutage().getFinished(), e.getNerc(), e.getPowerOutage());
				event.setDonatore(nercDonatore);
				this.queue.add(event);
			} else {
				this.catastrofi++;
			}
			
			break;
		
		}
		
	}
	
	
	public Integer getCatastrofi() {
		return this.catastrofi;
	}

}
