// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.RobotPreferences;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private static Climber m_instance;
  private final SparkMax climberMotor;
  private final RelativeEncoder climberEncoder;

  public static Climber getInstance(){
     if(m_instance == null){
      m_instance = new Climber();
     }
     return m_instance;
  }

  /** Creates a new Climber. */
  public Climber() {
    climberMotor = new SparkMax(ClimberConstants.climberMotorCanID, MotorType.kBrushless);
    climberEncoder = climberMotor.getEncoder(); 
  }

  public void climb(){
    climberMotor.set(RobotPreferences.getClimbSpeed());
  }

  public void release(){
      climberMotor.set(-RobotPreferences.getClimbSpeed());
  }

  public void stop() {
    climberMotor.set(0);
  }
  
  public double getRotations(){
    return climberEncoder.getPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    RobotPreferences.setClimberDisplay(getRotations());
  }
}
