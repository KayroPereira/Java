package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout.Constraints;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Controller.DBStatusPP;
import Controller.DBStatusPedidos;
import Controller.DataHora;
import Controller.Design;
import Controller.PedidoMesa;
import Controller.ProdutoPedidoCompleto;
import Controller.Som;
import Controller.TempoDataHora;
import Controller.UsoGeral;
import Model.ConfiguracaoBanco;
import Model.FuncoesBanco;

public class Principal extends JFrame {
	
	private JPanel contentPane;
	private JTable jTPedidos;
	
	private JLabel jLConexao;
	private JLabel jLConexaoTexto;
	private JLabel jlRelogio;
	
	private JMenuItem mIDetalhe;
	private JMenuItem mIAtendimento;
	private JMenuItem mIEspera;
	private JMenuItem mIEntregue;
	private JMenuItem mIDescricao;
	
	private JButton jBSair;

	private String ipMachine = "";
	
	/*
	private ConfiguracaoBanco dadosBanco = new ConfiguracaoBanco("org.postgresql.Driver","postgres","admin", 
			//"jdbc:postgresql://192.168.1.252:5432/DBUmaiV18", null);
			//"jdbc:postgresql://localhost:5432/DBUmaiV18+", null);
			//"jdbc:postgresql://25.74.86.173:5432/DBUmaiV18+", null);
			//"jdbc:postgresql://localhost:5432/DBUmaiV20", null);
			//"jdbc:postgresql://192.168.0.18:5432/DBUmaiV20", null);
			//"jdbc:postgresql://192.168.1.252:5432/DBUmaiV18", null);
			//"jdbc:postgresql://localhost:5432/DBUmaiV20", null);
			//"jdbc:postgresql://192.168.0.252:5432/DBUmaiV18", null);
			//"jdbc:postgresql://localhost:5432/DBUmaiV18", null);
			"jdbc:postgresql://"+ ipMachine +":5432/DBUmaiV18", null);
			*/
	
	private ConfiguracaoBanco dadosBanco;

	private ArrayList<ProdutoPedidoCompleto> listaPedidos = new ArrayList<>();
	private ArrayList<DBStatusPedidos> listaStatus =  new ArrayList<DBStatusPedidos>();
	private ArrayList<PedidoMesa> pedidoIdBkp = new ArrayList<>();

	private Timer atualizaTabela;
	
	private static final DateFormat formato = new SimpleDateFormat("HH:mm");
	
	private int linhaTabela = 0;

	private String nivelAcesso;
	//private String perfil = "SushiMan";
	private String perfil = "Cozinha";
	
	private boolean atualizacao = true;

	public ConfiguracaoBanco getDadosBanco() {
		return dadosBanco;
	}

	public void setDadosBanco(ConfiguracaoBanco dadosBanco) {
		this.dadosBanco = dadosBanco;
	}

	public static Object[][] jTTitulo = new Object[][] {
		new String[] {"Espera"},
		new String[] {"Mesa"},
		new String[] {"Descri\u00E7\u00E3o"},
		new String[] {"Observa\u00E7\u00F5es"},
		new String[] {"Por\u00E7\u00F5es"},
		new String[] {"Status"}
	};
	
	private UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();

	public Principal() {
		setUndecorated(true);
		setBackground(Color.ORANGE);
		try  {
			UIManager.setLookAndFeel(looks[3].getClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ipMachine = Files.readAllLines(new File("workconfig.txt").toPath()).get(0);
			dadosBanco = new ConfiguracaoBanco("org.postgresql.Driver","postgres","admin", 
						//"jdbc:postgresql://192.168.1.252:5432/DBUmaiV18", null);
						//"jdbc:postgresql://localhost:5432/DBUmaiV18+", null);
						//"jdbc:postgresql://25.74.86.173:5432/DBUmaiV18+", null);
						//"jdbc:postgresql://localhost:5432/DBUmaiV20", null);
						//"jdbc:postgresql://192.168.0.18:5432/DBUmaiV20", null);
						//"jdbc:postgresql://192.168.1.252:5432/DBUmaiV18", null);
						//"jdbc:postgresql://localhost:5432/DBUmaiV20", null);
						//"jdbc:postgresql://192.168.0.252:5432/DBUmaiV18", null);
						//"jdbc:postgresql://localhost:5432/DBUmaiV18", null);
						"jdbc:postgresql://"+ ipMachine +":5432/DBUmaiV18", null);
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao ler arquivo de configuração", "Erro", MessageType.ERROR.ordinal());
			System.exit(1);
		}
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 300);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel jPTabela = new JPanel();
		jPTabela.setOpaque(false);
		jPTabela.setBackground(Color.ORANGE);
		
		//final Border bkgrnd = new CentredBackgroundBorder("/Imagens/imagem.jpg");
		final Border bkgrnd = new CentredBackgroundBorder("/Imagens/logo umai 600.png");
		
		JScrollPane sPTabela = new JScrollPane();
		sPTabela.getViewport().setOpaque(false);
		sPTabela.setBackground(Color.ORANGE);
		
		JPanel jPMenu = new JPanel();
		jPMenu.setBackground(Color.ORANGE);
		
		jLConexao = new JLabel("");
		jLConexaoTexto = new JLabel("OffLine");
		jLConexaoTexto.setForeground(Color.RED);
		jLConexaoTexto.setFont(new Font("Tahoma", Font.BOLD, 22));
		
		jLConexao.setIcon(new ImageIcon(Design.PreparaImagem(Principal.class.getResource("/Imagens/conexão off.png"), 60, 36)));
		
		jBSair = new JButton("");
		jBSair.setToolTipText("Fechar");
		jBSair.setFocusTraversalPolicyProvider(true);
		jBSair.setContentAreaFilled(false);
		jBSair.setFocusPainted(false);
		jBSair.setBorder(null);
		jBSair.setBorderPainted(false);
		jBSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//dispose();
				System.exit(0);
			}
		});
		
		jBSair.setIcon(new ImageIcon(Design.PreparaImagem(Principal.class.getResource("/Imagens/sair.png"), 30, 30)));
		
		JButton jBRefresh = new JButton("");
		jBRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AtualizaTabela(true);
			}
		});
		jBRefresh.setToolTipText("");
		jBRefresh.setFocusTraversalPolicyProvider(true);
		jBRefresh.setFocusPainted(false);
		jBRefresh.setContentAreaFilled(false);
		jBRefresh.setBorderPainted(false);
		jBRefresh.setBorder(null);
		
		jBRefresh.setIcon(new ImageIcon(Design.PreparaImagem(Principal.class.getResource("/Imagens/refresh 30x30.png"), 30, 30)));
		
		JLabel jLOperacao = new JLabel("OffLine");
		jLOperacao.setHorizontalAlignment(SwingConstants.CENTER);
		jLOperacao.setForeground(Color.BLACK);
		jLOperacao.setFont(new Font("Viner Hand ITC", Font.BOLD, 30));
		jLOperacao.setText(perfil);
		
		jlRelogio = new JLabel("00:00");
		jlRelogio.setForeground(Color.BLUE);
		jlRelogio.setFont(new Font("Dialog", Font.BOLD, 24));
		
		GroupLayout gl_jPMenu = new GroupLayout(jPMenu);
		gl_jPMenu.setHorizontalGroup(
			gl_jPMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jPMenu.createSequentialGroup()
					.addGap(10)
					.addComponent(jLConexao, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(jLConexaoTexto, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jBRefresh, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jLOperacao, GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(jlRelogio)
					.addGap(18)
					.addComponent(jBSair, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(18))
		);
		gl_jPMenu.setVerticalGroup(
			gl_jPMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jPMenu.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_jPMenu.createParallelGroup(Alignment.LEADING)
						.addComponent(jBSair)
						.addComponent(jLConexaoTexto, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(jBRefresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLConexao, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_jPMenu.createParallelGroup(Alignment.BASELINE)
							.addComponent(jlRelogio, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(jLOperacao, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		jPMenu.setLayout(gl_jPMenu);
		
		dadosBanco.setjLConexao(jLConexao);
		dadosBanco.setjLConexaoTexto(jLConexaoTexto);
		
		GroupLayout gl_jPTabela = new GroupLayout(jPTabela);
		gl_jPTabela.setHorizontalGroup(
			gl_jPTabela.createParallelGroup(Alignment.LEADING)
				.addComponent(jPMenu, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1240, Short.MAX_VALUE)
				.addComponent(sPTabela, GroupLayout.DEFAULT_SIZE, 1240, Short.MAX_VALUE)
		);
		gl_jPTabela.setVerticalGroup(
			gl_jPTabela.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jPTabela.createSequentialGroup()
					.addComponent(jPMenu, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sPTabela, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
		);
		

		Runnable r = new Runnable() {
            public void run() {
            	sPTabela.setViewportBorder(bkgrnd);
            	sPTabela.repaint();
            }
	   };
	   SwingUtilities.invokeLater(r);
		
		jTPedidos = new JTable();
		jTPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTPedidos.setOpaque(false);
		jTPedidos.setBackground(Color.ORANGE);
		//jTPedidos.setFont(UIManager.getFont("Table.font"));
		//jTPedidos.setFont(new Font("Viner Hand ITC", Font.BOLD, 24));
		jTPedidos.setFont(new Font("Tahoma", Font.PLAIN, 24));
		jTPedidos.setRowHeight(50);
		jTPedidos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Espera", "Mesa", "Descri\u00E7\u00E3o", "Observa\u00E7\u00F5es", "Por\u00E7\u00F5es", "Status"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		jTPedidos.getColumnModel().getColumn(0).setResizable(false);
		jTPedidos.getColumnModel().getColumn(0).setPreferredWidth(100);
		jTPedidos.getColumnModel().getColumn(0).setMinWidth(100);
		jTPedidos.getColumnModel().getColumn(0).setMaxWidth(100);
		jTPedidos.getColumnModel().getColumn(1).setResizable(false);
		jTPedidos.getColumnModel().getColumn(1).setPreferredWidth(120);
		jTPedidos.getColumnModel().getColumn(1).setMinWidth(120);
		jTPedidos.getColumnModel().getColumn(1).setMaxWidth(120);
		//jTPedidos.getColumnModel().getColumn(2).setResizable(false);
		jTPedidos.getColumnModel().getColumn(2).setPreferredWidth(300);
		jTPedidos.getColumnModel().getColumn(2).setMinWidth(300);
		jTPedidos.getColumnModel().getColumn(2).setMaxWidth(3000);
		//jTPedidos.getColumnModel().getColumn(3).setResizable(false);
		jTPedidos.getColumnModel().getColumn(3).setPreferredWidth(300);
		jTPedidos.getColumnModel().getColumn(3).setMinWidth(300);
		jTPedidos.getColumnModel().getColumn(3).setMaxWidth(3000);
		//jTPedidos.getColumnModel().getColumn(4).setResizable(false);
		jTPedidos.getColumnModel().getColumn(4).setPreferredWidth(90);
		jTPedidos.getColumnModel().getColumn(4).setMinWidth(90);
		jTPedidos.getColumnModel().getColumn(4).setMaxWidth(90);
		jTPedidos.getColumnModel().getColumn(5).setResizable(false);
		jTPedidos.getColumnModel().getColumn(5).setPreferredWidth(130);
		jTPedidos.getColumnModel().getColumn(5).setMinWidth(130);
		jTPedidos.getColumnModel().getColumn(5).setMaxWidth(130);
		
		MultiLineHeaderRenderer headerRenderer = new MultiLineHeaderRenderer(SwingConstants.CENTER, SwingConstants.BOTTOM);
		headerRenderer.setBackground(Color.ORANGE);
		headerRenderer.setForeground(Color.BLACK);
		headerRenderer.setFont(new Font("Viner Hand ITC", Font.BOLD, 20));
		
		TableColumnModel tabelaModel = jTPedidos.getColumnModel();
		
		int columns = jTTitulo.length;
		for (int i = 0; i < columns; i++) {
			tabelaModel.getColumn(i).setHeaderRenderer(headerRenderer);
			tabelaModel.getColumn(i).setHeaderValue(jTTitulo[i]);			
		}
		
		//jTPedidos.getTableHeader().setReorderingAllowed(false);

		jTPedidos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {  
                Component celula = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Object valor = table.getValueAt(row, 4);

                int espera = listaPedidos.get(row).getTempoEspera() > 0 ? listaPedidos.get(row).getTempoEspera() 
            				: 86400+listaPedidos.get(row).getTempoEspera();
                
                celula.setForeground(Color.BLACK);
                if (isSelected)
    				setBackground(new Color(82, 169, 235));
                else
                	if(table.getValueAt(row, 5).toString().equals("Entregue"))
    					setBackground(Color.GREEN);
                	else
		                if (valor != null && Integer.parseInt(valor.toString()) > 1 && column > 1) {
		                	celula.setBackground(Color.BLUE);
		                	celula.setForeground(Color.YELLOW);
		                }
		                else {
		                	if (espera >= listaPedidos.get(row).getPrTempoEsperaMax()*60) {
		                		celula.setBackground(Color.RED);
		                		celula.setForeground(Color.YELLOW);
		                	}
		                	else
		                		if (espera >= listaPedidos.get(row).getPrTempoEsperaMin()*60)
		                			celula.setBackground(Color.YELLOW);
		                		else
		                			celula.setBackground(Color.WHITE);
		                }                
                setHorizontalAlignment(JLabel.CENTER);

                return this;
            }
		});
		
		jTPedidos.setPreferredScrollableViewportSize(jTPedidos.getPreferredSize());
		
		sPTabela.setViewportView(jTPedidos);
		
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				//atualizacao = true;
				//AtualizaTabela(true);
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			}
		});
		popupMenu.setBackground(Color.ORANGE);
		popupMenu.setOpaque(isBackgroundSet());
		addPopup(jTPedidos, popupMenu);
		
		mIAtendimento = new JMenuItem("Em atendimento");
		mIAtendimento.setVisible(false);
		
		mIDescricao = new JMenuItem("Descri\u00E7\u00E3o");
		mIDescricao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Detalhe detalhe = new Detalhe(listaPedidos.get(linhaTabela).getItem().getObservacao() + ".\n Porção: " 
						+ listaPedidos.get(linhaTabela).getItem().getPorcao());
						detalhe.setVisible(true);
						linhaTabela = -1;
					}
				}).start();
			}
		});
		mIDescricao.setOpaque(true);
		mIDescricao.setFont(new Font("Viner Hand ITC", Font.PLAIN, 30));
		mIDescricao.setBackground(Color.ORANGE);
		popupMenu.add(mIDescricao);
		
		mIDetalhe = new JMenuItem("Detalhe");
		mIDetalhe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Detalhe detalhe = new Detalhe(listaPedidos.get(linhaTabela).getPpObservacao());
						detalhe.setVisible(true);
						linhaTabela = -1;
					}
				}).start();
			}
		});
		mIDetalhe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mIDetalhe.setOpaque(true);
		mIDetalhe.setBackground(Color.ORANGE);
		mIDetalhe.setFont(new Font("Viner Hand ITC", Font.PLAIN, 30));
		popupMenu.add(mIDetalhe);
		
		mIEspera = new JMenuItem("Em Espera");
		mIEspera.setOpaque(true);
		mIEspera.setFont(new Font("Viner Hand ITC", Font.PLAIN, 30));
		mIEspera.setBackground(Color.ORANGE);
		popupMenu.add(mIEspera);
		
		mIAtendimento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mIAtendimento.setBackground(Color.ORANGE);
		mIAtendimento.setOpaque(true);
		mIAtendimento.setFont(new Font("Viner Hand ITC", Font.PLAIN, 30));
		popupMenu.add(mIAtendimento);
		
		mIEntregue = new JMenuItem("Entregue");
		mIEntregue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AtualizaStatus(3);
			}
		});
		mIEntregue.setOpaque(true);
		mIEntregue.setFont(new Font("Viner Hand ITC", Font.PLAIN, 30));
		mIEntregue.setBackground(Color.ORANGE);
		popupMenu.add(mIEntregue);
		
		mIAtendimento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AtualizaStatus(1);
			}
		});
		
		mIEspera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AtualizaStatus(0);
			}
		});
		
		jPTabela.setLayout(gl_jPTabela);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(jPTabela, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(jPTabela, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);

		nivelAcesso = setNivelAcesso(perfil);

		atualizaTabela = new Timer(20000, new ActionListener(){
		public void actionPerformed(ActionEvent e){
			AtualizaTabela(true);
		}});
		
		atualizaTabela.start();
		
		try {
			listaStatus = new FuncoesBanco().getStatusPedido(dadosBanco, new DBStatusPedidos(-1, ""));
		} catch (Exception e) {
			listaStatus.clear();
		}
		
		jlRelogio.setText(formato.format(new Date()));
		
		AtualizaTabela(true);

		Timer tRelogio = new Timer(3000, new ClockAction());               
		tRelogio.setInitialDelay(0);
		tRelogio.setRepeats(true);
		tRelogio.start();
	}

	private class ClockAction implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            jlRelogio.setText(formato.format(new Date()));
        }
    }
	
	public String setNivelAcesso(String descricao) {
		ArrayList<String> filtro = new ArrayList<>();
		String retorno;
		
		switch (descricao) {
			case "SushiMan":
				filtro.add("Sushi");
				filtro.add("Sushi-Cozinha");
				filtro.add("Sobremesa");
			break;
			
			case "Cozinha":
				filtro.add("Bebida");
				filtro.add("Cozinha");
				filtro.add("Sushi-Cozinha");
				filtro.add("Sobremesa-Cozinha");
			break;
		}
		
		retorno = "(cf.cfdescricao = '" + filtro.get(0) + "' ";
		for (int i = 1; i < filtro.size(); i++)
			retorno += "or cf.cfdescricao = '" + filtro.get(i) + "' ";

		return retorno+")";
	}

	class MultiLineHeaderRenderer extends JPanel implements TableCellRenderer {
		protected int verticalAlignment;
		protected int horizontalAlignment;
		protected float alignmentX;
		protected Color foreground;
		protected Color background;
		
		protected Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");
		protected Font font = UIManager.getFont("TableHeader.font");

		public MultiLineHeaderRenderer(int horizontalAlignment, int verticalAlignment) {
		    setBorder(headerBorder);
		    setOpaque(true);
		
		    background = null;
		}
		
		public void setForeground(Color foreground) {
			this.foreground = foreground;
			super.setForeground(foreground);
		}

		public void setBackground(Color background) {
			this.background = background;
			super.setBackground(background);
		}

		public void setFont(Font font) {
			this.font = font;
		}
	  
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			removeAll();
			invalidate();

			if (value == null)
				return this;


			if (table != null) {
				JTableHeader header = table.getTableHeader();
				if (header != null) {
					if (foreground == null)
						super.setForeground(header.getForeground());

					if (background == null)
						super.setBackground(header.getBackground());
				}
			}
			
			Object[] values;
			int length;
			if (value instanceof Object[])
				values = (Object[]) value;
			else {
				values = new Object[1];
				values[0] = value;
			}
			length = values.length;

			for (int i = 0; i < length; i++) {
				Object thisRow = values[i];
    			if (thisRow instanceof JComponent)
    				add((JComponent) thisRow);
    			else {
			        JLabel label = new JLabel();
			        setValue(label, thisRow, i);
			        add(label);
    			}
			}
	    
			if (verticalAlignment != SwingConstants.BOTTOM)
				add(Box.createVerticalGlue());
			
			return this;
		}
		
		protected void setValue(JLabel l, Object value, int lineNumber) {
			if (value != null && value instanceof Icon)
				l.setIcon((Icon) value);
			else
				l.setText(value == null ? "" : value.toString());
			
			l.setHorizontalAlignment(horizontalAlignment);
			l.setAlignmentX(alignmentX);
			l.setOpaque(false);
			l.setForeground(foreground);
			l.setFont(font);
		}
	}

	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1)
					showMenu(e);
			}

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == 1)
					showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				int col =  jTPedidos.columnAtPoint(e.getPoint());  
                int row = jTPedidos.rowAtPoint(e.getPoint());
                
                if (col != -1 && row != -1) {
                	jTPedidos.setRowSelectionInterval(row, row);
                	linhaTabela = jTPedidos.getSelectedRow();
                }

                atualizacao = false;
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void AtualizaStatus (int idStatus) {
		Date data = new DataHora().GetDataAtual();
		Time hora = new DataHora().GetHoraAtual();
		
		ArrayList<DBStatusPP> listaStatusPP = new ArrayList<>();
		
		if (linhaTabela > -1)
			listaStatusPP.add(new DBStatusPP(data, hora, listaPedidos.get(linhaTabela).getPpId(), 
			listaStatus.get(idStatus).getIdStPP(), "", 1, "CLIENTELOCALUMAI", listaPedidos.get(linhaTabela).getIdPedido()));

		if (listaStatusPP.size() > 0) {
			listaPedidos = new FuncoesBanco().setStatusProdutoPedido(dadosBanco, listaStatusPP, nivelAcesso);

			linhaTabela = -1;
			atualizacao = true;
			AtualizaTabela(false);
		}
	}
	
	public void AtualizaTabela(boolean consulta) {
		Date data = new DataHora().GetDataAtual();
		
		DefaultTableModel modeloProduto = (DefaultTableModel) jTPedidos.getModel();

		if (listaStatus.size() > 0) {
			if (atualizacao) {
				
				if (consulta)
					listaPedidos = new FuncoesBanco().getListaProdutoPedido(this.getDadosBanco(), nivelAcesso);
				
				ArrayList<PedidoMesa> pedidoId = new ArrayList<>();
				//ArrayList<Integer> mesaId = new ArrayList<>();
				ArrayList<Integer> mesaId = new ArrayList<>();
			
				for (; modeloProduto.getRowCount() > 0;)
					modeloProduto.removeRow(0);
				
				if (listaPedidos != null)
					for (ProdutoPedidoCompleto temp : listaPedidos) {
						Object[] linha = {new DataHora().formatarPeriodo(new TempoDataHora(data, temp.getTempoEspera())), 
						temp.getDadosDelivery() == null ? temp.getDescricaoMesa() :	!temp.getDadosDelivery().getPyEntregarPara().equals("") ? 
						temp.getDadosDelivery().getPyEntregarPara() : 
						new UsoGeral().getDadosEntrega(temp.getDadosDelivery().getPyDadosEntrega(), 0).equals("") ? temp.getDescricaoMesa() :
						new UsoGeral().getDadosEntrega(temp.getDadosDelivery().getPyDadosEntrega(), 0), temp.getItem().getIdProduto() + " - " + 
						temp.getItem().getNomeProduto(), temp.getPpObservacao(), temp.getQuantidadeId(), temp.getDescricaoStatusPedido()};
						modeloProduto.addRow(linha);
						
						if (!temp.getDescricaoStatusPedido().equals("Entregue"))
							pedidoId.add(new PedidoMesa(temp.getPpId(), temp.getIdMesa()));
					}
				
				for (PedidoMesa temp : pedidoId) {
					boolean achou = false;
					for (PedidoMesa temp1 : pedidoIdBkp)
						if (temp1.getPedidoId() == temp.getPedidoId()) {
							achou = true;
							break;
						}
					
					if (!achou)
						mesaId.add(temp.getMeasaId());
				}
				
				//remove itens duplicados
				//mesaId.addAll((ArrayList<Integer>) mesaId.stream().distinct().collect(Collectors.toList()));
				//mesaId.addAll((ArrayList<Integer>) mesaId.stream().distinct().collect(Collectors.toList()));
				//mesaId.clone();
				//mesaId = new ArrayList<Integer>(new LinkedHashSet<Integer>(mesaId));
				
				ArrayList<Integer> mesaIdTemp = (ArrayList<Integer>) mesaId.stream().distinct().collect(Collectors.toList());
				
				//mesaIdTemp = (ArrayList<Integer>) mesaId.stream().distinct().collect(Collectors.toList());
				mesaId.clear();
				mesaId.addAll(mesaIdTemp);
				
				/*
				for (int i = 0; i < mesaId.size(); i++)
					for (int j = i+1; j < mesaId.size(); j++)
						if (mesaId.get(i) == mesaId.get(j)) {
							mesaId.remove(j);
							j--;
						}
				*/
				
				pedidoIdBkp = pedidoId;		

				if (mesaId.size() > 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							new Som().playwav(new File("novoPedido.wav"));
					        long tempoInicio1 = System.currentTimeMillis();
					        
							while ((System.currentTimeMillis() - tempoInicio1) < 3000){}
							
							for (Integer temp : mesaId) {
							
								new Som().playwav(new File("mesa" + temp + ".wav"));
						        long tempoInicio = System.currentTimeMillis();
						        
								while ((System.currentTimeMillis() - tempoInicio) < 3000){}
							}
						}
					}).start();
				}
			}
		}
		else {
			try {
				listaStatus = new FuncoesBanco().getStatusPedido(dadosBanco, new DBStatusPedidos(-1, ""));
			} catch (Exception e) {
				listaStatus.clear();
			}
		}
		atualizaTabela.restart();
	}
	
	public class CentredBackgroundBorder implements Border {
	     private final ImageIcon image;
	     public CentredBackgroundBorder(String sImg) {
	         this.image = new ImageIcon(getClass().getResource(sImg));
	     }
	     public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	         int x0 = x + (width-image.getIconWidth())/2;
	         int y0 = y + (height-image.getIconHeight())/2;
	         g.drawImage(image.getImage(), x0, y0, null);
	     }
	     public Insets getBorderInsets(Component c) {
	         return new Insets(0,0,0,0);
	     }
	     public boolean isBorderOpaque() {
	         return true;
	     }
	}
}