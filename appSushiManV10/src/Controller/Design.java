package Controller;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;


public class Design
{
	/**
	 * 
	 * Responsável por formatar a imagem para que caiba no seu local de destino - Dimesões da imagem
	 * 
	 * 
	 * @param arquivo = endereço da imagem - URL
	 * @param x = tamanho em x
	 * @param y = tamanho em y
	 * 
	 * @return  um objeto do tipo Image com as novas dimensões
	 */
		
    public static Image PreparaImagem(URL arquivo, int x, int y)
    {
    	ImageIcon img = new ImageIcon(arquivo);

        Image imagemformatada = img.getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT);
        return imagemformatada;
    }
}