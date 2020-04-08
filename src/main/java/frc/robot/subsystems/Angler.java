/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;

/**
 * This part of the robot is a worm screw controlled by a JOhnson electric motor
 * andymark JE-PLG-149.
 */
public class Angler extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private final VictorSP m_AnglerMotor = new VictorSP(Constants.ShooterTargeterSP);
  private final Encoder m_encoder = new Encoder(0, 1);

  public Angler() {
    super();
    m_encoder.reset();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void drive(double speed) {
    m_AnglerMotor.set(speed);
    SmartDashboard.putNumber("AnglerDistance", m_encoder.getDistance());
  }
}
