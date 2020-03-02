package escriptoriSaberApp.views;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class PanelBarraEsq extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelBarraEsq() {
		setLayout(new GridLayout(4, 1, 5, 5));
		
		JButton btnInici = new JButton("Inici");
		btnInici.setIcon(new ImageIcon(PanelBarraEsq.class.getResource("/escriptoriSaberApp/resources/001-home-page.png")));
		btnInici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnInici);
		
		JButton btnUsuaris = new JButton("Usuaris");
		btnUsuaris.setIcon(new ImageIcon(PanelBarraEsq.class.getResource("/escriptoriSaberApp/resources/002-multiple-users-silhouette.png")));
		add(btnUsuaris);
		
		JButton btnRanking = new JButton("Rànquing");
		btnRanking.setIcon(new ImageIcon(PanelBarraEsq.class.getResource("/escriptoriSaberApp/resources/004-rank.png")));
		add(btnRanking);
		
		JButton btnJoc = new JButton("Joc");
		btnJoc.setForeground(Color.BLACK);
		btnJoc.setBackground(Color.ORANGE);
		btnJoc.setIcon(new ImageIcon(PanelBarraEsq.class.getResource("/escriptoriSaberApp/resources/003-dice.png")));
		add(btnJoc);

	}

}
