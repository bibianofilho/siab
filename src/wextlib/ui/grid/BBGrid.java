package wextlib.ui.grid;


import waba.fx.Color;
import waba.fx.FontMetrics;
import waba.fx.Graphics;
import waba.sys.Convert;
import waba.sys.Settings;
import waba.ui.Container;
import waba.ui.Event;
import waba.ui.PenEvent;
import waba.ui.Timer;

public class BBGrid extends Grid {
	/** Vari�vel que guarda a quantidade de colunas.*/
	protected int colCount;

	/** Vari�vel para armazenar texto do hint. */
	private String[] textToWrite;
	/** Vari�vel para guardar o n�mero de linhas de texto do hint. */
	private int nbLine;
	/** Vari�vel que guarda o container no qual o grid foi adicionado. */
	private Container container;
	/** Timer para contagem do tempo para mostrar o hint. */
	private Timer mostrar;
	/** Timer para contagem do tempo que o hint fica aparecendo. */
	private Timer mostrando;
	/** Variavel que guarda a posi��o X do tap da stylus. */
	private int coordPenX = -1;
	/** Variavel que guarda a posi��o Y do tap da stylus. */
	private int coordPenY = -1;
	/** Constante de estado para o hint. */
	private final int MOSTRAR = 0;
	/** Constante de estado para o hint. */
	private final int ESCONDIDO = -1;
	/** Vari�vel de controle de estado do hint. */
	private int estado = ESCONDIDO;
	/** Vari�vel de cor do background do hint. */
	private Color _hintBackColor = Color.BRIGHT;
	/** Vari�vel de cor do texto do hint. */
	private Color _hintForeColor = Color.BLACK;
	/** Vari�vel que guarda o tempo de espera do hint, em milisegundos. */
	private int _hintDelay = 500;
	/** Vari�vel que guarda o tempo de mostra do hint, em milisegundos. */
	private int _hintDisplay = 3000;
	private int _length = 0;
	
	protected int btnX;
	protected int offset;
	protected boolean simpleBorder; // used by PopList
	
	public BBGrid() {
		super();
		// TODO Auto-generated constructor stub
		mostrar = new Timer();
		mostrando = new Timer();
	}

	/**
	 * M�todo que trata todos os eventos recebidos pelo controle.
	 * @param event Evento ocorrido.
	 */
	public void onEvent(Event event)
	{
		super.onEvent(event);
		//container.onEvent(event);

		if(event.type == PenEvent.PEN_DOWN && estado == ESCONDIDO)
	    {
	         mostrar = addTimer(_hintDelay);
	         estado = MOSTRAR;
	         PenEvent pe = (PenEvent)event;
	         coordPenX = pe.x;
	         coordPenY = pe.y;
	    }
		else if(mostrar.triggered && estado == MOSTRAR)
	    {   
			drawHint(coordPenX,coordPenY,getCellText(getUserSelectedCellID()));    
		    removeTimer(mostrar);
		    estado = ESCONDIDO;
		    mostrando = addTimer(_hintDisplay);
	    }
		else if(event.type == PenEvent.PEN_UP)
		{
		    removeTimer(mostrando);
		    removeTimer(mostrar);
		    estado = ESCONDIDO;
		    getParentWindow().repaint();
  		}
		else if(mostrando.triggered)
		{
		    removeTimer(mostrando);
		    estado = ESCONDIDO;
		    getParentWindow().repaint();
		}
	}
	
	/**
	 * M�todo que mostra um dica flutuante sobre um item da coluna do grid.
	 * @param int posX Posi��o horizontal inicial.
	 * @param int posY Posi��o vertival inicial.
	 * @param String hint Texto que ser� mostrado na dica.
	 */
	protected void drawHint(int posX, int posY, String hint)
	{
	    int width = 0;
	    int height = 0;

	    int tX = Settings.screenWidth/160;
	    int tY = Settings.screenHeight/160;

	    hint =  formatLines(hint, '|', Settings.screenWidth - 15, getFontMetrics(getFont()));

        textToWrite = Convert.tokenizeString(hint,'|');
	    if(textToWrite.length == 0)
	    {
	            textToWrite = new String[]{hint};
	    }
	    nbLine = textToWrite.length;

	    width = getMaxWidth() + 4 * tX;
	    height = fmH + tY;

	    Graphics g = this.createGraphics();
	    // verifica se o hint passa da largura da tela
	    if((posX + width + 2) > Settings.screenWidth - 10)
	    {
	        posX = Settings.screenWidth - width - 8 * tX;
	    }
	    // desenha interior do hint
	    g.setBackColor(_hintBackColor);
	    if(nbLine > 0)
	    {
	        height = height * (nbLine - 1);
	    }
	    // verifica se o hint passa da altura da tela
	    if((posY + height + 2 + 2 * fmH) > Settings.screenHeight - 10)
	    {
	        posY = Settings.screenHeight - height - 8 * tY - 3 * fmH;
	    }
	    g.fillRoundRect(posX, posY, width + 2 * tX, height + 1, 4 * tX);
	    // desenha borda do hint
	    g.setForeColor(Color.BLACK);
	    g.drawRoundRect(posX, posY, width + 2 * tX, height + 1, 4 * tX);
	    // desenha texto do hint
	    g.setForeColor(_hintForeColor);
	    for(int i = 0; i < nbLine; i++, posY += fmH)
	    {
	        g.drawText(textToWrite[i], posX + 3, posY + 1);
	    }
	}
	
	/**
	 * M�todo que formata um determinado texto, colocando os delimitadores no
	 * final do texto que alcan�a o tamanho estabelecido para uma linha.
	 * @param text Texto a ser formatado.
	 * @param markChar Caractere delimitador.
	 * @param lenLine Tamanho da linha.
	 * @param fMet Objeto m�trico da fonte.
	 * @return Texto formatado.
	 */
	public static String formatLines(String text, char markChar, int lenLine,
		FontMetrics fMet) {

		String[] words = Convert.tokenizeString( text, ' ' );
		StringBuffer newMsg = new StringBuffer( "" );
		StringBuffer line = new StringBuffer( "" );
		int length = words.length;
		int lenCurrent = 0;
		int lenWord = 0;

		for (int i = 0; i < length; i++) {
			//elimina os caracteres em branco desnecessarios.
			if ( words[i].trim().length() > 0 ) {
				//tamanho da palavra.
				lenWord = fMet.getTextWidth( words[i] );
				//tamanho ultrapassa o da linha.
				if ( ( lenCurrent + lenWord ) > lenLine ) {
					newMsg.append( line.toString() + markChar );
					line.setLength( 0 );
					lenCurrent = 0;
				}
				//mais 2, corresponde ao tamanho do espa�o.
				lenCurrent += lenWord + 2;
				line.append( words[i] + ' ' );
			}
		}

		if ( line.length() > 0 ) {
			newMsg.append( line.toString() + markChar + ' ' );
		}

		return newMsg.toString();
	}
	
	/**
	 * M�todo que retorna o tamanho m�ximo do hint com o texto passado.
	 * @return int
	 */
	private int getMaxWidth()
	{
	    int tmpMax = -1;
	    for(int i = nbLine; --i >= 0;)
	    {
	        int lengthCurrent = fm.getTextWidth(textToWrite[i]);
	        if (lengthCurrent > tmpMax)
	        {
	            tmpMax = lengthCurrent;
	        }
	    }
	    return tmpMax;
	}
}
