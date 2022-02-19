/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.ShooterDriveWithJoystick;
import edu.wpi.first.wpilibj.Preferences;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.*;

//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 * Add your docs here.
 */
public class Shooter extends SubsystemBase {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private int targetRPMS = 29000;
  private final WPI_TalonSRX m_shooterMotor 
      = new WPI_TalonSRX(Constants.Shooter);
  private double driveLimiter;

  private StringBuilder sb = new StringBuilder();
  public Shooter(){
    super();
    driveLimiter = Preferences.getDouble("Shooter Factor", 1.0);
    initTalon(m_shooterMotor,false);
    //Push value back to Preferences widget so it forces
    //correct key to show up with default value if not set. 
    Preferences.setDouble("Shooter Factor",driveLimiter);
  }
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ShooterDriveWithJoystick());

  }

  public void drive(double speed) {
    this.m_shooterMotor.set(speed);
    /*
    //m_leftMotor.set(ControlMode.Velocity,speed*targetRPMS);
    //m_rightMotor.set(ControlMode.Velocity,-speed*targetRPMS);
    sb.append("left speed:");
    sb.append(m_leftMotor.getSelectedSensorVelocity(0));
    if(false){
      System.out.println(sb.toString());
    }
    sb.setLength(0);/**/
  }

  private void initTalon(TalonSRX tal,boolean sensorPhase){
    tal.configFactoryDefault();
   
     /* Config sensor used for primary PID [Velocity] */
     tal.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                                           Constants.kPIDLoopIdx, 
                                           Constants.kTimeoutMs);
     /**
      * Phase sensor accordingly. 
      * Positive Sensor Reading should match Green (blinking) Leds on Talon
      */

    tal.setSensorPhase(sensorPhase);  
   /* Config the peak and nominal outputs */
   tal.configNominalOutputForward(0, Constants.kTimeoutMs);
   tal.configNominalOutputReverse(0, Constants.kTimeoutMs);
   tal.configPeakOutputForward(1, Constants.kTimeoutMs);
   tal.configPeakOutputReverse(-1, Constants.kTimeoutMs);

   /* Config the Velocity closed loop gains in slot0 */
   tal.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
   tal.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
   tal.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
   tal.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);
 }
}
