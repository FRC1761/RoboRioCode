// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

//import org.littletonrobotics.junction.AutoLog;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveTrain m_robotDrive = new DriveTrain();
  private ShooterSubsystem m_shooterDrive;

  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);
  XboxController m_operatorController = new XboxController(OIConstants.kOperatorControllerPort);
  XboxController m_TestController = new XboxController(OIConstants.kTestControllerPort);
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    m_shooterDrive = new ShooterSubsystem();
    configureButtonBindings();
    m_shooterDrive.setDefaultCommand(
      new RunCommand(()->
        m_shooterDrive.drive(m_driverController.getRightTriggerAxis())
      ,m_shooterDrive));

    // Configure default commands
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                m_driverController.getLeftY(),
                m_driverController.getRightY()),
            m_robotDrive));
/*

    /**/
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {            
    new JoystickButton(m_driverController, OIConstants.kFireButton)
     .onTrue(new RunCommand(
      () -> m_shooterDrive.fire(true),
      m_shooterDrive));
  
    new JoystickButton(m_driverController, OIConstants.kReleaseButton)
     .onTrue(new RunCommand(
       () -> m_shooterDrive.fire(false),
       m_shooterDrive));
  }
}
