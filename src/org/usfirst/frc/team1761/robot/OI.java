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
//import org.usfirst.frc.team1761.robot.commands.Autonomous;
/*import org.usfirst.frc.team1761.robot.commands.CloseClaw;
import org.usfirst.frc.team1761.robot.commands.OpenClaw;
import org.usfirst.frc.team1761.robot.commands.Pickup;
import org.usfirst.frc.team1761.robot.commands.Place;
import org.usfirst.frc.team1761.robot.commands.PrepareToPickup;
import org.usfirst.frc.team1761.robot.commands.SetElevatorSetpoint;
import org.usfirst.frc.team1761.robot.commands.SetWristSetpoint;
*/
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
		SmartDashboard.putData("Elevator Platform", new SetElevatorSetpoint(0.2));
		SmartDashboard.putData("Elevator Top", new SetElevatorSetpoint(0.3));
		SmartDashboard.putData("Wrist Horizontal", new SetWristSetpoint(0));
		SmartDashboard.putData("Raise Wrist", new SetWristSetpoint(-45));
		SmartDashboard.putData("Open Claw", new OpenClaw());
		SmartDashboard.putData("Close Claw", new CloseClaw());
		SmartDashboard.putData("Deliver Soda", new Autonomous()); /**/

		/*
		// Create some buttons
		JoystickButton dpadUp = new JoystickButton(m_joystick, 5);
		JoystickButton dpadRight = new JoystickButton(m_joystick, 6);
		JoystickButton dpadDown = new JoystickButton(m_joystick, 7);
		JoystickButton dpadLeft = new JoystickButton(m_joystick, 8);
		
		// Connect the buttons to commands
		dpadUp.whenPressed(new SetElevatorSetpoint(0.2));
		dpadDown.whenPressed(new SetElevatorSetpoint(-0.2));
		dpadRight.whenPressed(new CloseClaw());
		dpadLeft.whenPressed(new OpenClaw());

		r1.whenPressed(new PrepareToPickup());
		r2.whenPressed(new Pickup());
		l1.whenPressed(new Place());
		l2.whenPressed(new Autonomous());
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
