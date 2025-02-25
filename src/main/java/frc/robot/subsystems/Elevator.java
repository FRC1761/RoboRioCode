// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.RobotPreferences;
import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  private static Elevator m_instance;
  private final SparkMax elevMotor;
  private final RelativeEncoder elevEncoder;
  private final LEDs m_leds;

  public static Elevator getInstance(){
     if(m_instance == null){
      m_instance = new Elevator();
     }
     return m_instance;
  }


  /** Creates a new Elevator. */
  private Elevator() {
    //TODO would like to add power limiter incase we get jammed up.
    //  also something to stop the power if the encoder stops moving.
    elevMotor = new SparkMax(ElevatorConstants.ElevatorCanID, MotorType.kBrushless);
    elevEncoder = elevMotor.getEncoder();
    m_leds = LEDs.getInstance();
    elevEncoder.setPosition(0.0);
  }

  public double getHeight(){
    //gear ratio is 9:1 , 5:1 (45 times)
    return elevEncoder.getPosition();
  }

  public void drive(double input){
    elevMotor.set(input);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double height = getHeight();
    RobotPreferences.setHeightDisplay(getHeight());
    if(height < ElevatorConstants.resolution) m_leds.goTeamColor();
    else if(height < ElevatorConstants.levelOne) m_leds.goBetweenLevels();
    else if(height > ElevatorConstants.levelOne) m_leds.goLevelOne();
    else if(height > ElevatorConstants.levelOne + ElevatorConstants.resolution) m_leds.goBetweenLevels();
    else if(height > ElevatorConstants.levelTwo) m_leds.goLevelTwo();
    else if(height > ElevatorConstants.levelTwo+ ElevatorConstants.resolution) m_leds.goBetweenLevels();
    else if(height > ElevatorConstants.levelThree) m_leds.goLevelThree();
}


}
