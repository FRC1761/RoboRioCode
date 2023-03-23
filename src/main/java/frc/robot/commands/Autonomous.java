package frc.robot.commands;

//import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotPreferences;

public class Autonomous extends SequentialCommandGroup {
  /**
  * Add your docs here.
  */
  public Autonomous() {
    //open grabber
    addCommands(new OpenGrabber());
    //drive back short distance
    addCommands(new TimedDrive(RobotPreferences.auto1stDelay(),-RobotPreferences.autoPower()));
    //close grabber
    addCommands(new CloseGrabber());
    //drive forward until hit bumper on level1
    addCommands(new TimedDrive(RobotPreferences.auto2ndDelay(),RobotPreferences.autoPower()));
    //drive back into Neutral Zone
    addCommands(new TimedDrive(RobotPreferences.auto3rdDelay(),-RobotPreferences.autoPower()));
  }
}
