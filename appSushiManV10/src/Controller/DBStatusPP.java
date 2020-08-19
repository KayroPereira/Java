package Controller;

import java.sql.Time;
import java.util.Date;

public class DBStatusPP {
	
	private Date stPPData;
	private Time stPPHora;
	private int ppId;
	private int statusPedido;
	private String descricaoStatusPedido;
	private int uId;
	private String uLogin;
	private long pdId;
	
	public DBStatusPP(Date stPPData, Time stPPHora, int ppId, int statusPedido, String descricaoStatusPedido, int uId,	String uLogin, long pdId) {
		this.stPPData = stPPData;
		this.stPPHora = stPPHora;
		this.ppId = ppId;
		this.statusPedido = statusPedido;
		this.descricaoStatusPedido = descricaoStatusPedido;
		this.uId = uId;
		this.uLogin = uLogin;
		this.pdId = pdId;
	}

	public Date getStPPData() {
		return stPPData;
	}

	public void setStPPData(Date stPPData) {
		this.stPPData = stPPData;
	}

	public Time getStPPHora() {
		return stPPHora;
	}

	public void setStPPHora(Time stPPHora) {
		this.stPPHora = stPPHora;
	}

	public int getPpId() {
		return ppId;
	}

	public void setPpId(int ppId) {
		this.ppId = ppId;
	}

	public int getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(int statusPedido) {
		this.statusPedido = statusPedido;
	}

	public String getDescricaoStatusPedido() {
		return descricaoStatusPedido;
	}

	public void setDescricaoStatusPedido(String descricaoStatusPedido) {
		this.descricaoStatusPedido = descricaoStatusPedido;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public String getuLogin() {
		return uLogin;
	}

	public void setuLogin(String uLogin) {
		this.uLogin = uLogin;
	}

	public long getPdId() {
		return pdId;
	}

	public void setPdId(long pdId) {
		this.pdId = pdId;
	}
}
