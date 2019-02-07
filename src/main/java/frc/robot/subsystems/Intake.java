/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 * The claw subsystem is a simple system with a motor for opening and closing.
 * If using stronger motors, you should probably use a sensor so that the motors
 * don't stall.
 */
public class Intake extends Subsystem {
  //spark is on PWM 1
  private final Spark m_motor = new Spark(1);
  private double driveFactor = Preferences.getInstance().getDouble("Intake Factor", 1.0);
  //There is one limit switch on this Spark that are hardwired
  // so no programming is necessary if they are wired correctly
  
  /**
   * Create a new claw subsystem.
   */
  public Intake() {
    super();
    // Let's name everything on the LiveWindow
    //addChild("Motor", m_motor);
    //addChild("Limit Switch", m_contact);
  }

  @Override
  public void initDefaultCommand() {
  }

  public void log() {
  }

  /**
   * Set the claw motor to move in the open direction.
   */
  public void open() {
    m_motor.set(-1*driveFactor);
  }

  /**
   * Set the claw motor to move in the close direction.
   */
  @Override
  public void close() {
    m_motor.set(1*driveFactor);
  }

  /**
   * Stops the claw motor from moving.
   */
  public void stop() {
    m_motor.set(0);
  }

  /**
   * Return true when the robot is grabbing an object hard enough to trigger
   * the limit switch.
   */
  public boolean isGrabbing() {
    return true;//return m_contact.get();
  }
}
