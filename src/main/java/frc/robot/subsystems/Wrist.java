/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.AnalogTrigger;


/**
 * The claw subsystem is a simple system with a motor for opening and closing.
 * If using stronger motors, you should probably use a sensor so that the motors
 * don't stall.
 */
public class Wrist extends Subsystem {
  //spark is on PWM 0
  private final Spark m_motor = new Spark(0);
  //hall effect sensor should go to DIO port 0
  //private final DigitalInput m_hallEffectSensor = new Counter(0);
  //There are two limit switches on this Spark that are hardwired
  // so no programming is necessary if they are wired correctly
  private Counter hallSensorCounter;
  private AnalogTrigger hallSensor;
  
  /**
   * Create a new claw subsystem.
   */
  public Wrist() {
    super();
    hallSensor = new AnalogTrigger(0);
    hallSensor.setLimitsVoltage(3.6, 4.9);
    hallSensorCounter = new Counter(hallSensor);
    hallSensorCounter.setUpDownCounterMode();
    hallSensorCounter.setDistancePerPulse(2);
    hallSensorCounter.setMaxPeriod(10);

    // Let's name everything on the LiveWindow
    //addChild("Motor", m_motor);
    //addChild("Limit Switch", m_contact);
  }

  @Override
  public void initDefaultCommand() {
  }

  public void log() {
    System.out.println("The Hall Encoder count is:" + hallSensorCounter.get());
  }

  /**
   * Set the claw motor to move in the open direction.
   */
  public void open() {
    m_motor.set(-1);
  }

  /**
   * Set the claw motor to move in the close direction.
   */
  @Override
  public void close() {
    m_motor.set(1);
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