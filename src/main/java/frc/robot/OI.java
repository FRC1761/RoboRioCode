/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.IntakeWithButton;
import frc.robot.commands.ReverseIntakeWithButton;
import frc.robot.commands.AdjustAlignment;
import frc.robot.subsystems.Intake;
/*import frc.robot.commands.RunAngler;
import frc.robot.commands.RunConveyor;
import frc.robot.commands.RunIntake;
/**/
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private final Joystick m_leftTank = new Joystick(Constants.LeftTankJoy);
  private final Joystick m_rightTank = new Joystick(Constants.RightTankJoy);

  private final Joystick m_gamepad = new Joystick(Constants.ShooterControllerPort);

  /**
   * Construct the OI and all of the buttons on it.
   */
  public OI() {
    // Create some buttons
    final JoystickButton buttonA = new JoystickButton(m_gamepad, Constants.buttonA);
    final JoystickButton buttonB = new JoystickButton(m_gamepad, Constants.buttonB);
    final JoystickButton buttonX = new JoystickButton(m_gamepad, Constants.buttonX);
    final JoystickButton buttonY = new JoystickButton(m_gamepad, Constants.buttonY);
    final JoystickButton bumperL = new JoystickButton(m_gamepad, Constants.bumperL);
    final JoystickButton bumperR = new JoystickButton(m_gamepad, Constants.bumperR);


    buttonA.whileHeld( new IntakeWithButton() );
    buttonB.whileHeld( new ReverseIntakeWithButton() );
    buttonX.whileHeld( new AdjustAlignment(1) );
      //buttonY.whileHeld( new IntakePivotButton() );
//    bumperL.whileHeld( new RunAngler(-1.0) );
//    bumperR.whileHeld( new RunAngler(1.0) );
    /*final JoystickButton l2 = new JoystickButton(m_gamepad, 9);
    final JoystickButton r2 = new JoystickButton(m_gamepad, 10);
    final JoystickButton l1 = new JoystickButton(m_gamepad, 11);
    final JoystickButton r1 = new JoystickButton(m_gamepad, 12);
    /* */
  }

  public Joystick getLeftJoystick() {
    return m_leftTank;
  }
  public Joystick getRightJoystick() {
    return m_rightTank;
  }
  public Joystick getGamepad() {
    return m_gamepad;
  }
}
