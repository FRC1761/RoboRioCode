// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.Encoder;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;

/** Add your docs here. */
public class PIDShooter extends PIDSubsystem {
  /** Add your docs here. */
  private final WPI_TalonSRX m_shooterMotor = new WPI_TalonSRX(Constants.Shooter);
  private Encoder shooterEncoder;
  public PIDShooter() {
    // Intert a subsystem name and PID values here
    super("Shooter", 0.75, 0, 0);
    // Use these to get going:
    setSetpoint(75000);
    // have command enable();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());

  }

  @Override
  protected double returnPIDInput() { 
    // Return your input value for the PID loop
    // e.g. a sensor, like a potentiometer:
    // yourPot.getAverageVoltage() / kYourMaxVoltage;
    
    shooterEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k1X);

    return shooterEncoder.getRate();
  }

  @Override
  protected void usePIDOutput(double output) {
    // Use output to drive your system, like a motor
    m_shooterMotor.set(output);
  }
}
