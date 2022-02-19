// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;


public class Intake extends Subsystem {
  private final WPI_TalonSRX m_intakeMotor 
  = new WPI_TalonSRX(Constants.IntakeCanAddress);
  private double driveLimiter;

  /** Creates a new Intake. */
  public Intake() {}

  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new IntakeDriveWithJoystick());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
