package it.polito.tdp.zaino;

import java.util.*;

public class Zaino {
	
	private int capienza;		//peso max sopportato dallo zaino
	private List<Pezzo> pezzi;	//pezzi da provare ad inserire

	/**
	 * Inizializza un nuovo problema dello zaino
	 * @param capienza Massimo peso che lo zaino può sopportare
	 */
	public Zaino(int capienza){
		this.capienza=capienza;
		pezzi=new ArrayList<Pezzo>();
	}
	
	/**
	 * Aggiunge un nuovo pezzo al problema dello zaino da risolvere. Il nuovo pezzo dev'essere diverso da quelli esistenti.
	 * @param p il {@link Pezzo} da aggiungere
	 */
	public void add(Pezzo p){
		if(!pezzi.contains(p))
			pezzi.add(p);
		else
			throw new IllegalArgumentException("Pezzo duplicato "+p);
	}
	
	/**
	 * Calcola il costo di una soluzione parziale
	 * @param parziale
	 * @return
	 */
	private int costo(Set<Pezzo> parziale){
		int r=0;
		for(Pezzo p : parziale)
			r+=p.getCosto();
		return r;
	}
	
	/**
	 * Calcola il peso di una soluzione parziale
	 * @param parziale
	 * @return
	 */
	private int peso(Set<Pezzo> parziale){
		int r=0;
		for(Pezzo p : parziale)
			r+=p.getPeso();
		return r;
	}
	
	private void scegli(Set<Pezzo> parziale, int livello, Set<Pezzo> best){
		if(costo(parziale)>costo(best)){
			//WOW! Trovata una soluzione migliore, devo salvarla in 'best'
			best.clear();
			best.addAll(parziale);
			System.out.println(parziale);
		}
		
		for(Pezzo p : pezzi){
			if(!parziale.contains(p)){
				//Pezzo 'p' non c'è ancora, provo a metterlo se non supera la capacità dello zaino
				if(peso(parziale)+p.getPeso()<=capienza){
					//Prova a mettere pezzo 'p' nello zaino 
					parziale.add(p);
					//Delega la ricerca al livello successivo
					scegli(parziale, livello+1, best);
					//Rimetti le cose a posto (togli pezzo 'p')
					parziale.remove(p);
				}
			}
		}
	}
	
	public Set<Pezzo> risolvi(){
		Set<Pezzo> parziale=new HashSet<Pezzo>();
		Set<Pezzo> best=new HashSet<Pezzo>();
		scegli(parziale, 0, best);
		return best;
	}
	
	public static void main(String[] args) {
		
		//Definizione del problema (inizializzazione)
		Zaino z=new Zaino(15);
		z.add(new Pezzo(12, 4, "Verde"));
		z.add(new Pezzo(2, 2, "Azzurro"));
		z.add(new Pezzo(1, 1, "Salmone"));
		z.add(new Pezzo(4, 10, "Giallo"));
		z.add(new Pezzo(1, 2, "Grigio"));
		
		//Metodo risolvi() per ottenere la soluzione del problema 
		Set<Pezzo> soluzione=z.risolvi();
		
		System.out.println(soluzione);
		
	}

}
