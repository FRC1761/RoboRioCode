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
 * Closes the claw for one second. Real robots should use sensors, stalling
 * motors is BAD!
 */
public class TimedLowerWrist extends TimedCommand {
  public TimedLowerWrist() {
    super(.60);
    requires(Robot.m_wrist);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.m_wrist.close();
  }

  // Make this return true when this Command no longer needs to run execute()
 // @Override
  //protected boolean isFinished() {
  //  return true; //Robot.m_claw.isGrabbing();
  //}

  // Called once after isFinished returns true
  @Override
  protected void end() {
    // NOTE: Doesn't stop in simulation due to lower friction causing the
    // can to fall out
    // + there is no need to worry about stalling the motor or crushing the
    // can.
    if (!Robot.isSimulation()) {
      Robot.m_wrist.stop();
    }
  }
}
