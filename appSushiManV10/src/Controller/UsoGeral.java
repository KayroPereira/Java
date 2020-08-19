package Controller;

import java.util.Random;

import javax.swing.text.AbstractDocument.LeafElement;

public class UsoGeral {

	public int GerarChave(int min, int max) {
		Random random = new Random();
		
		return  (int) ((max - min) * random.nextDouble()) + min;
	}
	
	public String AjusteDecimal (String valor) {
		
		valor = valor.replace(".", ",");
		int cont = -1;
		
		for (int i = 0; i < valor.length(); i++)
			if (valor.charAt(i) == ',')
				cont++;
		
		for (int i = 0; i < cont; i++)
			valor = valor.replaceFirst(",", "");
		
		int posicao = valor.lastIndexOf(",");
		
		if (posicao > 0)
			posicao = valor.length() - posicao;

		switch (posicao) {
			case -1:
				valor+=",00";
			break;
			
			case 1:
				valor+="00";
			break;
			
			case 2:
				valor+="0";
			break;
		}

		return valor;
	}
	
	public String getDadosEntrega (String dados, int modo) {
		char [] informacao;
		String retorno = "";
		int i = 0;
		
		switch (modo) {
			case 0:
				informacao = dados.substring(dados.indexOf("Nome: ")+6, dados.length()).toCharArray();
				int quantidadeNomes = 0;
				while (informacao[i] != '\n' && quantidadeNomes < 2) {
					if (informacao[i] == ' ')
						quantidadeNomes++;
					
					if (quantidadeNomes < 2)
						retorno += informacao[i];
					
					i++;
				}
				
				/*
				if (retorno.length() > 0)
					return retorno.substring(0, retorno.length()-1);
				else
				*/
				return retorno;
				
			case 1:
				informacao = dados.substring(dados.indexOf("Nome: ")+6, dados.length()).toCharArray();
			break;
			
			case 2:
				informacao = dados.substring(dados.indexOf("Telefone: ")+10, dados.length()).toCharArray();
			break;
			
			case 3:
				informacao = dados.substring(dados.indexOf("Endereço: ")+10, dados.length()).toCharArray();
			break;
			
			case 4:
				informacao = dados.substring(dados.indexOf("Complemento: ")+13, dados.length()).toCharArray();
			break;
			
			default:
				informacao = dados.toCharArray();
		}

		while (informacao[i] != '\n') {
			retorno += informacao[i];
			i++;
		}
		
		return retorno;
	}
	
	/*
	 Nome: \nTelefone: 0000000000 \nEndereço: Não existem dados cadastrados 
	 */
}