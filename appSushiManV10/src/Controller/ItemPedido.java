package Controller;

public class ItemPedido {
	private int idProduto;
	private String nomeProduto;
	private String observacao;
	private int quantidade;
	private String porcao;
	private float preco;
	private int idCardapio;
	
	public ItemPedido(int idProduto, String nomeProduto, String observacao, int quantidade, String porcao, float preco, int idCardapio) {
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
		this.observacao = observacao;
		this.quantidade = quantidade;
		this.porcao = porcao;
		this.preco = preco;
		this.idCardapio = idCardapio;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getPorcao() {
		return porcao;
	}

	public void setPorcao(String porcao) {
		this.porcao = porcao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getIdCardapio() {
		return idCardapio;
	}

	public void setIdCardapio(int idCardapio) {
		this.idCardapio = idCardapio;
	}	
}
