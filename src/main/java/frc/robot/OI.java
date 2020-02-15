/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.RunConveyor;
import frc.robot.commands.RunIntake;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private final Joystick m_driverStick = new Joystick(Constants.DriverControllerPort);
  private final Joystick m_rightStick = new Joystick(1);
  private final Joystick m_gamepad = new Joystick(Constants.ShooterControllerPort);

  /**
   * Construct the OI and all of the buttons on it.
   */
  public OI() {
    // Create some buttons
    final JoystickButton dpadUp = new JoystickButton(m_gamepad, Constants.RunConveyorIn);
    final JoystickButton dpadRight = new JoystickButton(m_gamepad, Constants.RunConveyorOut );
    final JoystickButton dpadDown = new JoystickButton(m_gamepad, Constants.RunIntakeIn);
    final JoystickButton dpadLeft = new JoystickButton(m_gamepad, Constants.RunIntakeOut);

    dpadUp.whileHeld( new RunConveyor(1.0) );
    dpadRight.whileHeld( new RunConveyor(-1.0) );
    dpadDown.whileHeld( new RunIntake(1.0) );
    dpadLeft.whileHeld( new RunIntake(-1.0) );
    /*final JoystickButton l2 = new JoystickButton(m_gamepad, 9);
    final JoystickButton r2 = new JoystickButton(m_gamepad, 10);
    final JoystickButton l1 = new JoystickButton(m_gamepad, 11);
    final JoystickButton r1 = new JoystickButton(m_gamepad, 12);
    /* */
  }

  public Joystick getLeftJoystick() {
    return m_driverStick;
  }
  public Joystick getRightJoystick() {
    return m_rightStick;
  }
  public Joystick getGamepad() {
    return m_gamepad;
  }
}
