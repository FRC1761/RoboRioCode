package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants.IntakeConstants;
/*
 * Intake Subsystem modified from 
 * https://github.com/CranberryAlarm/CA24_RobotCode
 */
public class Intake extends SubsystemBase {
  //Our Neo could move intake arm at .06 so start there 
  //.12 was Cranberry's start point
  private static final double k_pivotMotorP = 0.06;
  private static final double k_pivotMotorI = 0.0;
  private static final double k_pivotMotorD = 0.001; 

  private final SparkPIDController mPivotPID;
  //private final PIDController m_pivotPID = new PIDController(k_pivotMotorP, k_pivotMotorI, k_pivotMotorD);

  //private final DutyCycleEncoder m_pivotEncoder = new DutyCycleEncoder(IntakeConstants.kArmPivotEncoderId);
  private final AbsoluteEncoder m_pivotEncoder;

  private final DigitalInput m_IntakeLimitSwitch = new DigitalInput(IntakeConstants.kIntakeLimitSwitchId);

  /*-------------------------------- Private instance variables ---------------------------------*/
  private static Intake mInstance;
  private PeriodicIO m_periodicIO;

  public static Intake getInstance() {
    if (mInstance == null) {
      mInstance = new Intake();
    }
    return mInstance;
  }

  private TalonSRX mIntakeMotor;
  private CANSparkMax mPivotMotor;

  private Intake() {
    super("Intake");

    mIntakeMotor = new TalonSRX(IntakeConstants.kIntakeCanId);
    mIntakeMotor.configFactoryDefault();
    mIntakeMotor.setNeutralMode(NeutralMode.Coast);

    mPivotMotor = new CANSparkMax(IntakeConstants.kArmPivotCanId, MotorType.kBrushless);
    mPivotMotor.restoreFactoryDefaults();
    mPivotMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    mPivotMotor.setSmartCurrentLimit(10);

    mPivotPID = mPivotMotor.getPIDController();
    mPivotPID.setP(k_pivotMotorP);
    mPivotPID.setI(k_pivotMotorI);
    mPivotPID.setD(k_pivotMotorD);
    mPivotPID.setFeedbackDevice(mPivotMotor.getAbsoluteEncoder());
    m_pivotEncoder = mPivotMotor.getAbsoluteEncoder(Type.kDutyCycle);

    m_periodicIO = new PeriodicIO();
  }

  private static class PeriodicIO {
    // Input: Desired state
    PivotTarget pivot_target = PivotTarget.STOW;
    IntakeState intake_state = IntakeState.NONE;

    // Output: Motor set values
    double intake_pivot_voltage = 0.0;
    double intake_speed = 0.0;
  }

  public enum PivotTarget {
    NONE,
    GROUND,
    SOURCE,
    AMP,
    STOW
  }

  public enum IntakeState {
    NONE,
    INTAKE,
    EJECT,
    PULSE,
    FEED_SHOOTER,
  }

  /*-------------------------------- Generic Subsystem Functions --------------------------------*/

  @Override
  public void periodic() {
    checkAutoTasks();

    // Pivot control
    double pivot_angle = pivotTargetToAngle(m_periodicIO.pivot_target);
    mPivotPID.setReference(pivot_angle,ControlType.kPosition);
//mPivotPID.
    // If the pivot is at exactly 0.0, it's probably not connected, so disable it
    if (m_pivotEncoder.getPosition() == 0.0) {
      //TODO need to learn how to stop PID controller in this case
      //I think this should work 
      mPivotPID.setReference(0.0,ControlType.kPosition);
    }

    // Intake control
    m_periodicIO.intake_speed = intakeStateToSpeed(m_periodicIO.intake_state);
    SmartDashboard.putString("State", m_periodicIO.intake_state.toString());
    writePeriodicOutputs();
    outputTelemetry();
  } //end periodic

  public void writePeriodicOutputs() {
    mPivotPID.setReference(m_periodicIO.intake_pivot_voltage,CANSparkMax.ControlType.kVoltage);
    //mPivotMotor.setVoltage(m_periodicIO.intake_pivot_voltage);
    mIntakeMotor.set(TalonSRXControlMode.PercentOutput,m_periodicIO.intake_speed);
  }

  public void stop() {
    m_periodicIO.intake_pivot_voltage = 0.0;
    m_periodicIO.intake_speed = 0.0;
  }

  public void outputTelemetry() {
    SmartDashboard.putNumber("Speed", intakeStateToSpeed(m_periodicIO.intake_state));
    SmartDashboard.putNumber("Pivot/Abs Enc (getPosition)", m_pivotEncoder.getPosition());
    SmartDashboard.putNumber("Pivot/Abs Enc (getPivotAngle)", getPivotAngle());
    SmartDashboard.putNumber("Pivot/Setpoint", pivotTargetToAngle(m_periodicIO.pivot_target));

    SmartDashboard.putNumber("Pivot/Power", m_periodicIO.intake_pivot_voltage);
    SmartDashboard.putNumber("Pivot/Current", mPivotMotor.getOutputCurrent());

    SmartDashboard.putBoolean("Limit Switch", getIntakeHasNote());
  }

  public void reset() {
  }

  public double pivotTargetToAngle(PivotTarget target) {
    switch (target) {
      case GROUND:
        return IntakeConstants.k_pivotAngleGround;
      case SOURCE:
        return IntakeConstants.k_pivotAngleSource;
      case AMP:
        return IntakeConstants.k_pivotAngleAmp;
      case STOW:
        return IntakeConstants.k_pivotAngleStow;
      default:
        // "Safe" default
        return 180;
    }
  }

  public double intakeStateToSpeed(IntakeState state) {
    switch (state) {
      case INTAKE:
        return IntakeConstants.k_intakeSpeed;
      case EJECT:
        return IntakeConstants.k_ejectSpeed;
      case PULSE:
        // Use the timer to pulse the intake on for a 1/16 second,
        // then off for a 15/16 second
        if (Timer.getFPGATimestamp() % 1.0 < (1.0 / 45.0)) {
          return IntakeConstants.k_intakeSpeed;
        }
        return 0.0;
      case FEED_SHOOTER:
        return IntakeConstants.k_feedShooterSpeed;
      default:
        // "Safe" default
        return 0.0;
    }
  }

  /*---------------------------------- Custom Public Functions ----------------------------------*/

  public IntakeState getIntakeState() {
    return m_periodicIO.intake_state;
  }

  public double getPivotAngle() {
    //getPosition should return rotations just like 
    //former code DutyCycleEncoder.getAbsolutePosition.  
    double value = m_pivotEncoder.getPosition() -
        IntakeConstants.k_pivotEncoderOffset + 0.5; /*TODO why do they add .5 here?
         Did they mount the coder upside down? */

    return value;
  }

  public boolean getIntakeHasNote() {
    // NOTE: this is intentionally inverted, because the limit switch is normally
    // closed
    return !m_IntakeLimitSwitch.get();
  }

  // Pivot helper functions
  public void goToGround() {
    m_periodicIO.pivot_target = PivotTarget.GROUND;
    m_periodicIO.intake_state = IntakeState.INTAKE;
    //m_leds.setColor(Color.kYellow);
  }

  public void goToSource() {
    m_periodicIO.pivot_target = PivotTarget.SOURCE;
    m_periodicIO.intake_state = IntakeState.NONE;
  }

  public void goToAmp() {
    m_periodicIO.pivot_target = PivotTarget.SOURCE;
    m_periodicIO.intake_state = IntakeState.NONE;
  }

  public void goToStow() {
    m_periodicIO.pivot_target = PivotTarget.STOW;
    m_periodicIO.intake_state = IntakeState.NONE;
  }

  // Intake helper functions
  public void intake() {
    m_periodicIO.intake_state = IntakeState.INTAKE;
  }

  public void eject() {
    m_periodicIO.intake_state = IntakeState.EJECT;
  }

  public void pulse() {
    m_periodicIO.intake_state = IntakeState.PULSE;
  }

  public void feedShooter() {
    m_periodicIO.intake_state = IntakeState.FEED_SHOOTER;
  }

  public void stopIntake() {
    m_periodicIO.intake_state = IntakeState.NONE;
    m_periodicIO.intake_speed = 0.0;
  }

  public void setState(IntakeState state) {
    m_periodicIO.intake_state = state;
  }

  public void setPivotTarget(PivotTarget target) {
    m_periodicIO.pivot_target = target;
  }

  /*---------------------------------- Custom Private Functions ---------------------------------*/
  private void checkAutoTasks() {
    // If the intake is set to GROUND, and the intake has a note, and the pivot is
    // close to it's target
    // Stop the intake and go to the SOURCE position
    if (m_periodicIO.pivot_target == PivotTarget.GROUND && getIntakeHasNote() && isPivotAtTarget()) {
      m_periodicIO.pivot_target = PivotTarget.STOW;
      m_periodicIO.intake_state = IntakeState.NONE;
      //m_leds.setColor(Color.kGreen);
    }
  }

  private boolean isPivotAtTarget() {
    //TODO need to recheck tolerance here. It was < 5 degrees (we use rotations)
    return Math.abs(getPivotAngle() - pivotTargetToAngle(m_periodicIO.pivot_target)) < 5.0/360;
  }
}