package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

public class RobotPreferences {

  public static double auto1stDelay() {
    double temp = Preferences.getDouble("auto1stDelay", 2.0);
    Preferences.setDouble("auto1stDelay", temp);
    return temp;
  }

  public static double auto2ndDelay() {
    double temp = Preferences.getDouble("auto2ndDelay", 2.0);
    Preferences.setDouble("auto2ndDelay", temp);
    return temp;
  }

  public static double auto3rdDelay() {
    double temp = Preferences.getDouble("auto3rdDelay", 2.0);
    Preferences.setDouble("auto3rdDelay", temp);
    return temp;
  }

  public static double autoPower() {
    double temp = Preferences.getDouble("autoPower", .65);
    Preferences.setDouble("autoPower", temp);
    return temp;
  }

  
}
