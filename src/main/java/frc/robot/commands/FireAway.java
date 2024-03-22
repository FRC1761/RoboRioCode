package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.SCShooter;
import frc.robot.subsystems.Intake.IntakeState;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FireAway extends InstantCommand {
  private Intake mIntake;
  private SCShooter mShooter;
  public FireAway() {
    // Use addRequirements() here to declare subsystem dependencies.
    mIntake = Intake.getInstance();
    mShooter = SCShooter.getInstance();
    addRequirements(mIntake,mShooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !mIntake.getIntakeHasNote() && mIntake.getIntakeState() == IntakeState.EJECT;
  }
}