
package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


/**
 * Have the robot drive tank style using the PS3 Joystick until interrupted.
 */
public class TankDriveWithJoystick extends Command {
  
private final double magnitude = 1.0;
    
private final double deadzone = 0.0;

  public TankDriveWithJoystick() {
    requires(Robot.m_drivetrain);
  }

  // Called repeatedly when this Command is scheduled to run

  @Override
  protected void execute() {
    // Robot.m_drivetrain.drive(Robot.m_oi.getLeftJoystick());
    
    	// Scaling joysticks to limit max speed
    	double leftPower = Robot.m_oi.getLeftJoystick().getY() * magnitude;
        double rightPower = Robot.m_oi.getRightJoystick().getY() * magnitude;
        
        // Adding a range of the joysticks in which the robot will not respond
        leftPower = ((Math.abs(leftPower) < deadzone)? 0 : leftPower) * -1;
        rightPower = (Math.abs(rightPower) < deadzone)? 0 : rightPower;
        
        Robot.m_drivetrain.drive(leftPower, rightPower);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_drivetrain.drive(0, 0);
  }
}
