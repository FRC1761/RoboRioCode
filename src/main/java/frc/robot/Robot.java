// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.*;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.CloseGrabber;
import frc.robot.commands.OpenGrabber;
import frc.robot.subsystems.ArcadeDrive;
import frc.robot.subsystems.Grabber;

public class Robot extends TimedRobot {
  public static final Grabber Grabber = new Grabber();
  public static final ArcadeDrive arcadeDrive = new ArcadeDrive();
  private CommandXboxController gamepad1 = new CommandXboxController(Constants.xboxPort);
	//private CommandXboxController gamepad2 = new CommandXboxController(1);

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
  public void teleopPeriodic() {
    arcadeDrive.periodic();
  }
}
