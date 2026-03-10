// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DigitalOutput;
//include libraries we will use 
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.RobotPreferences;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  private static Shooter m_instance; 
  private final SparkFlex shooterMotor;
  private final RelativeEncoder shooterEncoder;
  /** Creates a new Shooter. */
  public Shooter() {
    shooterMotor = new SparkFlex(ShooterConstants.ShooterAddress,
                                 MotorType.kBrushless);
    shooterEncoder = shooterMotor.getEncoder();
  }

  public static Shooter getInstance() {
    if (m_instance == null) {
      m_instance = new Shooter();
    }
    return m_instance;
  }

  
  public void shootFar(){   
    shooterMotor.set(RobotPreferences.getShooterFar());
  }

  public void shootShort(){   
    shooterMotor.set(RobotPreferences.getShooterShort());
  }

  public void stop(){
    shooterMotor.set(0);
  }

  @Override
  public void periodic() {
    RobotPreferences.setShooterSpeedDisplay(shooterEncoder.getVelocity());
  }
}
