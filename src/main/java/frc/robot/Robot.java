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
import edu.wpi.first.cscore.UsbCamera;
import frc.robot.commands.Autonomous;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakeArm;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.GetAutoPoints;
/*import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Angler;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.VelocityControlledShooter;
/**/
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {


  public static DriveTrain m_drivetrain;
  public static Shooter m_shooter;
  public static Intake m_intake;
  public static IntakeArm m_intakeArm;
  public static OI m_oi;
  public static GetAutoPoints m_GetAutoPoints;
  
  /*public static Intake m_intake;
  public static Conveyor m_conveyor;
  public static Angler m_angler;
  public static Climber m_climber;
  public static VelocityControlledShooter m_velocityshooter;
  /** */
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //initialize the camera
    UsbCamera frontCamera,rearCamera;
		frontCamera = CameraServer.startAutomaticCapture(0);
		frontCamera.setFPS(15);
		frontCamera.setResolution(320,240);
    /* Setting two usb cameras is as simple as this.*/

    rearCamera  = CameraServer.startAutomaticCapture(1);
    rearCamera.setFPS(15);
    rearCamera.setResolution(160,120);
    /**/
    // Initialize all subsystems
    m_drivetrain = new DriveTrain();
    m_shooter = new Shooter();
    m_intake  = new Intake();
    m_intakeArm  = new IntakeArm();
    m_oi = new OI();
/*
    m_conveyor = new Conveyor();
    m_intake = new Intake();
    m_angler = new Angler();
    m_climber= new Climber();
    */
  //m_velocityshooter = new VelocityControlledShooter();
    // Initialize the autonomous command here!

    m_GetAutoPoints = new GetAutoPoints(5);
  }

  @Override
  public void autonomousInit() {
    m_GetAutoPoints.start(); // schedule the autonomous command
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
    // Bye bye autonomous!
    m_GetAutoPoints.cancel();
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
