// Copyright (c) FIRST and other WPILib contributors.
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Constants;

public class IntakeArmWithJoystick extends Command {
  /** Creates a new IntakeDriveWithJoystick. */
  public IntakeArmWithJoystick() {
    // Use addRequirements() here to declare subsystem dependencies.
    requires(Robot.m_intakeArm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.m_intakeArm.drive(Robot.m_oi.getGamepad().getRawAxis(Constants.IntakeArmAxis));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(){
    Robot.m_intakeArm.drive(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
