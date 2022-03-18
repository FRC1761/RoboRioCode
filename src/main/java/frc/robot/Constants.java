package frc.robot;
//Control file for all addresses and parameters for program.

public class Constants {
  //DriveTrain Addresses on CAN bus
  public static final int FrontLeftVictor = 4;
  public static final int FrontRightVictor = 12;
  public static final int RearLeftVictor = 3;
  public static final int RearRightVictor = 22;

  //Shooter addresses CAN bus
  public static final int Shooter = 2;
  public static final int IntakeCanAddress = 20;
  public static final int IntakeArmAddress = 8;

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

  public static final int StrafeXaxis = 0;
  public static final int StrafeYaxis = 1;
  public static final int RotateZaxis = 4;

  public static final int buttonA = 1; //! uh.. double check this please
  public static final int buttonB = 2;
  public static final int buttonX = 3;
  public static final int buttonY = 4;
  public static final int bumperL = 5;
  public static final int bumperR = 6;

  //addresses based on gamepad
  // XBOX Controller Addresses (a comprhensive list of all buttons and axes)
  /**
  //* BUTTONS
   * 0 = A
   * 1 = B
   * 2 = X
   * 3 = Y
   * 4 = LB
   * 5 = RB
   * 6 = Back
   * 7 = Start
   * 8 = Left Stick
   * 9 = Right Stick
   * 10 = Left Trigger
   * 11 = Right Trigger
   *
   */
   //* AXES
   /*
   * 0 = Left Stick, X Axis
   * 1 = Left Stick, Y Axis
   * 2 = Left Trigger
   * 3 = Right Trigger
   * 4 = Right Stick, X Axis
   * 5 = Right Stick, Y Axis
   * 6 = PROBABLY POV Left
   * 7 = PROBABLY POV Right
   */
  // axises... axi? axes? axis?
  //TODO: we should rename these to be more descriptive (more layman's terms rather than Strafe/Intake/Shooter/etc)



  public static final int ShooterAxis = 1;

  public static final int IntakeAxis = 5; //! we dont need you here, intake axis. (we dont use an axis to control this)

  public static final int IntakeArmAxis = 4;
//  public static final int ClimberAxis = 5; //heh.


// BUTTONS! I like buttons.

  public static final int IntakeButton = 0; //A
  public static final int ClimberButton = 1; //B

  public static final int kSlotIdx = 0;
  public static final int kPIDLoopIdx = 0;
  public static final int kTimeoutMs = 30;
  public final static Gains kGains_Velocit = new Gains(0.25, 0.001, 20, 1023.0/7200, 300, 1.00);

}
