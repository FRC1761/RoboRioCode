/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import frc.robot.commands.MecanumDriveWithJoystick;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors, a left and right encoder
 * and a gyro.
 */
public class DriveTrain extends Subsystem {
  
  //Settings for True RObot
  private final WPI_TalonSRX m_leftFrontMotor 
      = new WPI_TalonSRX(10);
  private final WPI_TalonSRX m_rightFrontMotor 
      = new WPI_TalonSRX(13);
  private final WPI_TalonSRX m_leftRearMotor 
      = new WPI_TalonSRX(8);
  private final WPI_TalonSRX m_rightRearMotor 
      = new WPI_TalonSRX(11);

   private final MecanumDrive m_drive
      = new MecanumDrive(m_leftFrontMotor,m_rightFrontMotor,m_leftRearMotor,m_rightRearMotor);
  private double driveLimiter;
 
  /**
   * Create a new drive train subsystem.(constructor function)
   */
  public DriveTrain() {
    super();
    //get key value or use default 1.0;
    driveLimiter = Preferences.getInstance().getDouble("DriveTrain Factor", 1.0);
    //Push value back to Preferences widget so it forces
    //correct key to show up with default value if not set. 
    Preferences.getInstance().putDouble("DriveTrain Factor",driveLimiter);
    m_drive.setSafetyEnabled(false);
  }

  /**
   * When no other command is running let the operator drive around using the
   * PS3 joystick.
   */
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new MecanumDriveWithJoystick());
  }

  /**
   * The log method puts interesting information to the SmartDashboard.
   */
  public void log() {
    
  }
  
  /**
   * Mechanum Drive system
   * left controls the speed in the x-direction
   * right controls the speed in the y-direction
   * rotation controls speed in the clockwise direction (+ CW, - CCW)
   * @param right Speed in range [-1,1]
   */
  public void drive(double left, double right,double rotation) {
    m_drive.driveCartesian(left, right, rotation);
  }
    //m_drive.drivePolar(left, right, rotation);
  /**
   * Tank style driving for the DriveTrain.
   *
   * @param joy The ps3 style joystick to use to drive tank style.
   */
  public void drive(Joystick joy) {
    m_drive.driveCartesian(joy.getRawAxis(1),joy.getRawAxis(0),joy.getRawAxis(4));
  }
}
