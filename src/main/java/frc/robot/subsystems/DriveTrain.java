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
      //private final Encoder m_leftEncoder = new Encoder(1, 2);
  //private final Encoder m_rightEncoder = new Encoder(3, 4);
  //private final AnalogInput m_rangefinder = new AnalogInput(6);
  //private final AnalogGyro m_gyro = new AnalogGyro(1);

  /**
   * Create a new drive train subsystem.
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
   * Tank style driving for the DriveTrain.
   *
   * @param left Speed in range [-1,1]
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

  /**
   * Get the robot's heading.
   *
   * @return The robots heading in degrees.
   */
  /*public double getHeading() {
    return m_gyro.getAngle();
  }*/

  /**
   * Reset the robots sensors to the zero states.
   */
  public void reset() {
    /*m_gyro.reset();
    m_leftEncoder.reset();
    m_rightEncoder.reset();
    */
  }

  /**
   * Get the average distance of the encoders since the last reset.
   *
   * @return The distance driven (average of left and right encoders).
   */
/*  public double getDistance() {
    return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2;
  }*/

  /**
   * Get the distance to the obstacle.
   *
   * @return The distance to the obstacle detected by the rangefinder.
   */
  /*public double getDistanceToObstacle() {
    // Really meters in simulation since it's a rangefinder...
    return m_rangefinder.getAverageVoltage();
  }*/
}
