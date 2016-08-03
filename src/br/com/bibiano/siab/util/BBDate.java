package br.com.bibiano.siab.util;

import waba.sys.Convert;
import waba.sys.Settings;
import waba.util.Date;
import br.com.bibiano.siab.exception.BBIllegalArgumentException;

/**
 * Classe Util
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class BBDate extends Date {

	/** Quantida de milisegundo desde o dia 01/01/1970 at� hoje.*/
	public static final long SSK_MILLIS_UTIL_2003 = 1440688000000l;
	/** Constante que indica a diferen�a de dias entre duas datas.*/
	public static final int SSK_DIFF_DAYS = 1;
	/** Constante que indica a diferen�a de semanas entre duas datas.*/
	public static final int SSK_DIFF_WEEKS = 2;
	/** Constante que indica a diferen�a de m�ses entre duas datas.*/
	public static final int SSK_DIFF_MONTHS = 3;
	/** Constante que indica a diferen�a de anos entre duas datas.*/
	public static final int SSK_DIFF_YEARS = 4;

	/**
	*  M�todo que retorna a quantidade de milisegundos decorrida desde o dia
	*  01/01/1970 at� o presente dia.
	*
	*  @return double Quantidade de milisegundos.
	*/
	public static long getCurrentTimeMillis() {

		return new BBDateTime().getTimeLong();
	}

	/**
    *  M�todo de classe que cria um objeto de data de acordo com a data passada
    *  e formato.
    *
    *  @param date       Data. Pode usar qualquer separador diferente de n�mero.
    *  @param dateFormat Formato: De acordo com da classe
    *                    waba.sys.Settings.DATE_XXX.
    *
    *  @return SSDate Objeto para a manipula��o de datas.
    *  @throws BBIllegalArgumentException Data inv�lida.
    */
	public static BBDate valueOf(String date, byte dateFormat)
	    throws BBIllegalArgumentException {

		return new BBDate( date, dateFormat );
	}

	/**
    *  M�todo de classe que cria um objeto de data de acordo com a data passada
    *  e formato especificado pelas as configura��es do dispositivo.
    *  Settings.dateFormat.
    *
    *  @param date Data. Pode usar qualquer separador diferente de n�mero.
    *
    *  @return SSDate Objeto para a manipula��o de datas.
    *  @throws BBIllegalArgumentException Data inv�lida.
    */
	public static BBDate valueOf(String date)
	    throws BBIllegalArgumentException {

		return new BBDate( date );
	}

    /**
    *  M�todo que valida se uma determinada data � valida.
    *
    *  @param day   Dia.
    *  @param month M�s.
    *  @param year  Ano.
    *
    *  @return boolean true: Data v�lida / false: Data inv�lida.
    */
    public static boolean isValidDate(int day, int month, int year) {

		//n�meros al�m dos limites.
		if (day > 31 || day < 1 || month > 12 || month < 1) {
		    return false;

		//m�s de fevereiro.
		}else if (month == 2) {
		    if ((day == 29 && isLeapYear(year)) || day <= 28) {
		    	return true;
		    }else {
		    	return false;
		    }
		//dia 31.
		}else if (day == 31) {
			if (month == 1 || month == 3 || month == 5 || month == 7 ||
			    month == 8 || month == 10 || month == 12) {
				return true;
			}else {
				return false;
			}
		//qualquer outro dia � valido.
		}else {
			return true;
		}
	}

    /**
    *  M�todo que verifica se um determinado ano � bissexto.
    *
    *  @param year Ano.
    *
    *  @return boolean true: Bissexto / false: N�o bissexto.
    */
    public static boolean isLeapYear(int year) {

		if ( ( year & 399 ) == 0 ||  //year & 399 == year % 400
		     ( ( year & 3 ) == 0 &&  //year & 3 == year % 4
		       ( year & 99 ) != 0 ) ) { //year & 99 == year % 100
			return true;
		}else {
			return false;
		}
    }

    /**
    *  M�todo que convete o objeto data numa string.
    *
    *  @param day    Dia.
    *  @param month  M�s.
    *  @param year   Ano.
    *  @param format Formato. Especificado pelas constantes
    *                waba.sys.Settings.DATE_XXX.
    *
    *  @return String Data convertida numa string.
    */
    public static String formatDate(int day, int month, int year, byte format) {

    	return Date.formatDate( day, month, year, format );
    }

    /**
    *  M�todo que retorna a diferen�a entre duas datas. Esta diferen�a pode ser
    *  tanto em n�meros de dias, m�ses, semanas ou anos dependendo do  terceiro
    *  par�metro do m�todo onde � especificado  que  diferen�a  ser�  retornada.
    *  Casos as data sejam passada invertidas, a diferen�a ser� retornado com o
    *  valor negativo.
    *
    *  @param dateStart  Data inicial.
    *  @param dateFinish Data final.
    *  @param type       Tipo da diferen�a. Usa-se as constantes SSK_DIFF_XXX.
    *
    *  @return int Difere�a entre datas.
    */
    public static int diffBetweenDates(BBDate dateStart, BBDate dateFinish,
        int type) {

    	boolean unordered = false;

    	if ( dateStart.equals( dateFinish ) ) { //data iguais.
    		return 0;

    	//data final � menor que a inicial.
    	}else if ( dateFinish.isBefore( dateStart ) ) {
    		BBDate dateAux = dateStart;
    		dateStart = dateFinish;
    		dateFinish = dateAux;
    		unordered = true; //indica que a data estava invertida.
    	}

		int yearStart = dateStart.getYear();
		int yearFinish = dateFinish.getYear();

		int diffYears = yearFinish - yearStart;
		if ( type == SSK_DIFF_YEARS ) { //diferen�a de anos.
			if(dateStart.getMonth()>dateFinish.getMonth()){
				diffYears=diffYears-1;
			}else if(dateStart.getMonth()==dateFinish.getMonth() && dateStart.getDay()>dateFinish.getDay()){
				diffYears=diffYears-1;
			}
			return ( unordered ? -diffYears : diffYears );
		}else {
			int mountStart = dateStart.getMonth();
			int monthFinish = dateFinish.getMonth();
			int amoutMonths = 0;

			if ( diffYears == 0 ) { //datas do mesmo ano.
				amoutMonths = monthFinish - mountStart;

			}else { //anos diferentes.
				amoutMonths = ( 12 - mountStart ) + monthFinish;
				amoutMonths += --diffYears  * 12;
			}

			if ( type == SSK_DIFF_MONTHS ) { //diferen�a de m�ses.
				return ( unordered ? -amoutMonths : amoutMonths );

			}else {
				int amountDays = 0;
				for (int cMonth = 1; cMonth <= amoutMonths; cMonth++,
				    mountStart++) {

					if ( ( mountStart % 2 != 0 && mountStart != 9 &&
					       mountStart != 11 ) ||

					     ( mountStart == 10 || mountStart == 12 ||
					       mountStart == 8 ) ) { //m�ses com 31 dias.

					    amountDays += 31;

					}else if ( mountStart == 2 ) { //m�s de fevereiro.
						if ( isLeapYear( yearStart ) ) { //ano bissexto.
							amountDays += 29;

						}else {
							amountDays += 28;
						}

					}else { //qualquer outro tem 30 dias.
						amountDays += 30;
					}

					if ( mountStart % 12 == 0 ) {
						yearStart++;
						mountStart = 0;
					}
			    }

			    amountDays -= ( dateStart.getDay() -1 );
			    amountDays += dateFinish.getDay() -1;

			    if ( type == SSK_DIFF_WEEKS ) { //n�mero de semanas.
			    	//se n�o entrar aqui, � o n�mero de dias que ser� retornado.
			    	amountDays /= 7;
			    }

			    return ( unordered ? -amountDays : amountDays );
			}
		}
    }

	/**
    *  Contrutor que cria um objeto de data com a data atual e no formato
    *  de acordo com as configura��es do dispositivo.
    */
	public BBDate() {

	    super();
	}

	/**
    *  Construtor que cria um objeto de data de acordo com a data passada e
    *  formato.
    *
    *  @param date       Data. Pode usar qualquer separador diferente de n�mero.
    *                    A data deve ser deste tamanho 00/00/0000.
    *  @param dateFormat Formato: De acordo com da classe
    *                    waba.sys.Settings.DATE_XXX.
    *
    *  @throws BBIllegalArgumentException Data inv�lida.
    */
	public BBDate(String date, byte dateFormat)
	    throws BBIllegalArgumentException {

		super( date, dateFormat );

		if ( date == null || date.length() != 10 ) {
			throw new BBIllegalArgumentException(
			    date + ": Argumento inv�lido."
			);
		}

		boolean valid = false;
		if ( dateFormat == Settings.DATE_DMY ) {
			valid = isValidDate(
			    Convert.toInt( date.substring( 0, 2 ) ),
			    Convert.toInt( date.substring( 3, 5 ) ),
			    Convert.toInt( date.substring( 6, 10 ) )
			);

	    }else if ( dateFormat == Settings.DATE_MDY ) {
			valid = isValidDate(
				Convert.toInt( date.substring( 3, 5 ) ),
			    Convert.toInt( date.substring( 0, 2 ) ),
			    Convert.toInt( date.substring( 6, 10 ) )
			);

		}else { // dateFormat == Settings.DATE_YMD
			valid = isValidDate(
				Convert.toInt( date.substring( 6, 10 ) ),
			    Convert.toInt( date.substring( 3, 5 ) ),
			    Convert.toInt( date.substring( 0, 2 ) )
			);
		}

		if ( !valid ) { //data inv�lida.
			throw new BBIllegalArgumentException( date + ": Data inv�lida." );
		}
	}

	/**
    *  Construtor que cria um objeto de data de acordo com a data passada e
    *  formato especificado pelas as configura��es do dispositivo.
    *  Settings.dateFormat.
    *
    *  @param date       Data. Pode usar qualquer separador diferente de n�mero.
    *
    *  @throws BBIllegalArgumentException Data inv�lida.
    */
	public BBDate(String date) {

		this( date, Settings.dateFormat );
	}
	
	public static  boolean isDataValida(String vData){
		
		if(vData == null || "".equals(vData)){
			return false;
		}
		String[] datePart = Convert.tokenizeString(vData,'/');
		if(datePart.length<3){
			return false;			
		}
		int dia= Convert.toInt(datePart[0]);		
		int mes= Convert.toInt(datePart[1]);
		int ano= Convert.toInt(datePart[2]);
		
		if(ano<100){
			return false;
		}
		 if( mes > 12 || mes < 1 ){
			return false;
		 }
		 if( dia < 1 ){
				return false;
	     } 
		 if (mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10 || mes==12 ){
			    //'nesses meses pode ter 31 dias
			    if (dia > 31) {
			    	return false;
			    }
			    
		 }else if (mes==4 || mes==6 || mes==9 || mes==11){
			    //'nesses meses pode ter 31 dias
			    if (dia > 30) {
			    	return false;
			    }
			    
		 }else if (mes==2) {
			    //'en fevereiro temos que ver se sera ano bissexto
			    //'consigo o numero de ano de 4 cifras.
			    //'se nos derem um valor de 2 cifras < 31 se refere a 2000 mais esse valor
			    if( ano < 31) {
			      ano = ano + 2000;
			    //'se nos derem um valor de 2 cifras > 31 se refere a 1900 mais esse valor
			    }else if (ano < 100){
			      ano = ano + 1900;
			    }
			    //'calculo se o ano eh bissexto
			    //'se for divisivel por quatro e (nao divisivel por 100 ou divisivel por 400)
			    if (((ano % 4)==0) && ((ano % 100)!=0 || (ano % 400)==0)) {
				      //'eh bissexto
				      if (dia > 29){
				       return false;
				      }
			    }else{
				      //'NAO eh bissexto
				      if( dia > 28 ){
				    	  return false;
				      }else			    //'em todos os demais meses chegam a ter 30 dias
					    if (dia > 30){
					     return false;
					    }
			   }
			  //'se estou aqui eh porque todas as comprovacoes foram positivas				      
			    }	      
		return true;
	}
	
	public static int calculaIdade(String data) {

		String [] x = Convert.tokenizeString(data,'/');
		String []h =  Convert.tokenizeString((new BBDate()).toString(),'/');

		int anosProvisorio = Convert.toInt(h[2]) - Convert.toInt(x[2]);

		if(Convert.toInt(h[1]) < Convert.toInt(x[1])) {
		anosProvisorio -= 1;
		}
		else if(Convert.toInt(h[1]) == Convert.toInt(x[1])) {
		if(Convert.toInt(h[0]) < Convert.toInt(x[0])) {
		anosProvisorio -= 1;
		}
		}

		return anosProvisorio;
	}
}