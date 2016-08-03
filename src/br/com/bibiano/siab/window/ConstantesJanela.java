package br.com.bibiano.siab.window;

import waba.fx.Color;
import waba.sys.Settings;
/**
 * Classe Window
 * 
 * @version 1.0 
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class ConstantesJanela {

    /**
	 * Constante que define a cor azul.
	 */
	public static final Color BLUE = new Color( 44, 0, 139 );
	/**
	 * Constante que define a cor laranja.
	 */
	public static final Color ORANGE = new Color( 255, 190, 70 );
	/**
	 * Constante que define a cor azul para o grid.
	 */
	public static final Color BLUEGRID = new Color(28, 80, 178);
	
	
	/**
	 * Constante que define o fator horizontal.
	 */
	public static final float FATOR_X = Settings.screenWidth / 160.0f;
	
	/**
	 * Constante que define o fator horizontal.
	 */
	public static final float FATOR_Y = Settings.screenHeight / 160.0f;
			
	/**
	 * Constante que define a altura de um botão.
	 */
	public static final int ALTURA_BOTAO = (int)(14 * FATOR_Y);
	
	/**
	 * Constante que define a altura de um texto.
	 */
	public static final int ALTURA_TEXTO = (int)(12 * FATOR_Y);	
	
	/**
	 * Construtor privado, classe não pode gerar instância.
	 */
	private ConstantesJanela() {}
}