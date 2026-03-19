package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.IntakeConstants;
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

  public static double getShooterFar() {
    double temp = Preferences.getDouble("shooterFarSpeed", ShooterConstants.kShootFar);
    Preferences.setDouble("shooterFarSpeed", temp);
    return temp;
  }

  public static double getShooterShort() {
    double temp = Preferences.getDouble("shooterShortSpeed", ShooterConstants.kShootShort);
    Preferences.setDouble("shooterShortSpeed", temp);
    return temp;
  }
    /* end example of preferences */
  public static void setShooterSpeedDisplay(double speed) {
    Preferences.setDouble("shooterSpeed",speed);
  }

  public static double getIntakePower() {
    double temp = Preferences.getDouble("intakePower", IntakeConstants.feedPower);
    Preferences.setDouble("intakePower", temp);
    return temp;
  }
  
  public static double getRetractPower() {
    double temp = Preferences.getDouble("retractPower", IntakeConstants.feedPower);
    Preferences.setDouble("retractPower", temp);
    return temp;
  }

  public static double getGatePower() {
    double temp = Preferences.getDouble("gatePower", ShooterConstants.kGateMove);
    Preferences.setDouble("gatePower", temp);
    return temp;
  }

  public static void setIntakeSpeedDisplay(double speed) {
    Preferences.setDouble("intakeSpeed",speed);
  }

  public static void setIntakePosition(double position){
    Preferences.setDouble("intakePosition", position);
  }

  public static void setRetractForwLimit(boolean isForwardPressed){
    Preferences.setBoolean("Forward Limit Switch", isForwardPressed);
  }

  public static void setRetractReverseLimit(boolean isReversePressed){
    Preferences.setBoolean("Reverse Limit Switch", isReversePressed);    
  }
}