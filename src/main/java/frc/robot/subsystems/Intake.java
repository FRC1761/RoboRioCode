// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DigitalOutput;
//include libraries we will use 
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.RobotPreferences;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {

  private static Intake m_instance; 
  private final SparkFlex intakeMotor;
  private final RelativeEncoder intakeEncoder;
  private SparkClosedLoopController intakeController;
  /** Creates a new Shooter. */
  public Intake() {
    intakeMotor = new SparkFlex(IntakeConstants.FeederCAN,
                                 MotorType.kBrushless);
    intakeEncoder = intakeMotor.getEncoder();
    intakeController = intakeMotor.getClosedLoopController();
  }

  public static Intake getInstance() {
    if (m_instance == null) {
      m_instance = new Intake();
    }
    return m_instance;
  }

  public void feedBallsIn(){   
    intakeMotor.set(RobotPreferences.getIntakePower());
    //Set the setpoint of the PID controller in raw position mode
    //intakeMotor.setSetpoint(RobotPreferences.getIntakePoint, ControlType.kVelocity);
  }

  public void stopBallsIn(){
    intakeMotor.set(0);
  }

  @Override
  public void periodic() {
    RobotPreferences.setIntakeSpeedDisplay(intakeEncoder.getVelocity());
  }
}
