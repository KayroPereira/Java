package Controller;

public class DBStatusProduto {
	
	private int idPedido;
	private int idProduto;
	private int idStatusPedido;
	
	public DBStatusProduto(int idPedido, int idProduto, int idStatusPedido) {
		this.idPedido = idPedido;
		this.idProduto = idProduto;
		this.idStatusPedido = idStatusPedido;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public int getIdStatusPedido() {
		return idStatusPedido;
	}

	public void setIdStatusPedido(int idStatusPedido) {
		this.idStatusPedido = idStatusPedido;
	}
}
