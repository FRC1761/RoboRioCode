// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.commands.IntakeDriveWithJoystick;


public class Intake extends Subsystem {
  private final WPI_TalonSRX m_intakeMotor
  = new WPI_TalonSRX(Constants.IntakeCanAddress);
  private double intakeLimiter;

  /** Creates a new Intake. */
  public Intake() {
    super();
    //get key value or use default 1.0;
    intakeLimiter = Preferences.getDouble("Intake Factor", 1.0);
    //Push value back to Preferences widget so it forces
    //correct key to show up with default value if not set.
    Preferences.setDouble("Intake Factor",intakeLimiter);
    //m_intakeMotor.setSafetyEnabled(false);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    intakeLimiter = Preferences.getDouble("Intake Factor", 1.0);
    setDefaultCommand(new IntakeDriveWithJoystick());

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double speed) {
    this.m_intakeMotor.set(speed);
  }
}
