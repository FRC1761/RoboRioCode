package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
/*
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.IntakeConstants;
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

  public static double getEjectPower() {
    double temp = Preferences.getDouble("ejectPower", AutoConstants.kEjectPowerDefault);
    Preferences.setDouble("ejectPower", temp);
    return temp;
  }

  public static double getShooterP(){
    double temp = Preferences.getDouble("ShooterP", ShooterConstants.kShooterP);
    Preferences.setDouble("ShooterP", temp);
    return temp;
  }

  public static double getShooterI(){
    double temp = Preferences.getDouble("ShooterI", ShooterConstants.kShooterI);
    Preferences.setDouble("ShooterI", temp);
    return temp;
  }

  public static double getShooterD(){
    double temp = Preferences.getDouble("ShooterD", ShooterConstants.kShooterD);
    Preferences.setDouble("ShooterD", temp);
    return temp;
  }

  public static double getShooterFF(){
    double temp = Preferences.getDouble("ShooterFF", ShooterConstants.kShooterFF);
    Preferences.setDouble("ShooterFF", temp);
    return temp;
  }
  
  public static boolean getPIDTuning(){
    boolean temp = Preferences.getBoolean("ShooterFF", ShooterConstants.isTuning);
    Preferences.setBoolean("ShooterFF", temp);
    return temp;
  }
}