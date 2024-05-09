// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class DriveTrain extends SubsystemBase {

  private TalonSRX rearLeft = new TalonSRX(DriveConstants.kRearLeftDrivingCanId);
  private TalonSRX rearRight = new TalonSRX(DriveConstants.kRearRightDrivingCanId);
  private TalonSRX frontLeft = new TalonSRX(DriveConstants.kFrontLeftDrivingCanId);
  private TalonSRX frontRight = new TalonSRX(DriveConstants.kFrontRightDrivingCanId);
  
  boolean isSlowMode = false;
  /** Creates a new DriveTrainExample. */
  public DriveTrain() {  }

  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void drive(double left, double right) {
    //drive all the motors]
    rearLeft.set(TalonSRXControlMode.PercentOutput,-left);
    frontLeft.set(TalonSRXControlMode.PercentOutput,-left);
    rearRight.set(TalonSRXControlMode.PercentOutput,right);
    frontRight.set(TalonSRXControlMode.PercentOutput,right);
  }
}

