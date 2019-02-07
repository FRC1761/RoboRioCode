/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.TimedLowerWrist;
import frc.robot.commands.TimedLiftWrist;
import frc.robot.commands.TakeInCargo;
import frc.robot.commands.TakeOutCargo;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private final Joystick m_leftStick = new Joystick(0);
  private final Joystick m_rightStick = new Joystick(1);
  private final Joystick m_gamepad = new Joystick(3);

  /**
   * Construct the OI and all of the buttons on it.
   */
  public OI() {
    // Put Some buttons on the SmartDashboard
    /*SmartDashboard.putData("Elevator Bottom", new SetElevatorSetpoint(0));
    SmartDashboard.putData("Elevator Platform", new SetElevatorSetpoint(0.2));
    SmartDashboard.putData("Elevator Top", new SetElevatorSetpoint(0.3));

    SmartDashboard.putData("Wrist Horizontal", new SetWristSetpoint(0));
    SmartDashboard.putData("Raise Wrist", new SetWristSetpoint(-45));

    SmartDashboard.putData("Open Claw", new OpenClaw());
    SmartDashboard.putData("Close Claw", new CloseClaw());

    SmartDashboard.putData("Deliver Soda", new Autonomous());
    /**/
    // Create some buttons
    final JoystickButton dpadUp = new JoystickButton(m_gamepad, 3);
    final JoystickButton dpadRight = new JoystickButton(m_gamepad, 1);
    final JoystickButton dpadDown = new JoystickButton(m_gamepad, 4);
    final JoystickButton dpadLeft = new JoystickButton(m_gamepad, 2);
    /*final JoystickButton l2 = new JoystickButton(m_gamepad, 9);
    final JoystickButton r2 = new JoystickButton(m_gamepad, 10);
    final JoystickButton l1 = new JoystickButton(m_gamepad, 11);
    final JoystickButton r1 = new JoystickButton(m_gamepad, 12);
    /* */
    // Connect the buttons to commands
    /*dpadUp.whenPressed(new SetElevatorSetpoint(0.2));
    dpadDown.whenPressed(new SetElevatorSetpoint(-0.2));
    */
    dpadRight.whenPressed(new TimedLowerWrist());
    dpadLeft.whenPressed(new TimedLiftWrist());

    dpadUp.whileHeld(new TakeInCargo());
    dpadDown.whileHeld(new TakeOutCargo());

    /* */
    /*r1.whenPressed(new PrepareToPickup());
    r2.whenPressed(new Pickup());
    l1.whenPressed(new Place());
    l2.whenPressed(new Autonomous());
    */
  }

  public Joystick getLeftJoystick() {
    return m_leftStick;
  }
  public Joystick getRightJoystick() {
    return m_rightStick;
  }
  public Joystick getGamepad() {
    return m_gamepad;
  }
}
