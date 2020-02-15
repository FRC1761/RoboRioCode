package frc.robot;
//Control file for all addresses and parameters for program.

public class Constants {
  //DriveTrain Addresses on CAN bus
  public static final int FrontLeftVictor = 1;
  public static final int FrontRightVictor = 2;
  public static final int RearLeftVictor = 3;
  public static final int RearRightVictor = 4;

  //Shotoer addresses CAN bus
  public static final int ShooterLeft = 11;
  public static final int ShooterRight = 24;

  // Intake /Conveyor/ Shooter addresses (PWM)
  public static final int IntakeSP = 0;
  public static final int ConveyorSP = 1;
  public static final int ShooterTargeterSP = 2;
  public static final int climberSP = 3;
  
  //Encoder addresses not needed because of using hardware in the loop
  //public static final int ShooterTargeterEn = 
  public static final int ShooterCameras = 0;
  public static final int IntakeCamera = 1;

  //GameController Addresses
  public static final int DriverControllerPort = 0;
  public static final int ShooterControllerPort = 2;

  public static final int RunConveyorIn = 1;
  public static final int RunConveyorOut= 2;
  public static final int RunIntakeIn = 3;
  public static final int RunIntakeOut= 4;
  
  //addresses based on gamepad
  public static final int StrafeXaxis = 0;
  public static final int StrafeYaxis = 1;
  public static final int RotateZaxis = 4;

  public static final int ShooterAxis = 1;


  
}
