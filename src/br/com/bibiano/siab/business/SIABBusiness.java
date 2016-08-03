package br.com.bibiano.siab.business;

import waba.util.Date;

/**
 * Classe Business
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

public class SIABBusiness {
	public static final String CREATORID = "SIAB"; 
	
	
	
	public static int getMes() {
		return  (new Date()).getMonth();
	}
	
	public static int getAno() {
		return  (new Date()).getYear();
	}
}
