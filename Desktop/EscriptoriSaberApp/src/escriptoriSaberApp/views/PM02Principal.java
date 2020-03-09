package escriptoriSaberApp.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.omega.server.consumer.client.impl.LoginRegistroImpl;
import com.omega.server.consumer.dto.UserDto;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import java.awt.Color;
import java.awt.Dimension;

/**
 * @author montse
 *
 */
public class PM02Principal extends JFrame {

	private JPanel contentPane;
	private PanelBarraEsq panelEsq;
	public PanelBarraTop panelTop;
	private PanelInici panelMain;
	protected String token;
	protected static String nick;
	protected String email;
	protected UserDto usuari;
	
	private LoginRegistroImpl lg; 
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PM02Principal frame = new PM02Principal();
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
	public PM02Principal() {
		initComponents();
		createEvents();
	}
	
	//////////////////////////////////////////////////////////////
	//Aquest mètode conté tot el codi per dissenyar elements
	//////////////////////////////////////////////////////////////
	private void initComponents() {
		setTitle("SaberApp");
		setSize(800,650);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 450));
		setBounds(20, 20, 600, 450);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		usuari = PM03Registre2.usuari;
		
		if (usuari !=null) {
			nick = usuari.getNickname();
			System.out.println(nick);
		} else {
			System.out.println("No s'ha trobat usuari");
			token = PM01Login2.token;
			System.out.println(token);
			
			email = PM01Login2.email;
			System.out.println(email);
			
			lg = new LoginRegistroImpl();
			usuari = lg.findUsuarioByEmail(email, token);
			nick = usuari.getNickname();
			System.out.println(nick);
		}
		
		panelTop = new PanelBarraTop();
		panelTop.setBackground(Utils.colorVerd);
		contentPane.add(panelTop, BorderLayout.NORTH);
		
		panelEsq = new PanelBarraEsq();
		panelEsq.setBackground(Utils.colorLilaFosc);
		contentPane.add(panelEsq, BorderLayout.WEST);
		
		panelMain = new PanelInici();
		contentPane.add(panelMain, BorderLayout.CENTER);
	}
	
	//////////////////////////////////////////////////////////////
	//Aquest mètode conté tot el codi per crear events
	//////////////////////////////////////////////////////////////
	private void createEvents() {
		
	}
	


}
