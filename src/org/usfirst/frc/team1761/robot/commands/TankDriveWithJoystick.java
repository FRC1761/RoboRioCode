/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1761.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team1761.robot.Robot;

/**
 * Have the robot drive tank style using the PS3 Joystick until interrupted.
 */
public class TankDriveWithJoystick extends Command {
	private double driveLimiter;
	private DifferentialDrive myDrive;
	
	public TankDriveWithJoystick(Subsystem mySubsystem,DifferentialDrive myDrive) {
		requires(mySubsystem);
		this.myDrive = myDrive;		
	}

	protected void initialize() {
		myDrive.setSafetyEnabled(false);
		Preferences prefs = Preferences.getInstance();
		driveLimiter = prefs.getDouble("Drive Limiter", 1.0);
    }
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		myDrive.tankDrive(Robot.m_oi.getLeftJoystick().getY() * driveLimiter,
				                 Robot.m_oi.getRightJoystick().getY() * driveLimiter);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		myDrive.tankDrive(0, 0);
		myDrive.setSafetyEnabled(true);
	}
}
