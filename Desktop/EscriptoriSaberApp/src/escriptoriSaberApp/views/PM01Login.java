package escriptoriSaberApp.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Montse
*/

public class PM01Login extends JFrame {

	private String contrasenya;
	public String nick;
	private int intents = 0;
	
	private JPanel ctpLogin;
	private JTextField tfUser;
	private JPasswordField pfPassword;
	private JButton btnLogin;
	private JLabel lblRegister;
	private JLabel lblExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PM01Login frame = new PM01Login();
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
	public PM01Login() {
		
		initComponents();
		setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
		createEvents();
				
	}

	//////////////////////////////////////////////////////////////
	//Aquest mètode conté tot el codi per dissenyar elements
	//////////////////////////////////////////////////////////////
	private void initComponents() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		ctpLogin = new JPanel();
		ctpLogin.setBackground(Utils.colorLila);
		ctpLogin.setSize(500,300);
		ctpLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ctpLogin);
		ctpLogin.setLayout(null);
		
		JLabel lblOblit = new JLabel("Si no recordes usuari o contrasenya, posa't en contacte amb l'administrador.");
		lblOblit.setForeground(Utils.colorLilaFosc);
		lblOblit.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblOblit.setBounds(105, 180, 370, 16);
		ctpLogin.add(lblOblit);
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(155, 132, 180, 26);
		ctpLogin.add(pfPassword);
		
		JLabel lblPassword = new JLabel("Contrasenya");
		lblPassword.setBounds(165, 114, 85, 16);
		ctpLogin.add(lblPassword);
		
		tfUser = new JTextField();
		tfUser.setToolTipText("Nickname");
		tfUser.setBounds(155, 87, 180, 26);
		ctpLogin.add(tfUser);
		tfUser.setColumns(10);
		
		btnLogin = new JButton("Entra");
		btnLogin.setBackground(Utils.colorVerd);
		btnLogin.setForeground(Utils.colorVerdFosc);
		btnLogin.setOpaque(true);
		btnLogin.setBounds(382, 111, 93, 47);
		ctpLogin.add(btnLogin);
		
		JLabel lblIconaEsq = new JLabel("");
		lblIconaEsq.setBackground(Utils.colorLilaFosc);
		lblIconaEsq.setOpaque(true);
		lblIconaEsq.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconaEsq.setIcon(new ImageIcon(PM01Login.class.getResource("/escriptoriSaberApp/resources/icon_account_login_enter_door.png")));
		lblIconaEsq.setBounds(0, 0, 95, 285);
		ctpLogin.add(lblIconaEsq);
		
		JLabel lblUser = new JLabel("Usuari");
		lblUser.setBounds(165, 69, 61, 16);
		ctpLogin.add(lblUser);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PM01Login.class.getResource("/escriptoriSaberApp/resources/logo_SaberApp_60.png")));
		lblNewLabel.setBounds(355, 20, 61, 60);
		ctpLogin.add(lblNewLabel);
		
		lblExit = new JLabel("X");
		lblExit.setBounds(483, 6, 11, 16);
		ctpLogin.add(lblExit);
		
		lblRegister = new JLabel("Encara no ets usuari? Registra't!");
		lblRegister.setForeground(Utils.colorVerd);
		lblRegister.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblRegister.setBounds(165, 208, 280, 30);
		ctpLogin.add(lblRegister);
	}
	
	//////////////////////////////////////////////////////////////
	//Aquest mètode conté tot el codi per crear events
	//////////////////////////////////////////////////////////////
	private void createEvents() {
		btnLogin.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				int res =1;
		        

		        contrasenya = new String(pfPassword.getPassword());
				nick = tfUser.getText().trim();
				
				//TODO prova de connectar:
				
				//res = ges.login(nick, contrasenya);

				//System.out.println("La resposta del servidor és " + res);

				//if (res == 1) {
				if (contrasenya.equals("123") && nick.equals("mon")) {

				    dispose();
				    JOptionPane.showMessageDialog(null, "Benvingut " + nick + "!!\n"
				            + "Acabes d'entrar al sistema correctament", "Missatge de benvinguda",
				            JOptionPane.INFORMATION_MESSAGE);
				    intents = 4;
				    PM02Principal f2 = new PM02Principal();
				    f2.pack();
				    f2.setVisible(true);
				    f2.setLocationRelativeTo(null);
				    f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    f2.nick=nick;
				    //TODO REVISAR, em podria endur l'objecte professor a la següent pantalla o crido des d'allà qui està connectat?
				    dispose();

				} else if (intents == 3) {
				    JOptionPane.showMessageDialog(null, "Accés bloquejat\n Ha superat el màxim d'intents erronis!!");
				    System.exit(0);
				    
				} else if (res == 3) {
				    tfUser.setText("");
				    pfPassword.setText("");
				    intents++;
				    JOptionPane.showMessageDialog(null, "S'ha produit un error en la lectura de dades",
				            "Error", JOptionPane.ERROR_MESSAGE);

				} else {
				    tfUser.setText("");
				    pfPassword.setText("");
				    intents++;
				    JOptionPane.showMessageDialog(null, "Usuari o contrasenya incorrecta\n"
				            + "Intenta-ho de nou", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblRegister.setForeground(Utils.colorVerdFosc);
				lblRegister.setFont(new Font("Lucida Grande", Font.BOLD, 22));
				lblRegister.setBounds(120, 205, 370, 40);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblRegister.setForeground(Utils.colorVerd);
				lblRegister.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				lblRegister.setBounds(165, 205, 280, 30);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				PM03Registre f3 = new PM03Registre();
			    f3.pack();
			    f3.setVisible(true);
			    f3.setLocationRelativeTo(null);
			    f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				dispose();
			}
		});
				
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
	}
}
