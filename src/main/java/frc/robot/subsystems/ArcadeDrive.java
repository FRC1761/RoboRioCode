// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArcadeDrive extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  WPI_TalonSRX m_frontLeft = new WPI_TalonSRX(Constants.frontLeftTalon);
  WPI_TalonSRX m_frontRight = new WPI_TalonSRX(Constants.frontRightTalon);
  WPI_TalonSRX m_rearLeft = new WPI_TalonSRX(Constants.rearLeftTalon);
  WPI_TalonSRX m_rearRight = new WPI_TalonSRX(Constants.rearRightTalon);

  XboxController driverController = new XboxController(Constants.xboxPort);

  private MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_rearLeft);
  private MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_rearRight);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

  private double driveLimiter;

  public ArcadeDrive() {
    
    super();
    //get key value or use default 1.0;
    driveLimiter = Preferences.getDouble("DriveTrain Factor", 1.0);
    //Push value back to Preferences widget so it forces
    //correct key to show up with default value if not set.
    Preferences.setDouble("DriveTrain Factor",driveLimiter);
    m_drive.setSafetyEnabled(false);
  }
  
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    m_drive.arcadeDrive(driverController.getLeftX()*driveLimiter,
                        driverController.getRightY()*driveLimiter);
  }

  public void autoDrive(double speed, double rotation) {
    m_drive.arcadeDrive(speed, rotation);
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
