package escriptoriSaberApp.proves;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;

import escriptoriSaberApp.views.PM01Login;

public class PM01LoginTest {
	
	private Robot robot;
	private AutoClick autoclick;
	private Runtime runtime;
	
	public void testejaLogin() {
		//TODO ajustar a la pantalla
		int xSet = 700;
		int ySet = 500;

		runtime = Runtime.getRuntime();
		
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("Fins aquí");
			runtime.exec("/Users/montse/eclipse-workspace/EscriptoriSaberApp/src/escriptoriSaberApp/views/PM01Login.java");
			System.out.println("Hauria d'haver fet el runtime");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.delay(10000);
		robot.mouseMove(xSet, ySet);//TODO ajustar a tfUser
		autoclick.leftClick(robot);
		autoclick.escriureTF(robot,"Mon");
		
		robot.delay(3000);
		robot.mouseMove(xSet + 50, ySet + 50);//TODO ajustar a pfPassword
		autoclick.leftClick(robot);		
		autoclick.escriureTF(robot,"123");
		
		robot.delay(3000);
		robot.mouseMove(xSet + 100, ySet + 0);//TODO ajustar a btnLogin
		autoclick.leftClick(robot);
		
	}

}
