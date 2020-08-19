package Controller;

public class DBStatusPedidos {
	private int idStPP;
	private String descricaoStPP;
	
	public DBStatusPedidos(int idStPP, String descricaoStPP) {
		this.idStPP = idStPP;
		this.descricaoStPP = descricaoStPP;
	}

	public int getIdStPP() {
		return idStPP;
	}

	public void setIdStPP(int idStPP) {
		this.idStPP = idStPP;
	}

	public String getDescricaoStPP() {
		return descricaoStPP;
	}

	public void setDescricaoStPP(String descricaoStPP) {
		this.descricaoStPP = descricaoStPP;
	}	
}
