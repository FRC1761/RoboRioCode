// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.controls.controllers.DriverController;
//import frc.robot.controls.controllers.DriverController;
import frc.robot.controls.controllers.OperatorController;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakeState;
//import edu.wpi.first.wpilibj2.command.Subsystem;
//import frc.robot.subsystems.DriveSubsystem;
//import frc.robot.subsystems.ShooterSubsystem;
//import edu.wpi.first.wpilibj.XboxController.Button;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends LoggedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private Intake m_intake;
  private boolean isIntakeAttached = true;  //TODO need to attach and TEST INTAKE!!!!
  private Climber m_climber;
  private boolean isClimberAttached = true; //TODO need to attach climber and TEST!!!
  private PowerDistribution PD;
  
  private DriverController m_driverController = new DriverController(0);
  private OperatorController m_operatorController = new OperatorController(1);
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override 
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    //m_driverController = new DriverController(0);
    Logger.recordMetadata("ProjectName", "MyProject"); // Set a metadata value
if(isIntakeAttached){
  m_intake = Intake.getInstance();  
}
if(isClimberAttached){
  m_climber = Climber.getInstance();
}
if (isReal()) {
    Logger.addDataReceiver(new WPILOGWriter()); // Log to a USB stick ("/U/logs")
    Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
    PD = new PowerDistribution(1, ModuleType.kRev); // Enables power distribution logging
    PD.clearStickyFaults();
} else {
    setUseTiming(false); // Run as fast as possible
    String logPath = LogFileUtil.findReplayLog(); // Pull the replay log from AdvantageScope (or prompt the user)
    Logger.setReplaySource(new WPILOGReader(logPath)); // Read replay log
    Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim"))); // Save outputs to a new log
}

// Logger.disableDeterministicTimestamps() // See "Deterministic Timestamps" in the "Understanding Data Flow" page
Logger.start(); // Start logging! No more data receivers, replay sources, or metadata values may be added.
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if(isIntakeAttached){    
      if (m_operatorController.getWantsFullIntake()) {
        m_intake.goToGround();
        System.out.println("trying to go to Ground");
      } else if (m_operatorController.getWantsIntake()) {
        if (m_intake.getIntakeHasNote()) {
          m_intake.pulse();
        } else {
          m_intake.intake();
        }
      } else if (m_operatorController.getWantsEject()) {
        m_intake.eject();
      } else if (m_operatorController.getWantsSource()) {
        m_intake.goToSource();
      } else if (m_operatorController.getWantsStow()) {
        m_intake.goToStow();
        System.out.println("trying to go to Stow");
      } else if (m_intake.getIntakeState() != IntakeState.INTAKE) {
        m_intake.stopIntake();
      }
    }
    
    if(isClimberAttached){
      // Climber
      if (m_driverController.getWantsClimberClimb()) {
        m_climber.climb();
      } else if (m_driverController.getWantsClimberRelease()) {
        m_climber.release();
      } else if (m_driverController.getWantsClimberTiltLeft()) {
        //TODO left tilt disabled
        //m_climber.tiltLeft();
      } else if (m_driverController.getWantsClimberTiltRight()) {
        //TODO right tilt disabled
        //m_climber.tiltRight();
      } else {
        m_climber.stopClimber();
      }
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
