package escriptoriSaberApp.views;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;

public class PanelInici extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelInici() {
		setBackground(Utils.colorLila);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {101, 75, 91, 103};
		gridBagLayout.rowHeights = new int[]{128, 50, 29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblLogoText = new JLabel("");
		lblLogoText.setIcon(new ImageIcon(PanelInici.class.getResource("/escriptoriSaberApp/resources/logo_Lletra_128.png")));
		GridBagConstraints gbc_lblLogoText = new GridBagConstraints();
		gbc_lblLogoText.anchor = GridBagConstraints.NORTH;
		gbc_lblLogoText.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogoText.gridwidth = 4;
		gbc_lblLogoText.gridx = 0;
		gbc_lblLogoText.gridy = 0;
		add(lblLogoText, gbc_lblLogoText);
		
		JLabel lblBenvinguts = new JLabel("Benvinguts!");
		lblBenvinguts.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		GridBagConstraints gbc_lblBenvinguts = new GridBagConstraints();
		gbc_lblBenvinguts.gridwidth = 2;
		gbc_lblBenvinguts.insets = new Insets(0, 0, 5, 5);
		gbc_lblBenvinguts.gridx = 1;
		gbc_lblBenvinguts.gridy = 1;
		add(lblBenvinguts, gbc_lblBenvinguts);
		
		JButton btnJoc = new JButton("Joc");
		GridBagConstraints gbc_btnJoc = new GridBagConstraints();
		gbc_btnJoc.anchor = GridBagConstraints.NORTH;
		gbc_btnJoc.insets = new Insets(0, 0, 0, 5);
		gbc_btnJoc.gridx = 0;
		gbc_btnJoc.gridy = 2;
		add(btnJoc, gbc_btnJoc);
		
		JButton btnUsuaris = new JButton("Usuaris");
		GridBagConstraints gbc_btnUsuaris = new GridBagConstraints();
		gbc_btnUsuaris.anchor = GridBagConstraints.NORTH;
		gbc_btnUsuaris.insets = new Insets(0, 0, 0, 5);
		gbc_btnUsuaris.gridx = 1;
		gbc_btnUsuaris.gridy = 2;
		add(btnUsuaris, gbc_btnUsuaris);
		
		JButton btnRanking = new JButton("Rànquing");
		GridBagConstraints gbc_btnRanking = new GridBagConstraints();
		gbc_btnRanking.insets = new Insets(0, 0, 0, 5);
		gbc_btnRanking.anchor = GridBagConstraints.NORTH;
		gbc_btnRanking.gridx = 2;
		gbc_btnRanking.gridy = 2;
		add(btnRanking, gbc_btnRanking);
		
		JButton btnSortir = new JButton("Sortir");
		GridBagConstraints gbc_btnSortir = new GridBagConstraints();
		gbc_btnSortir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSortir.anchor = GridBagConstraints.NORTH;
		gbc_btnSortir.gridx = 3;
		gbc_btnSortir.gridy = 2;
		add(btnSortir, gbc_btnSortir);

	}
}
