package frc.robot.subsystems;

import frc.robot.Constants.LEDConstants;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDs extends SubsystemBase {
  private static LEDs m_instance;

  private DigitalOutput  mRedLED;
  private DigitalOutput  mGreenLED;
  private DigitalOutput  mBlueLED;
  
  private boolean RedOn,GreenOn,BlueOn;
  // Main sections
  public static LEDs getInstance() {
    if (m_instance == null) {
      m_instance = new LEDs();
    }
    return m_instance;
  }

  private LEDs() {
    super("LEDs");
    mRedLED   = new DigitalOutput(LEDConstants.RedIOPort);
    mGreenLED = new DigitalOutput(LEDConstants.GreenIOPort);
    mBlueLED  = new DigitalOutput(LEDConstants.BlueIOPort); 
}

  public void periodic() {
    mRedLED.set(RedOn);
    mGreenLED.set(GreenOn);
    mBlueLED.set(BlueOn);
  }

  public void defaultLEDS() {
    RedOn   = false;
    BlueOn  = false;
    GreenOn = false;    
  }

  public void setColorMode(boolean RED,boolean GREEN, boolean BLUE) {
    RedOn = RED;
    GreenOn = GREEN;
    BlueOn = BLUE;
  }

  public void goBlue(){
    setColorMode(false,false,true);
  }

  public void goRed(){
    setColorMode(true,false,false);
  }

  public void goGreen(){
    setColorMode(false,true,false);
  }

  public void goMagenta(){
    setColorMode(true,false,true);
  }

  public void goYellow(){
    setColorMode(true,true,false);
 }

 public void goCyan(){
    setColorMode(false,true,true);
 }

}
