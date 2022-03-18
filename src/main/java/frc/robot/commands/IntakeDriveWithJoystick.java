// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.Constants;

public class IntakeDriveWithJoystick extends Command {
  /** Creates a new IntakeDriveWithJoystick. */
  public IntakeDriveWithJoystick() {
    // Use addRequirements() here to declare subsystem dependencies.
    requires(Robot.m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {


    Robot.m_shooter.drive(Robot.m_oi.getGamepad().getRawAxis(Constants.IntakeAxis));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(){
    Robot.m_intake.drive(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
