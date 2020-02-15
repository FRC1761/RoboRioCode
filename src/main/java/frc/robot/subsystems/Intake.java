/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.VictorSP;
//import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.drive.MecanumDrive;
import frc.robot.Constants;
//import frc.robot.commands.MecanumDriveWithJoystick;

/**
 * The DriveTrain subsystem incorporates the sensors and actuators attached to
 * the robots chassis. These include four drive motors, a left and right encoder
 * and a gyro.
 */
public class Intake extends Subsystem {
  
  //Settings for True RObot
  private final VictorSP m_IntakeMotor = new VictorSP(Constants.IntakeSP);
//  private final DigitalInput m_CoInput = new Victor(Constants.ConveyorSP);

  
  private double IntakeLimiter;
 
  /**
   * Create a new drive train subsystem.(constructor function)
   */
  public Intake() {
    super();
    //get key value or use default 1.0;
    IntakeLimiter = Preferences.getInstance().getDouble("Intake Factor", 1.0);
    //Push value back to Preferences widget so it forces
    //correct key to show up with default value if not set. 
    Preferences.getInstance().putDouble("Intake Factor",IntakeLimiter);
    m_IntakeMotor.setSafetyEnabled(false);
  }

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand();
  }
 
  public void drive(double speed) {
    m_IntakeMotor.set(speed);
  }
  
}
