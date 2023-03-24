package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotPreferences;

public class Autonomous extends SequentialCommandGroup {
  /**
  * Add your docs here.
  */
  public Autonomous() {
    //open grabber
    this.andThen(new OpenGrabber()).
    andThen(new TimedDrive(RobotPreferences.auto1stDelay(),-RobotPreferences.autoPower())).
    andThen(new CloseGrabber()).
    andThen(new TimedDrive(RobotPreferences.auto2ndDelay(),RobotPreferences.autoPower())).
    andThen(new TimedDrive(RobotPreferences.auto3rdDelay(),-RobotPreferences.autoPower()));
  }
}
