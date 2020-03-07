package escriptoriSaberApp.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.omega.server.consumer.client.impl.LoginRegistroImpl;
import com.omega.server.consumer.dto.UserDto;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PM03Registre2 extends JFrame {


	private String email, contrasenya, nick, nom, cognoms, institut, materia, imatge;
	private JPanel ctpRegistre;
	private JTextField tfMail;
	private JTextField tfNom;
	private JTextField tfCognoms;
	private JTextField tfNick;
	private JTextField tfImatge;
	private JPasswordField pfContrasenya;
	public static UserDto usuari;
	private LoginRegistroImpl lg;
	private JLabel lblExit_1;
	private JButton btnTornar_1;
	private JButton btnRegistrar_1;
	private JComboBox cbMateria_1;
	private JComboBox cbInstitut_1;
	protected UserDto datosUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PM03Registre2 frame = new PM03Registre2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PM03Registre2() {
		initComponents();
		setSize(750, 625);
        //setResizable(false);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(750, 625));
		createEvents();
	}
	
	//////////////////////////////////////////////////////////////
	//Aquest mètode conté tot el codi per dissenyar elements
	//////////////////////////////////////////////////////////////
	private void initComponents() {
		setTitle("Registre");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 750, 600);
		ctpRegistre = new JPanel();
		ctpRegistre.setBorder(new EmptyBorder(5, 5, 5, 5));
		ctpRegistre.setBackground(Utils.colorLila);
		ctpRegistre.setSize(750,625);
		setContentPane(ctpRegistre);
		ctpRegistre.setLayout(null);
		
		JLabel lblIcona = new JLabel("");
		lblIcona.setIcon(new ImageIcon(PM03Registre2.class.getResource("/escriptoriSaberApp/resources/icon_rounded_user_login.png")));
		lblIcona.setBounds(43, 35, 64, 64);
		ctpRegistre.add(lblIcona);
		
		JLabel lblMail = new JLabel("Correu electrònic");
		lblMail.setBounds(144, 103, 116, 16);
		ctpRegistre.add(lblMail);
		
		tfMail = new JTextField();
		tfMail.setToolTipText("elteu@correu.com");
		tfMail.setBounds(144, 119, 248, 26);
		ctpRegistre.add(tfMail);
		tfMail.setColumns(10);
		
		JLabel lblContrasenya = new JLabel("Contrasenya");
		lblContrasenya.setBounds(144, 149, 92, 16);
		ctpRegistre.add(lblContrasenya);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(144, 280, 61, 16);
		ctpRegistre.add(lblNom);
		
		tfNom = new JTextField();
		tfNom.setBounds(144, 296, 211, 26);
		ctpRegistre.add(tfNom);
		tfNom.setColumns(10);
		
		JLabel lblCognoms = new JLabel("Cognoms");
		lblCognoms.setBounds(144, 330, 61, 16);
		ctpRegistre.add(lblCognoms);
		
		tfCognoms = new JTextField();
		tfCognoms.setBounds(144, 346, 211, 26);
		ctpRegistre.add(tfCognoms);
		tfCognoms.setColumns(10);
		
		JLabel lblNick = new JLabel("Nick");
		lblNick.setBounds(144, 230, 61, 16);
		ctpRegistre.add(lblNick);
		
		tfNick = new JTextField();
		tfNick.setBounds(144, 246, 130, 26);
		ctpRegistre.add(tfNick);
		tfNick.setColumns(10);
		
		JLabel lblInstitut = new JLabel("Centre educatiu");
		lblInstitut.setBounds(412, 230, 116, 16);
		ctpRegistre.add(lblInstitut);
		
		cbInstitut_1 = new JComboBox();
		cbInstitut_1.setModel(new DefaultComboBoxModel(new String[] {"", "IOC", "IES JOAN MARAGALL", "IES JAUME BALMES"}));
		cbInstitut_1.setBounds(412, 246, 211, 27);
		ctpRegistre.add(cbInstitut_1);
		
		JLabel lblMateria = new JLabel("Matèria");
		lblMateria.setBounds(412, 280, 61, 16);
		ctpRegistre.add(lblMateria);
		
		cbMateria_1 = new JComboBox();
		cbMateria_1.setModel(new DefaultComboBoxModel(new String[] {"", "Geografia", "Història", "Art", "Matemàtiques"}));
		cbMateria_1.setBounds(412, 296, 211, 27);
		ctpRegistre.add(cbMateria_1);
		
		JLabel lblImatge = new JLabel("Imatge");
		lblImatge.setBounds(412, 330, 61, 16);
		ctpRegistre.add(lblImatge);
		
		tfImatge = new JTextField();
		tfImatge.setBounds(412, 346, 211, 26);
		ctpRegistre.add(tfImatge);
		tfImatge.setColumns(10);
		
		pfContrasenya = new JPasswordField();
		pfContrasenya.setBounds(144, 165, 248, 26);
		ctpRegistre.add(pfContrasenya);
		
		lblExit_1 = new JLabel("X");
		lblExit_1.setBounds(720, 16, 13, 16);
		ctpRegistre.add(lblExit_1);
		
		btnRegistrar_1 = new JButton("REGISTRAR");
		btnRegistrar_1.setBounds(302, 387, 117, 41);
		ctpRegistre.add(btnRegistrar_1);
		
		JLabel lblInfoObligatori = new JLabel("* Aquests camps són obligatòris");
		lblInfoObligatori.setBounds(255, 440, 211, 16);
		ctpRegistre.add(lblInfoObligatori);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(PM03Registre2.class.getResource("/escriptoriSaberApp/resources/logo_Lletra_72.png")));
		lblLogo.setBounds(247, 489, 226, 72);
		ctpRegistre.add(lblLogo);
		
		btnTornar_1 = new JButton("<- TORNAR");
		btnTornar_1.setBounds(606, 553, 117, 29);
		ctpRegistre.add(btnTornar_1);
	}
	
	
	//////////////////////////////////////////////////////////////
	//Aquest mètode conté tot el codi per crear events
	//////////////////////////////////////////////////////////////
	private void createEvents() {
		lblExit_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		btnTornar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PM01Login2 f1 = new PM01Login2();
			    f1.pack();
			    f1.setVisible(true);
			    f1.setLocationRelativeTo(null);
			    f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dispose();
			}
		});
		
		cbInstitut_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				institut = cbInstitut_1.getSelectedItem().toString();
			}
		});
		
		cbMateria_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				materia = cbMateria_1.getSelectedItem().toString();
			}
		});
		
		btnRegistrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lg = new LoginRegistroImpl();
				
				contrasenya = new String(pfContrasenya.getPassword());
				email = tfMail.getText().trim();
				nick = tfNick.getText().trim();
				nom = tfNom.getText().trim();
				cognoms = tfCognoms.getText().trim();				
				imatge = tfImatge.getText().trim();
				
				
				System.out.println("UserDto [name=" + nom +", cognom=" + cognoms + ", email=" + email + ", nickname=" + nick+
						", password=" + contrasenya + ", center=" + institut+  ", matèria=" + materia+" ,rol=P]");
				datosUsuario = new UserDto(null,nom,cognoms,email,nick,contrasenya,institut,'P');
				usuari = new UserDto();
				if (contrasenya.length()!=0 && email.length()!=0) {	
					try {
						usuari = lg.registraUsuario(datosUsuario);
						if (usuari!=null) {
							
						    dispose();
						    JOptionPane.showMessageDialog(null, "Benvingut, " + nick +"!!\n"
						            + "Acabes d'entrar al sistema correctament", "Missatge de benvinguda",
						            JOptionPane.INFORMATION_MESSAGE);
						    
						    PM02Principal f2 = new PM02Principal();
						    f2.pack();
						    f2.setVisible(true);
						    f2.setLocationRelativeTo(null);
						    f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						    //informacio necessaria a la següent pantalla 
						    f2.usuari=usuari;
						    
						    dispose();
						}
					} catch (Exception e1){
						JOptionPane.showMessageDialog(null, "Error entrada de dades.\n"
					            + "Revisa els camps", "Error", JOptionPane.ERROR_MESSAGE);
					} finally {
				    	tfMail.setText("");
					    pfContrasenya.setText("");
				    }
				
				} else {
					JOptionPane.showMessageDialog(null, "Els camps han d'estar plens\n"
				            + "Intenta-ho de nou", "Error", JOptionPane.ERROR_MESSAGE);
				}
								
			}
		});
	}
}
