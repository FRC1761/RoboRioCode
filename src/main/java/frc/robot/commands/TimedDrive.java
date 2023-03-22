//import robot here
//import commandbase

public class TimedDrive extends CommandBase {

private double expireTime;
private double timeout;
private double direction;

  public DoDelay(double seconds,double direction) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_drive);
    timeout = seconds;
    this.direction = direction;
  }

  protected void startTimer() {
    expireTime = timeSinceInitialized() + timeout;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startTimer();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    Robot.m_drive.drive(direction,0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (timeSinceInitialized() >= expireTime);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_drive.drive(0,0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
