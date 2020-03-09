package escriptoriSaberApp.views;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelBarraTop extends JPanel {
	
	String nick = PM02Principal.nick;
	/**
	 * Create the panel.
	 */
	public PanelBarraTop() {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnMenu = new JButton("");
		btnMenu.setHorizontalAlignment(SwingConstants.RIGHT);
		btnMenu.setIcon(new ImageIcon(PanelBarraTop.class.getResource("/escriptoriSaberApp/resources/icon_menu_64.png")));
		add(btnMenu, BorderLayout.WEST);
		
		System.out.println("Aquest és el nick: "+ nick);
		
		JLabel lblSalutacio = new JLabel("Hola, " + nick);
		lblSalutacio.setIcon(new ImageIcon(PanelBarraTop.class.getResource("/escriptoriSaberApp/resources/icon_rounded_user_login.png")));
		lblSalutacio.setIconTextGap(15);
		lblSalutacio.setForeground(Color.BLUE);
		lblSalutacio.setHorizontalAlignment(SwingConstants.RIGHT);
		
		add(lblSalutacio);
		
		JButton btnExit = new JButton("");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnExit.setIcon(new ImageIcon(PanelBarraTop.class.getResource("/escriptoriSaberApp/resources/icon_exit2_64.png")));
		add(btnExit, BorderLayout.EAST);

	}

}
