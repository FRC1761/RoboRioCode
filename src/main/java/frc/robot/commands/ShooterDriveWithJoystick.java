/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Have the robot drive tank style using the PS3 Joystick until interrupted.
 */
public class ShooterDriveWithJoystick extends Command {
  public ShooterDriveWithJoystick() {
    requires(Robot.m_shooter);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_shooter.drive(Robot.m_oi.getGamepad().getRawAxis(Constants.ShooterAxis));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false; // Runs until interrupted
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_shooter.drive(0);
  }
}
