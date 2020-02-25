/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.cameraserver.*;
import edu.wpi.cscore.UsbCamera;
import frc.robot.commands.Autonomous;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Angler;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Conveyor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  Command m_autonomousCommand;

  public static DriveTrain m_drivetrain;
  public static Conveyor m_conveyor;
  public static OI m_oi;
  public static Intake m_intake;
  public static Shooter m_shooter;
  public static Angler m_angler;
  public static Climber m_climber;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //initialize the camera
    UsbCamera frontCamera;
		frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
		frontCamera.setFPS(30);
		frontCamera.setResolution(320,240);
    /* Setting two usb cameras is as simple as this.

    rearCamera  = CameraServer.getInstance().startAutomaticCapture(1);
    rearCamera.setFPS(30);
    rearCamera.setResolution(320,240);
    */
    // Initialize all subsystems
    m_drivetrain = new DriveTrain();
    m_conveyor = new Conveyor();
    m_intake = new Intake();
    m_shooter = new Shooter();
    m_angler = new Angler();
    m_oi = new OI();
    m_climber= new Climber();

    // instantiate the command used for the autonomous period
    m_autonomousCommand = new Autonomous();
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
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  private void log() {
  }
}
