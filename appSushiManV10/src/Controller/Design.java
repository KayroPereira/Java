package Controller;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;


public class Design
{
	/**
	 * 
	 * Respons�vel por formatar a imagem para que caiba no seu local de destino - Dimes�es da imagem
	 * 
	 * 
	 * @param arquivo = endere�o da imagem - URL
	 * @param x = tamanho em x
	 * @param y = tamanho em y
	 * 
	 * @return  um objeto do tipo Image com as novas dimens�es
	 */
		
    public static Image PreparaImagem(URL arquivo, int x, int y)
    {
    	ImageIcon img = new ImageIcon(arquivo);

        Image imagemformatada = img.getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT);
        return imagemformatada;
    }
}