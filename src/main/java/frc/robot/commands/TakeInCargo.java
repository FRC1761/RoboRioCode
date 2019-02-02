/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * Closes the claw for one second. Real robots should use sensors, stalling
 * motors is BAD!
 */
public class TakeInCargo extends Command {
  public TakeInCargo() {
    requires(Robot.m_intake);
  }

  public boolean isFinished() {
    return false;
  }
  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }
@Override
protected void interrupted(){
  Robot.m_intake.stop();
}

  protected void execute() {
    Robot.m_intake.close();
}
// Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_intake.stop();
  }
}
