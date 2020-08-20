package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Controller.DBResumoDelivery;
import Controller.DBStatusPP;
import Controller.DBStatusPedidos;
import Controller.ItemPedido;
import Controller.ProdutoPedidoCompleto;

public class FuncoesBanco {

	public ArrayList<ProdutoPedidoCompleto> getListaProdutoPedido(ConfiguracaoBanco dadosBanco, String filtro) {
		
		ArrayList<ProdutoPedidoCompleto> temp = new ArrayList<>();
		
		ComunicacaoBanco comunicacaoBanco = new ComunicacaoBanco(dadosBanco);

		try {
			dadosBanco.setConexao(comunicacaoBanco.Conectar());
			
			if (dadosBanco.getConexao() != null) {
				java.sql.Statement stmt = dadosBanco.getConexao().createStatement();
				temp = executeGetListaProdutoPedido(stmt, filtro);
				
				/*
				ResultSet retornoBanco;
				
				for (int i = 0; i < 2; i++) {
					String query = "";
					if (i == 0) {
						query = "select distinct pp.ppid, py.pyentrega, py.pyentregarpara, pydadosentrega, pytelefone from statuspp spp "
						+ "inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) filtroSPPid "
						+ "on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join pedido pd "
						+ "on pp.pdid = pd.pdid inner join pedidodelivery py on pd.pdid = py.pdid inner join chaveacesso ca on ca.pdid = pd.pdid "
						+ "inner join mesa ms on ms.msid = pd.msid inner join produto pr on pp.prid = pr.prid inner join statuspedido sp "
						+ "on sp.spId = spp.spId inner join classificacao cf on pr.cfid = cf.cfid where "
						+ "(ppdata = current_date or ppdata = current_date-1) and spp.spId < 4 and " 
						+ filtro + " order by pp.ppid;";
					}
					else {
						query = "select distinct pp.ppid, py.pyentrega, py.pyentregarpara, pydadosentrega, pytelefone from statuspp spp "
						+ "inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) filtroSPPid "
						+ "on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join pedido pd "
						+ "on pp.pdid = pd.pdid inner join pedidodelivery py on pd.pdid = py.pdid inner join chaveacesso ca on ca.pdid = pd.pdid "
						+ "inner join mesa ms on ms.msid = pd.msid inner join produto pr on pp.prid = pr.prid inner join statuspedido sp "
						+ "on sp.spId = spp.spId inner join cardapio cd on pr.cdid = cd.cdid inner join classificacao cf on pr.cfid = cf.cfid "
						+ "where spp.spId = 4 and ((((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
						+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
						+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time)) < 180 and spp.stppdata = current_date) or "
						+ "((86400+((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
						+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
						+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time))) < 180 and spp.stppdata =  current_date-1)) "
						+ "and cd.cdid <> (select cdid from cardapio where cddescricao = 'Taxas') and " 
						+ filtro + " order by pp.ppid;";
					}
	
					retornoBanco = stmt.executeQuery(query);
					
					ArrayList<DBResumoDelivery> dadosDelivery = new ArrayList<>();
				
					while (retornoBanco.next())
						dadosDelivery.add(new DBResumoDelivery(retornoBanco.getInt(1), retornoBanco.getBoolean(2), retornoBanco.getString(3), 
						retornoBanco.getString(4), retornoBanco.getString(5)));
				
					if (i == 0) {
						query = "select distinct pp.ppid, pp.ppobservacao, pp.ppquantidade, pp.ppdata, pp.pphora, pp.ppprporcao, "
						+ "pp.ppprpreco, pp.pdid, pp.tpid, pp.cid, pp.cpid, pp.prid, pr.prnome, pr.prdescricao, ca.caid, sp.*, ms.msid, ms.msnome, "
						+ "((DATE_PART ('hour', current_time :: time - pp.pphora :: time) * 60 "
						+ "+ DATE_PART ('minute', current_time :: time - pp.pphora :: time)) * 60 "
						+ "+ DATE_PART ('second', current_time :: time - pp.pphora :: time)) as espera, cdid, pr.prTempoEsperaMin, pr.prTempoEsperaMax "
						+ "from statuspp spp inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) "
						+ "filtroSPPid on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join "
						+ "pedido pd on pp.pdid = pd.pdid inner join chaveacesso ca on ca.pdid = pd.pdid inner join mesa ms on ms.msid = pd.msid "
						+ "inner join produto pr on pp.prid = pr.prid inner join statuspedido sp on sp.spId = spp.spId inner join classificacao cf "
						+ "on pr.cfid = cf.cfid where (ppdata = current_date or ppdata = current_date-1) and spp.spId < 4 "
						+ "and " + filtro + " order by pp.ppid;";
					} else {
						query = "select distinct pp.ppid, pp.ppobservacao, pp.ppquantidade, pp.ppdata, pp.pphora, pp.ppprporcao, pp.ppprpreco, "
						+ "pp.pdid, pp.tpid, pp.cid, pp.cpid, pp.prid, pr.prnome, pr.prdescricao, ca.caid, sp.*, ms.msid, ms.msnome, "
						+ "((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
						+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
						+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time)) as espera, cd.cdid, spp.stppid, "
						+ "pr.prTempoEsperaMin, pr.prTempoEsperaMax from statuspp spp "
						+ "inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) filtroSPPid "
						+ "on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join pedido pd "
						+ "on pp.pdid = pd.pdid inner join chaveacesso ca on ca.pdid = pd.pdid inner join mesa ms on ms.msid = pd.msid "
						+ "inner join produto pr on pp.prid = pr.prid inner join statuspedido sp on sp.spId = spp.spId "
						+ "inner join cardapio cd on pr.cdid = cd.cdid inner join classificacao cf on pr.cfid = cf.cfid where spp.spId = 4 "
						+ "and ((((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
						+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
						+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time)) < 180 and spp.stppdata = current_date) or "
						+ "((86400+((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
						+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
						+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time))) < 180 and spp.stppdata =  current_date-1)) "
						+ "and cd.cdid <> (select cdid from cardapio where cddescricao = 'Taxas') and " + filtro + " order by spp.stppid;";
					}
				
					retornoBanco = stmt.executeQuery(query);
	
					while (retornoBanco.next()) {
						DBResumoDelivery dadosTemp = null; 
						
						if (retornoBanco.getInt(18) == 15)
							for (DBResumoDelivery temp1 : dadosDelivery) {
								if (retornoBanco.getInt(1) == temp1.getPpId()) {
									dadosTemp = temp1;
									break;
								}
							}
						else
							dadosTemp = null;
								
						temp.add(new ProdutoPedidoCompleto(retornoBanco.getInt(1), retornoBanco.getString(2), retornoBanco.getInt(3), 
						retornoBanco.getDate(4), retornoBanco.getTime(5), new ItemPedido(retornoBanco.getInt(12), retornoBanco.getString(13), 
						retornoBanco.getString(14), -1, retornoBanco.getString(6), retornoBanco.getFloat(7), retornoBanco.getInt(21)), 
						retornoBanco.getLong(8), retornoBanco.getInt(15), retornoBanco.getInt(9), retornoBanco.getInt(10), retornoBanco.getInt(11), 
						retornoBanco.getInt(16), retornoBanco.getString(17), retornoBanco.getInt(18), retornoBanco.getString(19), 
						retornoBanco.getInt(20), dadosTemp, retornoBanco.getInt(22), retornoBanco.getInt(23)));
					}
				}
				*/
				comunicacaoBanco.Desconectar();
			}
			return temp;
		} 
		catch (SQLException e) {
			System.out.println("Erro de SQL getListaProdutoPedido: " + e);
			comunicacaoBanco.Desconectar();
			return null;
		}
	}

	public ArrayList<DBStatusPedidos> getStatusPedido(ConfiguracaoBanco dadosBanco, DBStatusPedidos modo) {
		ComunicacaoBanco comunicacaoBanco = new ComunicacaoBanco(dadosBanco);
		ArrayList<DBStatusPedidos> temp = new ArrayList<>();
		
		try {
			dadosBanco.setConexao(comunicacaoBanco.Conectar());
			
			if (dadosBanco.getConexao() != null) {
				java.sql.Statement stmt = dadosBanco.getConexao().createStatement();
				ResultSet retornoBanco;
	
				if (modo.getIdStPP() > 0)
					retornoBanco = stmt.executeQuery("select * from statuspedido where spId = " + modo.getIdStPP());
				else
					if (!modo.getDescricaoStPP().equals(""))
						retornoBanco = stmt.executeQuery("select * from statuspedido where spDescricao = '" + modo.getDescricaoStPP() + "'");
					else
						if (modo.getIdStPP() == -1)
							retornoBanco = stmt.executeQuery("select * from statuspedido where spId < 6;");
						else
							retornoBanco = stmt.executeQuery("select * from spId");
				
				
				
				while (retornoBanco.next())
					temp.add(new DBStatusPedidos(retornoBanco.getInt(1), retornoBanco.getString(2)));
				
				comunicacaoBanco.Desconectar();
			}
			return temp;
		}
		catch (SQLException e) {
			System.out.println("Erro de SQL getStatusPedido: " + e);
			
			comunicacaoBanco.Desconectar();
			return null;
		}
	}

	public ArrayList<ProdutoPedidoCompleto> setStatusProdutoPedido(ConfiguracaoBanco dadosBanco, ArrayList<DBStatusPP> statusPP, String filtro) {
		PreparedStatement informacaoBanco;
		
		ArrayList<ProdutoPedidoCompleto> retorno = new ArrayList<>();
		ComunicacaoBanco comunicacaoBanco = new ComunicacaoBanco(dadosBanco);
		
		try {
			dadosBanco.setConexao(comunicacaoBanco.Conectar());
			
			if (dadosBanco.getConexao() != null) {
				informacaoBanco = dadosBanco.getConexao().prepareStatement("insert into statuspp (stPPData, stPPHora, ppId, spId, "
				+ "uId, uLogin, pdid) values (?, ?, ?, ?, ?, ?, ?)");
				
				for (DBStatusPP temp : statusPP) {
					informacaoBanco.setDate(1, new java.sql.Date(temp.getStPPData().getTime()));
					informacaoBanco.setTime(2, temp.getStPPHora());
					informacaoBanco.setInt(3, temp.getPpId());
					informacaoBanco.setInt(4, temp.getStatusPedido());
					informacaoBanco.setInt(5, temp.getuId());
					informacaoBanco.setString(6, temp.getuLogin());
					informacaoBanco.setLong(7, temp.getPdId());
					informacaoBanco.execute();
				}
				
				java.sql.Statement stmt = dadosBanco.getConexao().createStatement();
				retorno = executeGetListaProdutoPedido(stmt, filtro);
				
				comunicacaoBanco.Desconectar();
			}
			return retorno;
		} 
		catch (SQLException e) {
			System.out.println("Erro de SQL setStatusProdutoPedido: " + e);
			comunicacaoBanco.Desconectar();
			return null;
		}
	}

	private ArrayList<ProdutoPedidoCompleto> executeGetListaProdutoPedido (java.sql.Statement stmt, String filtro){
		ArrayList<ProdutoPedidoCompleto> temp = new ArrayList<>();

		try {
			ResultSet retornoBanco;
			
			for (int i = 0; i < 2; i++) {
				String query = "";
				if (i == 0) {
					/*
					query = "select distinct pp.ppid, py.pyentrega, py.pyentregarpara, pydadosentrega, pytelefone from statuspp spp "
					+ "inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) filtroSPPid "
					+ "on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join pedido pd "
					+ "on pp.pdid = pd.pdid inner join pedidodelivery py on pd.pdid = py.pdid inner join chaveacesso ca on ca.pdid = pd.pdid "
					+ "inner join mesa ms on ms.msid = pd.msid inner join produto pr on pp.prid = pr.prid inner join statuspedido sp "
					+ "on sp.spId = spp.spId inner join classificacao cf on pr.cfid = cf.cfid where "
					+ "(ppdata = current_date or ppdata = current_date-1) and spp.spId < 4 and " 
					+ filtro + " order by pp.ppid;";
					*/
					query = 
					"select distinct pp.ppid, py.pyentrega, py.pyentregarpara, pydadosentrega, pytelefone from statuspp spp "
					+ 	"inner join "
					+ 		"(select statuspp.ppid, max(stppid) as stppidTemp from statuspp "
					+ 			"inner join produtopedido pp on statuspp.ppid = pp.ppid "
					+ 		"where (pp.ppdata = current_date or pp.ppdata = current_date-1) group by statuspp.ppid) filtroSPPid "
					+ 	"on spp.stppid = filtroSPPid.stppidTemp "
					+ 	"inner join produtopedido pp on filtroSPPid.ppid = pp.ppid "
					+ 	"inner join pedido pd on pp.pdid = pd.pdid "
					+ 	"inner join pedidodelivery py on pd.pdid = py.pdid "
					+ 	"inner join produto pr on pp.prid = pr.prid "
					+ 	"inner join classificacao cf on pr.cfid = cf.cfid "
					+ "where "
					+ 	"(spp.spId < 4 "
					+ 	"and "
					+ 	filtro + ") order by pp.ppid;";

				}
				else {
					/*
					query = "select distinct pp.ppid, py.pyentrega, py.pyentregarpara, pydadosentrega, pytelefone from statuspp spp "
					+ "inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) filtroSPPid "
					+ "on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join pedido pd "
					+ "on pp.pdid = pd.pdid inner join pedidodelivery py on pd.pdid = py.pdid inner join chaveacesso ca on ca.pdid = pd.pdid "
					+ "inner join mesa ms on ms.msid = pd.msid inner join produto pr on pp.prid = pr.prid inner join statuspedido sp "
					+ "on sp.spId = spp.spId inner join cardapio cd on pr.cdid = cd.cdid inner join classificacao cf on pr.cfid = cf.cfid "
					+ "where spp.spId = 4 and ((((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
					+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
					+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time)) < 180 and spp.stppdata = current_date) or "
					+ "((86400+((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
					+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
					+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time))) < 180 and spp.stppdata =  current_date-1)) "
					+ "and cd.cdid <> (select cdid from cardapio where cddescricao = 'Taxas') and " 
					+ filtro + " order by pp.ppid;";
					*/
					
					query = 
					"select distinct pp.ppid, py.pyentrega, py.pyentregarpara, pydadosentrega, pytelefone from "
					+	"(select filtroSPPid.ppid, filtroSPPid.stppidtemp, spp.spid, spp.stpphora, spp.stppdata from statuspp spp "
					+ 		"inner join "
					+ 			"(select spp1.ppid, max(spp1.stppid) as stppidtemp from statuspp spp1 "
					+ 				"inner join produtopedido pp on spp1.ppid = pp.ppid "
					+ 			"where (pp.ppdata = current_date or pp.ppdata = current_date-1) group by spp1.ppid) filtroSPPid "
					+ 		"on spp.stppid = filtroSPPid.stppidTemp "
					+ 	"where spp.stppid = filtroSPPid.stppidtemp) filterspp "
					+ 	"inner join produtopedido pp on filterspp.ppid = pp.ppid "
					+ 	"inner join pedido pd on pp.pdid = pd.pdid "
					+ 	"inner join pedidodelivery py on pd.pdid = py.pdid "
					+ 	"inner join produto pr on pp.prid = pr.prid " 
					//--inner join statuspedido sp on sp.spId = spp.spId 
					+ 	"inner join cardapio cd on pr.cdid = cd.cdid "
					+ 	"inner join classificacao cf on pr.cfid = cf.cfid "
					+"where "
					+ 	"filterspp.spId = 4 "
					+ 	"and "
					+ 		"(((((DATE_PART ('hour', current_time :: time - filterspp.stpphora :: time) * 60 + DATE_PART ('minute', current_time :: time - filterspp.stpphora :: time)) * 60 + "
					+ 		"DATE_PART ('second', current_time :: time - filterspp.stpphora :: time)) < 180)	and filterspp.stppdata = current_date) "
					+		"or "
					+ 		"((86400+((DATE_PART ('hour', current_time :: time - filterspp.stpphora :: time) * 60 + DATE_PART ('minute', current_time :: time - filterspp.stpphora :: time)) * 60 + "
					+ 		"DATE_PART ('second', current_time :: time - filterspp.stpphora :: time))) < 180 and filterspp.stppdata =  current_date-1)) "
					+ 	"and cd.cdid <> (select cdid from cardapio where cddescricao = 'Taxas') "
					+ 	"and " + filtro + " order by pp.ppid;";
				}

				retornoBanco = stmt.executeQuery(query);
				
				ArrayList<DBResumoDelivery> dadosDelivery = new ArrayList<>();
			
				while (retornoBanco.next())
					dadosDelivery.add(new DBResumoDelivery(retornoBanco.getInt(1), retornoBanco.getBoolean(2), retornoBanco.getString(3), 
					retornoBanco.getString(4), retornoBanco.getString(5)));
			
				if (i == 0) {
					/*
					query = "select distinct pp.ppid, pp.ppobservacao, pp.ppquantidade, pp.ppdata, pp.pphora, pp.ppprporcao, "
					+ "pp.ppprpreco, pp.pdid, pp.tpid, pp.cid, pp.cpid, pp.prid, pr.prnome, pr.prdescricao, ca.caid, sp.*, ms.msid, ms.msnome, "
					+ "((DATE_PART ('hour', current_time :: time - pp.pphora :: time) * 60 "
					+ "+ DATE_PART ('minute', current_time :: time - pp.pphora :: time)) * 60 "
					+ "+ DATE_PART ('second', current_time :: time - pp.pphora :: time)) as espera, cdid, pr.prTempoEsperaMin, pr.prTempoEsperaMax "
					+ "from statuspp spp inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) "
					+ "filtroSPPid on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join "
					+ "pedido pd on pp.pdid = pd.pdid inner join chaveacesso ca on ca.pdid = pd.pdid inner join mesa ms on ms.msid = pd.msid "
					+ "inner join produto pr on pp.prid = pr.prid inner join statuspedido sp on sp.spId = spp.spId inner join classificacao cf "
					+ "on pr.cfid = cf.cfid where (ppdata = current_date or ppdata = current_date-1) and spp.spId < 4 "
					+ "and " + filtro + " order by pp.ppid;";
					*/
					query =
					"select distinct pp.ppid, pp.ppobservacao, pp.ppquantidade, pp.ppdata, pp.pphora, pp.ppprporcao, pp.ppprpreco, pp.pdid, pp.tpid, pp.cid, pp.cpid, pp.prid, pr.prnome, pr.prdescricao, "
					+ "ca.caid, sp.*, ms.msid, ms.msnome, ((DATE_PART ('hour', current_time :: time - pp.pphora :: time) * 60 + DATE_PART ('minute', current_time :: time - pp.pphora :: time)) * 60 + "
					+ "DATE_PART ('second', current_time :: time - pp.pphora :: time)) as espera, cdid, pr.prTempoEsperaMin, pr.prTempoEsperaMax from statuspp spp "
					+ 		"inner join "
					+ 			"(select statuspp.ppid, max(stppid) as stppidTemp from statuspp "
					+ 				"inner join produtopedido pp on statuspp.ppid = pp.ppid "
					+ 			"where (pp.ppdata = current_date or pp.ppdata = current_date-1) group by statuspp.ppid) filtroSPPid "
					+ 		"on spp.stppid = filtroSPPid.stppidTemp "
					+ 		"inner join produtopedido pp on filtroSPPid.ppid = pp.ppid " 
					+ 		"inner join pedido pd on pp.pdid = pd.pdid "
					+ 		"inner join chaveacesso ca on ca.pdid = pd.pdid "
					+ 		"inner join mesa ms on ms.msid = pd.msid "
					+ 		"inner join produto pr on pp.prid = pr.prid "
					+ 		"inner join statuspedido sp on sp.spId = spp.spId "
					+ 		"inner join classificacao cf on pr.cfid = cf.cfid "
					+ "where "
					+ 		"spp.spId < 4 "
					+ 		"and " + filtro + " order by pp.ppid;";
				} else {
					/*
					query = "select distinct pp.ppid, pp.ppobservacao, pp.ppquantidade, pp.ppdata, pp.pphora, pp.ppprporcao, pp.ppprpreco, "
					+ "pp.pdid, pp.tpid, pp.cid, pp.cpid, pp.prid, pr.prnome, pr.prdescricao, ca.caid, sp.*, ms.msid, ms.msnome, "
					+ "((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
					+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
					+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time)) as espera, cd.cdid, spp.stppid, "
					+ "pr.prTempoEsperaMin, pr.prTempoEsperaMax from statuspp spp "
					+ "inner join (select statuspp.ppid, max(stppid) as stppidTemp from statuspp group by ppid) filtroSPPid "
					+ "on spp.stppid = filtroSPPid.stppidTemp inner join produtopedido pp on filtroSPPid.ppid = pp.ppid inner join pedido pd "
					+ "on pp.pdid = pd.pdid inner join chaveacesso ca on ca.pdid = pd.pdid inner join mesa ms on ms.msid = pd.msid "
					+ "inner join produto pr on pp.prid = pr.prid inner join statuspedido sp on sp.spId = spp.spId "
					+ "inner join cardapio cd on pr.cdid = cd.cdid inner join classificacao cf on pr.cfid = cf.cfid where spp.spId = 4 "
					+ "and ((((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
					+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
					+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time)) < 180 and spp.stppdata = current_date) or "
					+ "((86400+((DATE_PART ('hour', current_time :: time - spp.stpphora :: time) * 60 "
					+ "+ DATE_PART ('minute', current_time :: time - spp.stpphora :: time)) * 60 "
					+ "+ DATE_PART ('second', current_time :: time - spp.stpphora :: time))) < 180 and spp.stppdata =  current_date-1)) "
					+ "and cd.cdid <> (select cdid from cardapio where cddescricao = 'Taxas') and " + filtro + " order by spp.stppid;";
					*/
					query = 
					"select distinct pp.ppid, pp.ppobservacao, pp.ppquantidade, pp.ppdata, pp.pphora, pp.ppprporcao, pp.ppprpreco, pp.pdid, pp.tpid, pp.cid, pp.cpid, pp.prid, pr.prnome, pr.prdescricao, "
					+ "ca.caid, sp.*, ms.msid, ms.msnome, ((DATE_PART ('hour', current_time :: time - filterspp.stpphora :: time) * 60 + DATE_PART ('minute', current_time :: time - filterspp.stpphora :: time)) * 60 + "
					+ "DATE_PART ('second', current_time :: time - filterspp.stpphora :: time)) as espera, cd.cdid, filterspp.stppidTemp, pr.prTempoEsperaMin, pr.prTempoEsperaMax from "
					+ 	"(select filtroSPPid.ppid, filtroSPPid.stppidtemp, spp.spid, spp.stpphora, spp.stppdata from statuspp spp "
					+ 		"inner join "
					+ 			"(select spp1.ppid, max(spp1.stppid) as stppidtemp from statuspp spp1 "
					+ 				"inner join produtopedido pp on spp1.ppid = pp.ppid "
					+ 			"where (pp.ppdata = current_date or pp.ppdata = current_date-1) group by spp1.ppid) filtroSPPid "
					+ 		"on spp.stppid = filtroSPPid.stppidTemp "
					+ 	"where spp.stppid = filtroSPPid.stppidtemp) filterspp "
					+ 	"inner join produtopedido pp on filterspp.ppid = pp.ppid "
					+	"inner join pedido pd on pp.pdid = pd.pdid "
					+	"inner join chaveacesso ca on ca.pdid = pd.pdid "
					+	"inner join mesa ms on ms.msid = pd.msid "
					+	"inner join produto pr on pp.prid = pr.prid "
					+	"inner join statuspedido sp on sp.spId = filterspp.spId "
					+	"inner join cardapio cd on pr.cdid = cd.cdid "
					+	"inner join classificacao cf on pr.cfid = cf.cfid "
					+"where "
					+	"filterspp.spId = 4 " 
					+	"and "
					+		"(((((DATE_PART ('hour', current_time :: time - filterspp.stpphora :: time) * 60 + DATE_PART ('minute', current_time :: time - filterspp.stpphora :: time)) * 60 + "
					+		"DATE_PART ('second', current_time :: time - filterspp.stpphora :: time)) < 180)	and filterspp.stppdata = current_date) "
					+	"or "
					+		"((86400+((DATE_PART ('hour', current_time :: time - filterspp.stpphora :: time) * 60 + DATE_PART ('minute', current_time :: time - filterspp.stpphora :: time)) * 60 + "
					+		"DATE_PART ('second', current_time :: time - filterspp.stpphora :: time))) < 180 and filterspp.stppdata =  current_date-1)) "
					+	"and cd.cdid <> (select cdid from cardapio where cddescricao = 'Taxas') "
					+	"and " + filtro + " order by pp.ppid; ";
				}
			
				retornoBanco = stmt.executeQuery(query);

				while (retornoBanco.next()) {
					DBResumoDelivery dadosTemp = null; 
					
					if (retornoBanco.getInt(18) == 15)
						for (DBResumoDelivery temp1 : dadosDelivery) {
							if (retornoBanco.getInt(1) == temp1.getPpId()) {
								dadosTemp = temp1;
								break;
							}
						}
					else
						dadosTemp = null;
							
					temp.add(new ProdutoPedidoCompleto(retornoBanco.getInt(1), retornoBanco.getString(2), retornoBanco.getInt(3), 
					retornoBanco.getDate(4), retornoBanco.getTime(5), new ItemPedido(retornoBanco.getInt(12), retornoBanco.getString(13), 
					retornoBanco.getString(14), -1, retornoBanco.getString(6), retornoBanco.getFloat(7), retornoBanco.getInt(21)), 
					retornoBanco.getLong(8), retornoBanco.getInt(15), retornoBanco.getInt(9), retornoBanco.getInt(10), retornoBanco.getInt(11), 
					retornoBanco.getInt(16), retornoBanco.getString(17), retornoBanco.getInt(18), retornoBanco.getString(19), 
					retornoBanco.getInt(20), dadosTemp, retornoBanco.getInt(22), retornoBanco.getInt(23)));
				}
			}
			
			return temp;
		} 
		catch (SQLException e) {
			System.out.println("Erro de SQL executeGetListaProdutoPedido: " + e);
			return null;
		}
	}
}
