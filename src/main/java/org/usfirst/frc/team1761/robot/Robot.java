/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1761.robot;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.usfirst.frc.team1761.robot.commands.Autonomous;
import org.usfirst.frc.team1761.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1761.robot.subsystems.FourBar;
import org.usfirst.frc.team1761.robot.subsystems.LastYearsDriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
	Command m_autonomousCommand;

	public static DriveTrain m_drivetrain;
	public static LastYearsDriveTrain m_olddrivetrain;
	public static OI m_oi;
	public static FourBar m_fourbar;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// Initialize all subsystems
		UsbCamera myCamera;
		myCamera = CameraServer.getInstance().startAutomaticCapture(0);
		myCamera.setFPS(30);
		myCamera.setResolution(680,480);
		//m_drivetrain = new DriveTrain();
		m_olddrivetrain = new LastYearsDriveTrain();
		m_fourbar    = new FourBar();
/*		m_elevator = new Elevator();
		m_wrist = new Wrist();
		m_claw = new Claw(); /**/
		m_oi = new OI();

		// instantiate the command used for the autonomous period
		m_autonomousCommand = new Autonomous();

/*		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(m_drivetrain);
		SmartDashboard.putData(m_elevator);
		SmartDashboard.putData(m_wrist);
		SmartDashboard.putData(m_claw); /**/
	}

	@Override
	public void autonomousInit() {
		m_autonomousCommand.start(); // schedule the autonomous command (example)
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		m_autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
		//test everything here
		
	}

	/**
	 * The log method puts interesting information to the SmartDashboard.
	 */
	private void log() {
/*		m_wrist.log();
		m_elevator.log();
		m_drivetrain.log();
		m_claw.log(); /**/
	}
}
