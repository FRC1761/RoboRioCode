// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.commands.IntakeArmWithJoystick;
import frc.robot.commands.IntakeDriveWithJoystick;

/** Add your docs here. */
public class IntakeArm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private final WPI_TalonSRX m_intakeArm
  = new WPI_TalonSRX(Constants.IntakeArmAddress);
  double intakeLimiter;

  public IntakeArm() {
    super();
    //get key value or use default 0.0;
    intakeLimiter = Preferences.getDouble("IntakeArm Factor", 0.0);
    //Push value back to Preferences widget so it forces
    //correct key to show up with default value if not set.
    Preferences.setDouble("IntakeArm Factor",intakeLimiter);
    m_intakeArm.setSafetyEnabled(false);
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new IntakeArmWithJoystick());
  }

  public void drive(double speed) {
    intakeLimiter = Preferences.getDouble("IntakeArm Factor", 0.0);
    this.m_intakeArm.set(speed* intakeLimiter);
  }
}
