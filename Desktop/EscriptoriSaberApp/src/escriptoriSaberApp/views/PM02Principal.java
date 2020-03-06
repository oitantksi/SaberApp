package escriptoriSaberApp.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import java.awt.Color;

/**
 * @author montse
 *
 */
public class PM02Principal extends JFrame {

	private JPanel contentPane;
	private PanelBarraEsq panelEsq;
	private PanelBarraTop panelTop;
	private PanelInici panelMain;
	protected String token;
	

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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 600, 450);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
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
