package Controller;

public class PedidoMesa {
	private int pedidoId;
	private int measaId;
	
	public PedidoMesa(int pedidoId, int measaId) {
		this.pedidoId = pedidoId;
		this.measaId = measaId;
	}

	public int getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}

	public int getMeasaId() {
		return measaId;
	}

	public void setMeasaId(int measaId) {
		this.measaId = measaId;
	};
}
