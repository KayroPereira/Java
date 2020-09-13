package Controller;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class UsoGeral {

	public int GerarChave(int min, int max) {
		Random random = new Random();
		
		return  (int) ((max - min) * random.nextDouble()) + min;
	}
	
	public String ajusteDecimal (String valor) {
		valor = valor.replace(".", ",");
		
		int posicao = valor.lastIndexOf(",") < 0 ? -1 : valor.length()-1-valor.lastIndexOf(",");
		
		valor = valor.replaceAll(",", "");
		
		if (posicao > -1)
			valor = valor.substring(0, (valor.length()-posicao)) + "," + valor.substring((valor.length()-posicao));
		
		valor += posicao == -1 ? ",00" : posicao == 0 ? "00" : posicao == 1 ? "0" : "";

		return valor;		
	}
	
	enum DadosEntrega{
		NOME("Nome: "), 
		TELEFONE("Telefone: "), 
		ENDERECO("Endereço: "), 
		COMPLEMENTO("Complemento: ");
		
		private String value;
		
		private DadosEntrega(String value) {
			this.value = value;
		}
	}
	
	public String getDadosEntrega (String dados, int modo) {
		
		BinaryOperator<String> filterData = (data, filtro) -> 
		data.substring(data.indexOf(filtro)+filtro.length(), data.indexOf('\n', data.indexOf(filtro)) < 0 ? data.length() : data.indexOf('\n', data.indexOf(filtro)));
		
		UnaryOperator<String> filterName = data -> {
			String name[] = data.split(" ");
			return name.length > 1 ? (name[0] + " " + name[1]) : name[0]; 
		};
		
		switch (modo) {
			case 0:			
				return filterData.andThen(filterName).apply(dados, DadosEntrega.NOME.value);
				
			case 1:
				return filterData.apply(dados, DadosEntrega.NOME.value);
			
			case 2:
				return filterData.apply(dados, DadosEntrega.TELEFONE.value);
			
			case 3:
				return filterData.apply(dados, DadosEntrega.ENDERECO.value);
			
			case 4:
				return filterData.apply(dados, DadosEntrega.COMPLEMENTO.value);
			
			default:
				return DadosEntrega.NOME.value + filterData.apply(dados, DadosEntrega.NOME.value);
		}
	}
	
//	Nome: Michele Ximenes\nTelefone: 81986419723\nEndereço: Rua Lafaiete de aquino lopes, 110\ncoab - Moreno - Pernambuco\nComplemento: proximo ao mercadinho vitoria
//	Nome: \nTelefone: 0000000000\nEndereço: Não existem dados cadastrados \npara entrega \nComplemento: 	
}