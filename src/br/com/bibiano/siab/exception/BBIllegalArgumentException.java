
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
    *  Construtor padrão.
    */
    public BBIllegalArgumentException() {}

	/**
	* Construtor.
	*
	* @param message Mensagem de exceção.
	*/
    public BBIllegalArgumentException(String message) {

   	    super( message );
    }
}