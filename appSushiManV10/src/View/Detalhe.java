package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import Controller.Design;

public class Detalhe extends JDialog {

	private final JPanel jPPrincipal = new JPanel();
	private JButton jBFechar;
	private JTextArea jTADetalhe;

	public Detalhe(String detalhe) {
		setUndecorated(true);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 761, 348);
		getContentPane().setLayout(new BorderLayout());
		jPPrincipal.setBackground(Color.ORANGE);
		jPPrincipal.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.BLACK, null));
		getContentPane().add(jPPrincipal, BorderLayout.CENTER);
		
		setLocationRelativeTo(this);
		
		jBFechar = new JButton("");
		jBFechar.setFocusTraversalPolicyProvider(true);
		jBFechar.setContentAreaFilled(false);
		jBFechar.setFocusPainted(false);
		jBFechar.setBorder(null);
		jBFechar.setBorderPainted(false);
		jBFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JScrollPane sPDetalhe = new JScrollPane();
		sPDetalhe.setBorder(null);
		
		GroupLayout gl_jPPrincipal = new GroupLayout(jPPrincipal);
		gl_jPPrincipal.setHorizontalGroup(
			gl_jPPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_jPPrincipal.createSequentialGroup()
					.addContainerGap(350, Short.MAX_VALUE)
					.addComponent(jBFechar, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(308))
				.addComponent(sPDetalhe, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
		);
		gl_jPPrincipal.setVerticalGroup(
			gl_jPPrincipal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_jPPrincipal.createSequentialGroup()
					.addComponent(sPDetalhe, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jBFechar, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		jTADetalhe = new JTextArea();
		jTADetalhe.setWrapStyleWord(true);
		jTADetalhe.setBorder(null);
		sPDetalhe.setViewportView(jTADetalhe);
		jTADetalhe.setEditable(false);
		jTADetalhe.setLineWrap(true);
		jTADetalhe.setFont(new Font("Tahoma", Font.PLAIN, 40));
		jTADetalhe.setBackground(Color.ORANGE);
		jPPrincipal.setLayout(gl_jPPrincipal);
		
		jBFechar.setIcon(new ImageIcon(Design.PreparaImagem(Principal.class.getResource("/Imagens/sair.png"), 70, 70)));
		jTADetalhe.setText(detalhe);
	}
}