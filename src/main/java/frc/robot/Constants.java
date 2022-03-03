package frc.robot;
//Control file for all addresses and parameters for program.

public class Constants {
  //DriveTrain Addresses on CAN bus
  public static final int FrontLeftVictor = 4;
  public static final int FrontRightVictor = 12;
  public static final int RearLeftVictor = 3;
  public static final int RearRightVictor = 22;

  //Shooter addresses CAN bus
  public static final int Shooter = 20;
  public static final int IntakeCanAddress = 2;

  // public static final int ShooterRight = 24;

  // Intake /Conveyor/ Shooter addresses (PWM)
  //public static final int ConveyorSP = 1;
  //public static final int ShooterTargeterSP = 2;
  //public static final int climberSP = 3;
  
  //Encoder addresses not needed because of using hardware in the loop
  //public static final int ShooterTargeterEn = 
  public static final int ShooterCameras = 0;
  public static final int IntakeCamera = 1;

  //GameController Addresses
  public static final int LeftTankJoy = 0;
  public static final int RightTankJoy = 1;

  public static final int ShooterControllerPort = 2;

  public static final int buttonA = 1;
  public static final int buttonB = 2;
  public static final int buttonX = 3;
  public static final int buttonY = 4;
  public static final int bumperL = 5;
  public static final int bumperR = 6;
  
  //addresses based on gamepad
  public static final int StrafeXaxis = 0;
  public static final int StrafeYaxis = 1;
  public static final int RotateZaxis = 4;

  public static final int ShooterAxis = 1;

  public static final int IntakeAxis = 4;
  public static final int ClimberAxis = 5;

  public static final int IntakeButton = 0;
  public static final int ClimberButton = 1;
  
  public static final int kSlotIdx = 0;
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public final static Gains kGains_Velocit = new Gains(0.25, 0.001, 20, 1023.0/7200, 300, 1.00);
  
}
