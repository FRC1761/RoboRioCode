package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.Constants.AutoConstants;
/*
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.OIConstants;
/**/
public class RobotPreferences {

  /* Example of preferences from SmartDashboard*/
  public static double getAuto1stDelay() {
    double temp = Preferences.getDouble("auto1stDelay", AutoConstants.kFirstAutoDelayDefault);
    Preferences.setDouble("auto1stDelay", temp);
    return temp;
  }
    /* end example of preferences */


}