// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.*;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CloseGrabber;
import frc.robot.commands.OpenGrabber;
import frc.robot.commands.Autonomous;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.ArcadeDrive;
import frc.robot.subsystems.ArmExtension;
// import frc.robot.subsystems.ScissorLift;

public class Robot extends TimedRobot {
  
  public static final Timer m_timer = new Timer();
  public static final Grabber GRABBER = new Grabber();
  public static final ArcadeDrive ARCADE_DRIVE = new ArcadeDrive();
  public static final ArmExtension ARM_EXTENSION = new ArmExtension();
  // public static final ScissorLift  SCISSOR_LIFT = new ScissorLift();


  private CommandXboxController gamepad1 = new CommandXboxController(Constants.xboxPort);
//	private CommandXboxController gamepad2 = new CommandXboxController(Constants.xboxPort2);

  @Override
  public void robotInit(){
    UsbCamera frontCamera;
		frontCamera = CameraServer.startAutomaticCapture(0);
		frontCamera.setFPS(30);
		frontCamera.setResolution(160,120);

    Trigger rightBumpah = gamepad1.rightBumper(); // Creates a new Trigger object for the `X` button on exampleCommandController
    rightBumpah.onTrue(new OpenGrabber());

    Trigger leftBumpah = gamepad1.leftBumper(); // Creates a new Trigger object for the `X` button on exampleCommandController
    leftBumpah.onTrue(new CloseGrabber());

    Trigger xButton = gamepad1.x(); // Creates a new Trigger object for the `X` button on exampleCommandController
    xButton.onTrue(new CloseGrabber());
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  public void autonomousInit(){
    
    // m_timer.reset();
    // m_timer.start();
    
    // ARCADE_DRIVE.autoDrive(1, 0, m_timer);
    CommandScheduler.getInstance().schedule(new Autonomous(ARCADE_DRIVE));
  }

  public void autonomousPeriodic(){
      // // Drive forwards half speed, make sure to turn input squaring off
      // System.out.println("1");
      // double kDefaultPeriod = 1;

      // ARCADE_DRIVE.autoDrive(1, 0, m_timer);
      // ARCADE_DRIVE.autoDrive(kDefaultPeriod, kDefaultPeriod, m_timer);
      // System.out.println("2");

      // // ARCADE_DRIVE.stop(); // stop robot
      // // System.out.println("3");
    }

  public void autonomousEnd(){
    ARCADE_DRIVE.stop();
    System.out.println("stopped");
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void teleopPeriodic() {
    
    ARCADE_DRIVE.periodic();
    ARM_EXTENSION.periodic();
    // SCISSOR_LIFT.periodic();
  }
}
