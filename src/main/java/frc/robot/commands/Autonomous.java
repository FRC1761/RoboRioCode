package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotPreferences;

public class Autonomous extends CommandGroup {
  /**
  * Add your docs here.
  */
  public Autonomous() {
    //open grabber
    addSequential(new OpenGrabber());
    //drive back short distance
    addSequential(new TimedDrive(RobotPreferences.auto1stDelay(),-RobotPreferences.autoPower()));
    //close grabber
    addSequential(new CloseGrabber());
    //drive forward until hit bumper on level1
    addSequential(new TimedDrive(RobotPreferences.auto2ndDelay(),RobotPreferences.autoPower()));
    //drive back into Neutral Zone
    addSequential(new TimedDrive(RobotPreferences.auto3rdDelay(),-RobotPreferences.autoPower()));
  }
}
