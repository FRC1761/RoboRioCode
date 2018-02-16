/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1761.robot.commands;

import org.usfirst.frc.team1761.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * The main autonomous command to pickup and deliver the soda to the box.
 */
public class Autonomous extends Command {
	public Autonomous() {
		requires(Robot.m_drivetrain);
	}
	
	protected void initialize() {
		Robot.m_drivetrain.disableSafety();
    }
	
	protected void execute() {
		
		Robot.m_drivetrain.drive(.75,.75);
		Timer.delay(3);
		Robot.m_drivetrain.drive(0,0);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.m_drivetrain.drive(0, 0);
		Robot.m_drivetrain.enableSafety();
	}
}
