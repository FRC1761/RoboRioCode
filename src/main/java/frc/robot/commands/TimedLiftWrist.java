/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;

/**
 * Opens the claw for one second. Real robots should use sensors, stalling
 * motors is BAD!
 */
public class TimedLiftWrist extends TimedCommand {


  public TimedLiftWrist() {
    super(.60);
    requires(Robot.m_wrist);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_wrist.open();
    Robot.m_wrist.log();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_wrist.stop();
    Robot.m_wrist.log();

  }
}
