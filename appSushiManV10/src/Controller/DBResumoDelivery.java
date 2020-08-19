package Controller;

public class DBResumoDelivery {
	private int ppId;
	private boolean pyEntrega;
	private String pyEntregarPara;
	private String pyDadosEntrega;
	private String pyTelefone;
	
	public DBResumoDelivery(int ppId, boolean pyEntrega, String pyEntregarPara, String pyDadosEntrega, String pyTelefone) {
		this.ppId = ppId;
		this.pyEntrega = pyEntrega;
		this.pyEntregarPara = pyEntregarPara;
		this.pyDadosEntrega = pyDadosEntrega;
		this.pyTelefone = pyTelefone;
	}

	public int getPpId() {
		return ppId;
	}

	public void setPpId(int ppId) {
		this.ppId = ppId;
	}

	public boolean isPyEntrega() {
		return pyEntrega;
	}

	public void setPyEntrega(boolean pyEntrega) {
		this.pyEntrega = pyEntrega;
	}

	public String getPyEntregarPara() {
		return pyEntregarPara;
	}

	public void setPyEntregarPara(String pyEntregarPara) {
		this.pyEntregarPara = pyEntregarPara;
	}

	public String getPyDadosEntrega() {
		return pyDadosEntrega;
	}

	public void setPyDadosEntrega(String pyDadosEntrega) {
		this.pyDadosEntrega = pyDadosEntrega;
	}

	public String getPyTelefone() {
		return pyTelefone;
	}

	public void setPyTelefone(String pyTelefone) {
		this.pyTelefone = pyTelefone;
	}
}
