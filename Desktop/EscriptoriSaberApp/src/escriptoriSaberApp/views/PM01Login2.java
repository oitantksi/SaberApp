package escriptoriSaberApp.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.omega.server.consumer.client.impl.LoginRegistroImpl;


public class PM01Login2 extends JFrame {

	private String contrasenya;
	public static String email;
	private int intents = 1;
	public static String token;
	
	private JPanel ctpLogin;
	private JTextField tfMail;
	private JPasswordField pfPassword;
	private JButton btnLogin;
	private JLabel lblRegister;
	private JLabel lblExit;
	
	private LoginRegistroImpl lg; 

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
					PM01Login2 frame = new PM01Login2();
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
	public PM01Login2() {
		
		initComponents();		
		createEvents();
				
	}

	//////////////////////////////////////////////////////////////
	//Aquest mètode conté tot el codi per dissenyar elements
	//////////////////////////////////////////////////////////////
	private void initComponents() {
		setTitle("Login");
		setSize(500,300);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500,300));
		setBounds(100, 100, 500, 300);
		setLocationRelativeTo(null);
        
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
		
		tfMail = new JTextField();
		tfMail.setToolTipText("Email");
		tfMail.setBounds(155, 87, 180, 26);
		ctpLogin.add(tfMail);
		tfMail.setColumns(10);
		
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
		lblIconaEsq.setIcon(new ImageIcon(PM01Login2.class.getResource("/escriptoriSaberApp/resources/icon_account_login_enter_door.png")));
		lblIconaEsq.setBounds(0, 0, 95, 285);
		ctpLogin.add(lblIconaEsq);
		
		JLabel lblMail = new JLabel("Correu electrònic");
		lblMail.setBounds(165, 69, 108, 16);
		ctpLogin.add(lblMail);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(PM01Login2.class.getResource("/escriptoriSaberApp/resources/logo_SaberApp_60.png")));
		lblLogo.setBounds(355, 20, 61, 60);
		ctpLogin.add(lblLogo);
		
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
								
				lg = new LoginRegistroImpl();
				
				contrasenya = new String(pfPassword.getPassword());
				email = tfMail.getText().trim();
				if (contrasenya.length()!=0 && email.length()!=0) {	
					try{
												
						token = lg.login(email, contrasenya);
						String nick= (lg.findUsuarioByEmail(email, token)).getNickname();
						
						if (token!=null) {
	
						    dispose();
						    JOptionPane.showMessageDialog(null, "Benvingut, " + nick +"!!\n"
						            + "Acabes d'entrar al sistema correctament", "Missatge de benvinguda",
						            JOptionPane.INFORMATION_MESSAGE);
						    intents = 4;
						    PM02Principal f2 = new PM02Principal();
						    f2.pack();
						    f2.setVisible(true);
						    f2.setLocationRelativeTo(null);
						    f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						    //informacio necessaria a la següent pantalla 
						    f2.token=token;
						    f2.email=email;
						    f2.panelTop.nick = nick;
						    
						    dispose();
	
						}
						
						
					} catch (Exception e1) {
					      if (intents == 3) {								
							    JOptionPane.showMessageDialog(null, "Accés bloquejat.\n Ha superat el màxim d'intents erronis!!");
							    System.exit(0);
							    
					      } else {							    
							    intents++;
							    JOptionPane.showMessageDialog(null, "Email o contrasenya incorrecta.\n"
							            + "Intenta-ho de nou", "Error", JOptionPane.ERROR_MESSAGE);
					      }
					      
				    } finally {
				    	tfMail.setText("");
					    pfPassword.setText("");
				    }
		        
				
				} else {
					JOptionPane.showMessageDialog(null, "Els camps han d'estar plens\n"
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
				PM03Registre2 f3 = new PM03Registre2();
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
