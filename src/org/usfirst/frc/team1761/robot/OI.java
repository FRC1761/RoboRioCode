/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1761.robot;

import org.usfirst.frc.team1761.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick left_joystick  = new Joystick(0);
	private Joystick right_joystick = new Joystick(1);
	private Joystick gamepad 		= new Joystick(3);

	public OI() {
		// Put Some buttons on the SmartDashboard
		/*SmartDashboard.putData("Elevator Bottom", new SetElevatorSetpoint(0));
		 /**/

		/*
		// Create some buttons
		JoystickButton dpadUp = new JoystickButton(m_joystick, 5);
		JoystickButton dpadRight = new JoystickButton(m_joystick, 6);
		JoystickButton dpadDown = new JoystickButton(m_joystick, 7);
		JoystickButton dpadLeft = new JoystickButton(m_joystick, 8);
		/**/
		JoystickButton lBumper = new JoystickButton(gamepad, 5);
		JoystickButton rBumper = new JoystickButton(gamepad, 6);
		JoystickButton buttonA = new JoystickButton(gamepad, 1);
		JoystickButton buttonB = new JoystickButton(gamepad, 2);
		JoystickButton buttonX = new JoystickButton(gamepad, 3);
		JoystickButton buttonY = new JoystickButton(gamepad, 4);
		
		
		lBumper.whenPressed(new ExtendArms());
		rBumper.whenPressed(new RetractArms());
		buttonA.whenPressed(new Lift());
		buttonB.whenPressed(new Lower());
		buttonX.whenPressed(new KickBox());
		buttonY.whenPressed(new WindUp());
		

	}

	public Joystick getLeftJoystick() {
		return left_joystick;
	}

	public Joystick getRightJoystick() {
		return right_joystick;
	}

}
