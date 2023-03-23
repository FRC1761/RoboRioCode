//import robot here
//import commandbase
package frc.robot.commands;
import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TimedDrive extends WaitCommand {

private double expireTime;
private double timeout;
private double direction;

  public TimedDrive(double seconds,double direction) {
    super(seconds);

    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    addRequirements(Robot.ARCADE_DRIVE);
    this.direction = direction;
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void initialize() {    
    Robot.ARCADE_DRIVE.autoDrive(direction,0);
  }

  // Called once after isFinished returns true
  @Override
  protected void end(Boolean isInterrupted) {
    Robot.ARCADE_DRIVE.autoDrive(0,0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
