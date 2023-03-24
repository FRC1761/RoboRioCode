// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.*;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CloseGrabber;
import frc.robot.commands.OpenGrabber;
import frc.robot.commands.Autonomous;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.ArcadeDrive;
import frc.robot.subsystems.ArmExtension;
import frc.robot.subsystems.ScissorLift;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
public class Robot extends TimedRobot {

  public static final Grabber GRABBER = new Grabber();
  public static final ArcadeDrive ARCADE_DRIVE = new ArcadeDrive();
  public static final ArmExtension ARM_EXTENSION = new ArmExtension();
  public static final ScissorLift  SCISSOR_LIFT = new ScissorLift();


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
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  public void autonomousInit(){
    CommandScheduler.getInstance().schedule(new Autonomous());
  }

  public void autonomousEnd(){
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void teleopPeriodic() {
    GRABBER.periodic();
    ARCADE_DRIVE.periodic();
    ARM_EXTENSION.periodic();
    SCISSOR_LIFT.periodic();
  }
}
