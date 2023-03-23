package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

public class RobotPreferences {

  public static double auto1stDelay() {
    double temp = Preferences.getInstance().getDouble("auto1stDelay", 2.0);
    Preferences.getInstance().setDouble("auto1stDelay", temp)
    return temp;
  }

  public static double auto2ndDelay() {
    double temp = Preferences.getInstance().getDouble("auto2ndDelay", 2.0);
    Preferences.getInstance().setDouble("auto2ndDelay", temp)
    return temp;
  }

  public static double auto3rdDelay() {
    double temp = Preferences.getInstance().getDouble("auto3rdDelay", 2.0);
    Preferences.getInstance().setDouble("auto3rdDelay", temp)
    return temp;
  }

  public static double autoPower() {
    double temp = Preferences.getInstance().getDouble("autoPower", .65);
    Preferences.getInstance().setDouble("autoPower", temp)
    return temp;
  }
}
