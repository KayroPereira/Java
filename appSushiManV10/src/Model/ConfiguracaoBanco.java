package Model;

import java.sql.Connection;

import javax.swing.JLabel;

public class ConfiguracaoBanco {

	private String driver;  
    private String user;  
    private String senha;  
    private String url;
    private Connection conexao;
    
    private JLabel jLConexao;
	private JLabel jLConexaoTexto;
    
	public ConfiguracaoBanco(String driver, String user, String senha, String url, Connection conexao) {
		this.driver = driver;
		this.user = user;
		this.senha = senha;
		this.url = url;
		this.conexao = conexao;
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
}
