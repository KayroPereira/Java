package Model;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Controller.Design;
import View.Principal;

public class ComunicacaoBanco {

	private JLabel jLConexao;
	private JLabel jLConexaoTexto;
	
	private ConfiguracaoBanco dadosBanco;
	
	public ComunicacaoBanco (ConfiguracaoBanco dadosBanco) {
		this.dadosBanco = dadosBanco;
		this.jLConexao = dadosBanco.getjLConexao();
		this.jLConexaoTexto = dadosBanco.getjLConexaoTexto();
	}

	public void Driver(String driver) {
		try {
		    Class.forName(driver);
		    AtualizaStatusComunicacao(true);
		}  
		catch (ClassNotFoundException ex) {
		    System.err.print(ex.getMessage());
		    AtualizaStatusComunicacao(false);
		    JOptionPane.showMessageDialog(null, "Erro ao carregar o driver do banco");
		}
    }
   
	public Connection Conectar() {
		Connection conexao = dadosBanco.getConexao();
		try {
			Driver(dadosBanco.getDriver());
			conexao = (Connection) DriverManager.getConnection(dadosBanco.getUrl(), dadosBanco.getUser(), dadosBanco.getSenha());
			AtualizaStatusComunicacao(true);
		} 
		catch (SQLException e) {
			System.err.print(e.getMessage() + " - Erro: Conectar\n");
			//JOptionPane.showMessageDialog(null, "Erro na conexão com banco");
			AtualizaStatusComunicacao(false);
			//dadosBanco.setConexao(null);
			return null;
		}  
		return conexao;
	}

	public void Desconectar() {
		try {
			dadosBanco.getConexao().close();
			//AtualizaStatusComunicacao(true);
		}
		catch (SQLException e) {
			System.err.print(e.getMessage() + " - Erro: Desconectar\n");
			//JOptionPane.showMessageDialog(null, "Erro ao desconectar com banco");
			AtualizaStatusComunicacao(false);
		}
	}
	
	public JLabel getjLConexao() {
		return jLConexao;
	}

	public void setjLConexao(JLabel jLConexao) {
		this.jLConexao = jLConexao;
	}

	public JLabel getjLConexaoTexto() {
		return jLConexaoTexto;
	}

	public void setjLConexaoTexto(JLabel jLConexaoTexto) {
		this.jLConexaoTexto = jLConexaoTexto;
	}
	
	public void AtualizaStatusComunicacao (boolean status) {
		if (status) {
			jLConexao.setIcon(new ImageIcon(Design.PreparaImagem(Principal.class.getResource("/Imagens/conexão on.png"), 60, 36)));
			jLConexaoTexto.setText("OnLine");
			jLConexaoTexto.setForeground(Color.GREEN);
		}
		else {
			jLConexao.setIcon(new ImageIcon(Design.PreparaImagem(Principal.class.getResource("/Imagens/conexão off.png"), 60, 36)));
			jLConexaoTexto.setText("OffLine");
			jLConexaoTexto.setForeground(Color.RED);
		}
	}
}