package Controller;

import java.sql.Time;
import java.util.Date;

public class ProdutoPedidoCompleto {
	private int ppId;
	private String ppObservacao;
	private int quantidadeId;
	private Date ppDate;
	private Time ppHora;
	private ItemPedido item;
	private long idPedido;
	private int chaveAcesso;
	private int idTipoPedido;
	private int idCliente;
	private int idContagemPedido;
	private int idStatusPedido;
	private String descricaoStatusPedido;
	private int idMesa;
	private String descricaoMesa;
	private int tempoEspera;
	private DBResumoDelivery dadosDelivery;
	private int prTempoEsperaMin;
	private int prTempoEsperaMax;
	
	public ProdutoPedidoCompleto(int ppId, String ppObservacao, int quantidadeId, Date ppDate, Time ppHora,
			ItemPedido item, long idPedido, int chaveAcesso, int idTipoPedido, int idCliente, int idContagemPedido,
			int idStatusPedido, String descricaoStatusPedido, int idMesa, String descricaoMesa, int tempoEspera,
			DBResumoDelivery dadosDelivery, int prTempoEsperaMin, int prTempoEsperaMax) {
		this.ppId = ppId;
		this.ppObservacao = ppObservacao;
		this.quantidadeId = quantidadeId;
		this.ppDate = ppDate;
		this.ppHora = ppHora;
		this.item = item;
		this.idPedido = idPedido;
		this.chaveAcesso = chaveAcesso;
		this.idTipoPedido = idTipoPedido;
		this.idCliente = idCliente;
		this.idContagemPedido = idContagemPedido;
		this.idStatusPedido = idStatusPedido;
		this.descricaoStatusPedido = descricaoStatusPedido;
		this.idMesa = idMesa;
		this.descricaoMesa = descricaoMesa;
		this.tempoEspera = tempoEspera;
		this.dadosDelivery = dadosDelivery;
		this.prTempoEsperaMin = prTempoEsperaMin;
		this.prTempoEsperaMax = prTempoEsperaMax;
	}

	public int getPpId() {
		return ppId;
	}

	public void setPpId(int ppId) {
		this.ppId = ppId;
	}

	public String getPpObservacao() {
		return ppObservacao;
	}

	public void setPpObservacao(String ppObservacao) {
		this.ppObservacao = ppObservacao;
	}

	public int getQuantidadeId() {
		return quantidadeId;
	}

	public void setQuantidadeId(int quantidadeId) {
		this.quantidadeId = quantidadeId;
	}

	public Date getPpDate() {
		return ppDate;
	}

	public void setPpDate(Date ppDate) {
		this.ppDate = ppDate;
	}

	public Time getPpHora() {
		return ppHora;
	}

	public void setPpHora(Time ppHora) {
		this.ppHora = ppHora;
	}

	public ItemPedido getItem() {
		return item;
	}

	public void setItem(ItemPedido item) {
		this.item = item;
	}

	public long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	public int getChaveAcesso() {
		return chaveAcesso;
	}

	public void setChaveAcesso(int chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}

	public int getIdTipoPedido() {
		return idTipoPedido;
	}

	public void setIdTipoPedido(int idTipoPedido) {
		this.idTipoPedido = idTipoPedido;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdContagemPedido() {
		return idContagemPedido;
	}

	public void setIdContagemPedido(int idContagemPedido) {
		this.idContagemPedido = idContagemPedido;
	}

	public int getIdStatusPedido() {
		return idStatusPedido;
	}

	public void setIdStatusPedido(int idStatusPedido) {
		this.idStatusPedido = idStatusPedido;
	}

	public String getDescricaoStatusPedido() {
		return descricaoStatusPedido;
	}

	public void setDescricaoStatusPedido(String descricaoStatusPedido) {
		this.descricaoStatusPedido = descricaoStatusPedido;
	}

	public int getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(int idMesa) {
		this.idMesa = idMesa;
	}

	public String getDescricaoMesa() {
		return descricaoMesa;
	}

	public void setDescricaoMesa(String descricaoMesa) {
		this.descricaoMesa = descricaoMesa;
	}

	public int getTempoEspera() {
		return tempoEspera;
	}

	public void setTempoEspera(int tempoEspera) {
		this.tempoEspera = tempoEspera;
	}

	public DBResumoDelivery getDadosDelivery() {
		return dadosDelivery;
	}

	public void setDadosDelivery(DBResumoDelivery dadosDelivery) {
		this.dadosDelivery = dadosDelivery;
	}

	public int getPrTempoEsperaMin() {
		return prTempoEsperaMin;
	}

	public void setPrTempoEsperaMin(int prTempoEsperaMin) {
		this.prTempoEsperaMin = prTempoEsperaMin;
	}

	public int getPrTempoEsperaMax() {
		return prTempoEsperaMax;
	}

	public void setPrTempoEsperaMax(int prTempoEsperaMax) {
		this.prTempoEsperaMax = prTempoEsperaMax;
	}	
}
