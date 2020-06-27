package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.poweroutages.model.Event.EventType;

public class Simulator {
	
	private Integer k;
	private Model model;
	private List<PowerOutage> powerOutages;
	private PriorityQueue<Event> queue;
	private List<Donazione> donazioni;
	private List<Donazione> donazioniAttive;
	
	private Integer catastrofi;
	private List<NercNumero> bonus;
	
	public void init(Integer k, List<PowerOutage> po, Model model) {
		this.k = k;
		this.model = model;
		this.powerOutages = new ArrayList<>(po);
		this.queue = new PriorityQueue<>();
		this.donazioni = new ArrayList<>();
		this.catastrofi = 0;
		this.bonus = new ArrayList<>();
		this.donazioniAttive = new ArrayList<>();
	}
	
	public void generateEvents() {
		for(PowerOutage p : powerOutages) {
			Event eb = new Event(EventType.INIZIO, p.getDateBegan(), this.model.getNerc().get(p.getNercId()));
			Event ef = new Event(EventType.FINE, p.getDateFinished(), this.model.getNerc().get(p.getNercId()));
			this.queue.add(eb);
			this.queue.add(ef);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);			
		}
	}
	
	public void processEvent(Event e) {
		switch(e.getTipo()) {
		
		case INIZIO:
			
			List<NercNumero> vicini = new ArrayList<>(this.model.getVicini(e.getNercAffetto()));
			
			this.pulisciDonazioni(e);
			List<Nerc> possibiliDonatori = new ArrayList<>(this.getPossibiliDonatori(e));
			
			// criterio 2
			if(possibiliDonatori.size()==0) {
				boolean flag = false;
				for(int i=vicini.size()-1; i>=0 && flag==false; i--) {
					Donazione donazione = new Donazione(vicini.get(vicini.size()-1).getNerc(), e.getNercAffetto(), e.getTime());
					if(this.controlloDonatore(donazione)) {
						donazioni.add(donazione);
						donazioniAttive.add(donazione);
						e.setNercAiutante(vicini.get(vicini.size()-1).getNerc());
						flag = true;
					}
				}
				if(!flag)
					catastrofi++;
			}
			
			// criterio 1
			else if(possibiliDonatori.size()==1) {
				donazioni.add(new Donazione(possibiliDonatori.get(0), e.getNercAffetto(), e.getTime()));
				donazioniAttive.add(new Donazione(possibiliDonatori.get(0), e.getNercAffetto(), e.getTime()));
				e.setNercAiutante(possibiliDonatori.get(0));
			}
			
			// caso in cui criterio 1 trova più di un NERC donatore
			else {
				boolean trovato = false;
				for(int i=vicini.size()-1; i>=0 && trovato==false; i--) {
					for(Nerc n : possibiliDonatori) {
						if(n.equals(vicini.get(i).getNerc())) {
							donazioni.add(new Donazione(n, e.getNercAffetto(), e.getTime()));
							donazioniAttive.add(new Donazione(n, e.getNercAffetto(), e.getTime()));
							e.setNercAiutante(n);
							trovato = true;
							break;
						}
					}
				}				
			}
			
			break;
			
		case FINE:
			
			List<Donazione> donAtt = new ArrayList<>(donazioniAttive);
			for(Donazione d : donAtt) {
				if(e.getNercAffetto().equals(d.getRicevente())) {
					donazioniAttive.remove(d);
					
					boolean incr = false;
					for(NercNumero nn : bonus) {
						if(nn.getNerc().equals(d.getDonatore())) {
							nn.incrementa((int) ChronoUnit.DAYS.between(e.getTime(), d.getInizio()));
							incr = true;
						}
					}
					
					if(incr==false) {
						bonus.add(new NercNumero(d.getDonatore(), 1));
					}
				}
			}			
			
			break;
		}
	}
	
	/**
	 * Pulisce la lista di donazioni effettuate eliminando quelle più vecchie
	 * di K mesi
	 *  
	 */
	
	public void pulisciDonazioni(Event e) {
		if(donazioni.isEmpty())
			return;
		
		List<Donazione> don = new ArrayList<>(this.donazioni);
		
		for(Donazione d : don) {
			if(d.getInizio().getMonthValue()==e.getTime().getMonthValue()-(this.k+1))
				donazioni.remove(d);
		}
		return;
	}
	
	/**
	 * restituisce una lista di possibili donatori, ovvero quelli che soddisfano il criterio 1 
	 * (hanno donato almeno una volta negli ultimi K mesi) e che non stanno donando in questo momento
	 *  
	 */
		
	public List<Nerc> getPossibiliDonatori(Event e) {
		List<Nerc> possibiliDonatori = new ArrayList<>();
		
		for(Donazione d : donazioni) {
			if(d.getDonatore().equals(e.getNercAffetto())) {
				Nerc donatore = d.getRicevente();
				Donazione don = new Donazione(donatore, e.getNercAffetto(), e.getTime());
				if(this.controlloDonatore(don)) {
					possibiliDonatori.add(donatore);
					return possibiliDonatori;
				}
			}
		}
		return possibiliDonatori;
	}
	
	/**
	 * controlla che la donazione che si ha intenzione di aggiungere non sia dopo una donazione
	 * già esistente nella lista di donazioni (il donatore sta già donando) 
	 *  
	 */
	
	public boolean controlloDonatore(Donazione don) {
		for(Donazione d : this.donazioniAttive) {
			if(d.getDonatore().equals(don.getDonatore()) && don.getInizio().isAfter(d.getInizio())) {
				return false;
			}
		}
		return true;
	}
	
	public Integer getCatastrofi() {
		return this.catastrofi;
	}
	
	public List<NercNumero> getBonus() {
		return this.bonus;
	}

}
