package br.com.bibiano.siab.util;

import br.com.bibiano.siab.exception.BBIllegalArgumentException;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.sys.Time;
import waba.util.Date;

/**
 * Classe Util
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class BBDateTime {

	/** Constante que indica a diferença de horas entre duas datas.*/
	public static final int SSK_DIFF_HOUR = 5;
	/** Constante que indica a diferença de minutos entre duas datas.*/
	public static final int SSK_DIFF_MIN = 6;
	/** Constante que indica a diferença de segundos entre duas datas.*/
	public static final int SSK_DIFF_SEC = 7;
	/** Constante que indica a diferença de milisegundos entre duas datas.*/
	public static final int SSK_DIFF_MILLI = 8;

	protected BBDate date; /** Objeto que armazena a data. */
	public Time time; /** Objeto que armazena a hora.*/
	
	/**
	 * 
	 * @param t1
	 * @param t2
	 * @param type
	 * @return
	 */
	public static long diffBetweenTimes(BBDateTime t1, BBDateTime t2, int type){
		
		boolean unordered = false;
		if ( t1.equals( t2 ) ) { //data iguais.
    		return 0;
    	//data final é menor que a inicial.
    	}else if ( t2.isBefore( t1 ) ) {
    		BBDateTime timeAux = t1;
    		t1 = t2;
    		t2 = timeAux;
    		unordered = true; //indica que a data estava invertida.
    	}

		if (type == BBDate.SSK_DIFF_YEARS || type == BBDate.SSK_DIFF_MONTHS ||
			type == BBDate.SSK_DIFF_WEEKS || type == BBDate.SSK_DIFF_DAYS) {
			
			return BBDate.diffBetweenDates(t1.date, t2.date, type);
		}

		int difDays =
			BBDate.diffBetweenDates(t1.date, t2.date, BBDate.SSK_DIFF_DAYS);
		
		long diffHours;
		//24 qtde de horas de um dia.
		diffHours = (difDays * 24) + t2.time.hour - t1.time.hour;
		if (type == SSK_DIFF_HOUR) {
			return (unordered ? -diffHours : diffHours);
		}
		long diffMins;
		//1440 qtde de minutos de um dia.
		diffMins = (difDays * 1440) + (diffHours * 60) + t2.time.minute - t1.time.minute;
		if (type == SSK_DIFF_MIN) {
			return (unordered ? -diffMins : diffMins);
		}
		long diffSecs;
		//86400 qtde de segundos de um dia.
		diffSecs = (difDays * 86400) + (diffMins * 60) + t2.time.second - t1.time.second;
		if (type == SSK_DIFF_SEC) {
			return (unordered ? -diffSecs : diffSecs);
		}
		long diffMillis;
		//86400000 qtde de segundos de um dia.
		diffMillis = (difDays * 86400000) + (diffSecs * 1000) + t2.time.millis - t1.time.millis;
		
		if (type == SSK_DIFF_MILLI) {
			return (unordered ? -diffMillis : diffMillis);
		}
		throw new BBIllegalArgumentException("Argumento type inválido.");
	}
	
	/**
	 * 
	 */
	public BBDateTime() {
		
		time = new Time();
		date = new BBDate();
	}

	/**
	 *
	 * @param datetime 
	 */
	public BBDateTime(String datetime) {
	
		this( datetime, Settings.dateFormat );
	}
	
	/**
	 *
	 * @param datetime 
	 */
	public BBDateTime(String datetime, byte dateFormat) {

		String[] parts = Convert.tokenizeString( datetime, ' ' );
		date = BBDate.valueOf( parts[0], dateFormat );
		time = new Time();
		time.day = date.getDay();
		time.month = date.getMonth();
		time.year = date.getYear();
		parts = Convert.tokenizeString( parts[1], Settings.timeSeparator );
		time.hour = Convert.toInt( parts[0] );
		time.minute = Convert.toInt( parts[1] );
		parts = Convert.tokenizeString( parts[2], '.' );
		time.second = Convert.toInt( parts[0] );
		if ( parts.length == 2 ) {
			time.millis = Convert.toInt( parts[1] );
		}
	}
	
	/**
	 * 
	 * @param dt
	 * @return
	 */
	public boolean isBefore(BBDateTime dt) {
		
		return getTimeLong() < dt.getTimeLong();
	}
	
	/**
	 * 
	 * @param dt
	 * @return
	 */
	public boolean isAfter(BBDateTime dt) {
		
		return getTimeLong() > dt.getTimeLong();
	}
	
	/**
	 * 
	 * @param dt
	 */
	public boolean equals(Object dt) {
		
		if ( dt instanceof BBDateTime ) {
			BBDateTime dta = ( BBDateTime ) dt;
			return getTimeLong() ==	dta.getTimeLong(); 
		}else {
			return false;
		}
	}
	
	/**
	 * Método que retorna a data no formato long. O valor retornado não
	 * contempla os milisegundos.
	 * @return Data formatada.
	 */
	public long getTimeLong() {
		
		long total = BBDate.SSK_MILLIS_UTIL_2003;
		total += (time.year - 2003) * 31536000000L;
		total += (time.month - 1) * 2592000000L;
		total += (time.day - 1) * 86400000L;
		total += time.hour * 3600000L;
		total += time.minute * 60000L;
		total += time.second * 1000L;
		total += time.millis;
		
		return total;
	}

	/**
	 * 
	 * @return
	 */
	public String toString() {

		Date dt = new Date( time.day, time.month, time.year );
		
		StringBuffer strb = new StringBuffer( 23 );
		strb.append( time.toString().substring( 0, 8 ) );
		strb.append( '.' + Convert.zeroPad( "" + time.millis, 3 ) );
		if ( !Settings.is24Hour ) {
			strb.append( time.hour >= 13 ? " PM": " AM" );
		}
		
		return dt.toString() + ' ' + strb.toString();
	}
	
	/*
	public static void main(String[] a) {
		
		Settings.dateFormat = Settings.DATE_DMY;
		Settings.dateSeparator = '/';
		Settings.timeSeparator = ':';
		Settings.is24Hour = false;
		
		SSDateTime d1 = new SSDateTime( "01/01/2004 04:10:00.005" );
		SSDateTime d2 = new SSDateTime( "01/01/2004 02:00:00.000" );

		System.out.println( d1 );
		System.out.println( d2 );
		System.out.println( d1.equals( d2 ) );
		System.out.println( d1.isBefore( d2 ) );
		System.out.println( d1.isAfter( d2 ) );
		System.out.println( SSDateTime.diffBetweenTimes(d1, d2, SSDateTime.SSK_DIFF_SEC));
	}
	*/
}