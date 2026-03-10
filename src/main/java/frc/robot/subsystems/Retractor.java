// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotPreferences;
import frc.robot.Constants.IntakeConstants;

public class Retractor extends SubsystemBase {
  private static Retractor m_instance;
  private final SparkFlex retractMotor;
  private final RelativeEncoder retractEncoder;
  
  /** Creates a new Retract. */
  public Retractor() {
    retractMotor = new SparkFlex(IntakeConstants.RetractCAN,
                                 MotorType.kBrushless);
    retractEncoder = retractMotor.getEncoder();
  }
  
  public void retractIntake(){
      retractMotor.set(RobotPreferences.getRetractPower());
      //Set the setpoint of the PID controller in raw position mode
      //retractMotor.setSetpoint(RobotPreferences.getRetractPoint(), ControlType.kPosition);
  }

  public void extendIntake(){
      retractMotor.set(-RobotPreferences.getRetractPower());
      //Set the setpoint of the PID controller in raw position mode
      //retractMotor.setSetpoint(RobotPreferences.getRetractPoint(), ControlType.kPosition);
  }

  public static Retractor getInstance() {
    if (m_instance == null) {
      m_instance = new Retractor();
    }
    return m_instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
