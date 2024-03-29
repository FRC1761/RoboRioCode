// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 4.8;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    public static final double kDirectionSlewRate = 1.2; // radians per second
    public static final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(22.5);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(22.5);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 1;
    public static final int kRearLeftDrivingCanId = 3;
    public static final int kFrontRightDrivingCanId = 2;
    public static final int kRearRightDrivingCanId = 4;

    public static final int kFrontLeftTurningCanId = 5;
    public static final int kRearLeftTurningCanId = 7;
    public static final int kFrontRightTurningCanId = 62;
    public static final int kRearRightTurningCanId = 28;
    public static final boolean kGyroReversed = true;    
  }
  public static final class ShooterConstants{
    public static final int kLeftShooterCanId = 12; //spark driving neo
    public static final int kRightShooterCanId = 13; //spark driving neo
    
    public static final double kShooterP = 0.0005;
    public static final double kShooterI = 0.0000001;
    public static final double kShooterD = 0.0;
    public static final double kShooterFF = 0.0002;
    public static boolean isTuning = true;
  
    public static final double kShooterMinOutput = 0;
    public static final double kShooterMaxOutput = 1;
    
    public static final double kShooterOutput = .75; //percentage output for ShooterSubsystem class
    public static final double kShooterSpeed = 3500; //RPM for SCShooter (speed controlled)
    public static final double kShooterSlowSpeed = 3000; //RPM for SCShooter (speed controlled)
    
  }

  public static final class IntakeConstants {
    public static final int kArmPivotCanId = 21; //spark driving neo
    // the following encoder ID would only be used if we used roborio inputs
    //but we are currently using encoder off SparkMax ArmPivotCanId
    //public static final int kArmPivotEncoderId = 21; //spark driving neo

    public static final int kIntakeCanId = 25;
    public static final int kIntakeLimitSwitchId=3;

    public static final double k_pivotAngleGround = 0.074; //
    public static final double k_pivotAngleAmp = .472; //need an amp to test for best angle for this
    public static final double k_pivotAngleSource = k_pivotAngleAmp; //assuming same as amp for now 
    public static final double k_pivotAngleStow = .683; //279 degreesfrom encoder

    //speed for intake based on values from -1 to 1
    public static final double k_intakeSpeed = .45;
    public static final double k_pulseSpeed = 0.1;
    public static final double kEjectPowerDefault    = -.85;
    public static final double kEjectPowerAmp        = -.85;
    public static final double kEjectPowerSpeaker    = -.85;    
    public static final double k_feedShooterSpeed = -.50;

    //basing Shooter Feed 
    public static final double kPivotPercentage = .25;
    public static final double kPivotSlowPercentage = .04;

    //not sure we will use this as we used the SparkMax to set offset of encoder to 0
    // in the ground position
    public static final double k_pivotEncoderOffset= 0;
  }
  
  public static final class ClimberConstants {
      // Climber
      public static final int kClimberLeftMotorId = 6;
      public static final int kClimberRightMotorId = 7;
      public static final double kClimberClimbPower = -.8; // Power Output
      public static final double kClimberReleasePower = +.5; // Power Output

      public static final double kClimberGearRatio = 1.0 / 12.0;
/* 
      public static final double kClimberP = 0.001;
      public static final double kClimberI = 0.0;
      public static final double kClimberD = 0.0;
      public static final double kClimberMinOutput = -0.5;
/* */
      public static final double kClimberMaxOutput = 0.5;
  }

  public static final class LEDConstants{
    public static final int RedIOPort   = 0;
    public static final int GreenIOPort = 1;
    public static final int BlueIOPort  = 2;
  }

  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T, 13T, or 14T.
    // This changes the drive speed of the module (a pinion gear with more teeth will result in a
    // robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 13;

    // Invert the turning encoder, since the output shaft rotates in the opposite direction of
    // the steering motor in the MAXSwerve Module.
    public static final boolean kTurningEncoderInverted = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15 teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;

    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction; // meters
    public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction) / 60.0; // meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
    public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second

    public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
    public static final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians

    public static final double kDrivingP = 0.04;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    public static final double kTurningP = 1;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningFF = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;

    public static final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 40; // amps
    public static final int kTurningMotorCurrentLimit = 30; // amps
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
    public static final int kTestControllerPort = 3;
    
    public static final double kDriveDeadband = 0.05;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kFirstAutoDelayDefault  = 2.0;
    public static final double kSecondAutoDelayDefault = 2.0;
    public static final double kThirdAutoDelayDefault  = 2.0;
    
    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }
}
