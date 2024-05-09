// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

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

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 3;
    public static final int kRearLeftDrivingCanId = 2;
    public static final int kFrontRightDrivingCanId = 4;
    public static final int kRearRightDrivingCanId = 22;

    public static final boolean kGyroReversed = true;    
  }
  public static final class ShooterConstants{
    public static final int kLeftShooterCanId = 12; 
    public static final int kRightShooterCanId = 20; 

    
    public static final int PCMaddress = 1;
    public static PneumaticsModuleType pcmType = PneumaticsModuleType.CTREPCM;
    public static final int kSolenoid1 = 0;
    public static final int kSolenoid2 = 1;
    public static final double defaultOutput = .8;  //default max power to shooter
  }

  public static final class LEDConstants{
    public static final int RedIOPort   = 0;
    public static final int GreenIOPort = 1;
    public static final int BlueIOPort  = 2;
  }


  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
    public static final int kTestControllerPort = 3;

    public static final int kFireButton = 2;
    public static final int kReleaseButton = 4;
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
