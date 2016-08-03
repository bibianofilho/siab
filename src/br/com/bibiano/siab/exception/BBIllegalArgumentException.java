
package br.com.bibiano.siab.exception;

/**
 * Classe Exception
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */

public class BBIllegalArgumentException extends RuntimeException {

    /**
    *  Construtor padr�o.
    */
    public BBIllegalArgumentException() {}

	/**
	* Construtor.
	*
	* @param message Mensagem de exce��o.
	*/
    public BBIllegalArgumentException(String message) {

   	    super( message );
    }
}