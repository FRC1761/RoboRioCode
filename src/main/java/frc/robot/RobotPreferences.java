package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
/*
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.OIConstants;
/**/
public class RobotPreferences {

  public static double getAuto1stDelay() {
    double temp = Preferences.getDouble("auto1stDelay", AutoConstants.kFirstAutoDelayDefault);
    Preferences.setDouble("auto1stDelay", temp);
    return temp;
  }

  public static double getAuto2ndDelay() {
    double temp = Preferences.getDouble("auto2ndDelay",AutoConstants.kSecondAutoDelayDefault);
    Preferences.setDouble("auto2ndDelay", temp);
    return temp;
  }

  public static double getAuto3rdDelay() {
    double temp = Preferences.getDouble("auto3rdDelay", AutoConstants.kThirdAutoDelayDefault);
    Preferences.setDouble("auto3rdDelay", temp);
    return temp;
  }
 
  public static double getShooterOutput() {
    double temp = Preferences.getDouble("shooterOutput", ShooterConstants.defaultOutput);
    Preferences.setDouble("shooterOutput", temp);
    return temp;
  }

}