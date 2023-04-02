package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotPreferences;
import frc.robot.subsystems.ArcadeDrive;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous extends CommandBase {
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */

  Timer m_timer = new Timer();
  ArcadeDrive auto_drive = new ArcadeDrive();
  
  public Autonomous(ArcadeDrive subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_timer.reset();
    m_timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    auto_drive.autoDrive(-0.61,0.0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_timer.get() > 7) {
      return true;
    }
    else {
      return false;
    }
  }
}

  //  final Timer m_timer = new Timer();
  //  m_timer.reset();



    // //open grabber
    // this.andThen(new OpenGrabber()).
    // andThen(new TimedDrive(RobotPreferences.auto1stDelay(),-RobotPreferences.autoPower())).
    // andThen(new CloseGrabber()).
    // andThen(new TimedDrive(RobotPreferences.auto2ndDelay(),RobotPreferences.autoPower())).
    // andThen(new TimedDrive(RobotPreferences.auto3rdDelay(),-RobotPreferences.autoPower()));
