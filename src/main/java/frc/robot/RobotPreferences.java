package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.ShooterConstants;
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

    public static double getShooterSpeedHigh(){
      double temp = Preferences.getDouble("shooterSpeedHigh", ShooterConstants.shooterSpeedHigh);
      Preferences.setDouble("shooterSpeedHigh", temp);
      return temp;
    }

    public static double getShooterSpeedLow(){
      double temp = Preferences.getDouble("shooterSpeedLow", ShooterConstants.shooterSpeedLow);
      Preferences.setDouble("shooterSpeedLow", temp);
      return temp;
    }

    public static void setHeightDisplay(double height) {
      Preferences.setDouble("Elevator Height",height);
    }

    public static void setClimberDisplay(double climb){
      Preferences.setDouble("ClimberRotations",climb);
    }

    public static double getClimbSpeed(){
      double temp = Preferences.getDouble("ClimberSpeed",ClimberConstants.defSpeed);
      Preferences.setDouble("ClimberSpeed",temp);
      return temp;
    }

    public static void setShooterSpeeds(double left, double right){
      Preferences.setDouble("Left ShooterSpeed",left);
      Preferences.setDouble("Right ShooterSpeed",right);
    }

    public static double getAutoX(){
      double x = Preferences.getDouble("AutonomousX",3);
      Preferences.setDouble("AutonomousX",x);
      return x;
    }

    public static double getAutoY(){
      double y = Preferences.getDouble("AutonomousY",0);
      Preferences.setDouble("AutonomousY",y);
      return y;
    }
}