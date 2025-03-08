// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DigitalOutput;
//include libraries we will use 
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.RobotPreferences;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  //Object properties go here
  private static Shooter m_instance; 
  private final SparkMax leftShooter;
  private final SparkMax rightShooter;
  private final RelativeEncoder leftEncoder,rightEncoder;
  private final DigitalOutput limitSwitch;

  /** Creates a new Shooter. */
  private Shooter() {

    leftShooter = new SparkMax(ShooterConstants.leftShooterCANID,MotorType.kBrushless);
    rightShooter = new SparkMax(ShooterConstants.rightShooterCANID, MotorType.kBrushless);
    leftEncoder = leftShooter.getEncoder();
    rightEncoder = rightShooter.getEncoder();
    limitSwitch = new DigitalOutput(ShooterConstants.limitSwitchDioID);
  }

  public static Shooter getInstance() {
    if (m_instance == null) {
      m_instance = new Shooter();
    }
    return m_instance;
  }

  public void shootHigh(){   
    leftShooter.set(RobotPreferences.getShooterSpeedHigh());
    rightShooter.set(RobotPreferences.getShooterSpeedHigh());
  }

  public void shootLow(){   
    leftShooter.set(RobotPreferences.getShooterSpeedLow());
    rightShooter.set(RobotPreferences.getShooterSpeedLow());
  }

  public void stop(){
    leftShooter.set(0);
    rightShooter.set(0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotPreferences.setShooterSpeeds(leftEncoder.getVelocity(),rightEncoder.getVelocity()/**/);
  }
}
