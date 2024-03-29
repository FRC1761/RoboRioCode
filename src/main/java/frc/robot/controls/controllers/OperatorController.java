package frc.robot.controls.controllers;

public class OperatorController extends FilteredController {

  public OperatorController(int port) {
    super(port, false, false);
  }

  public OperatorController(int port, boolean useDeadband, boolean useSquaredInput) {
    super(port, useDeadband, useSquaredInput);
  }

  // Axis
  private final double k_triggerActivationThreshold = 0.5;

  public boolean getWantsTriggerSomething1() {
    return this.getFilteredAxis(3) > k_triggerActivationThreshold;
  }

  public boolean getWantsTriggerSomething2() {
    return this.getFilteredAxis(2) > k_triggerActivationThreshold;
  }

  // Buttons
  /* 
  public boolean getWantsBrakeMode() {
    return this.getRawButton(1);
  }

  public boolean getWantsCoastMode() {
    return this.getRawButton(2);
  }

  public boolean getWantsMoreSpeed() {
    return this.getRawButtonPressed(6);
  }

  public boolean getWantsLessSpeed() {
    return this.getRawButtonPressed(5);
  }
/* */
  public boolean getWantsShooterStop() {
    return this.getRawButtonPressed(4);
  }

  // D pad

  public boolean getWantsClimberRelease() {
    return this.getHatDown();
  }

  public boolean getWantsClimberTiltRight() {
    return this.getHatRight();
  }

  public boolean getWantsClimberClimb() {
    return this.getHatUp();
  }

  public boolean getWantsClimberTiltLeft() {
    return this.getHatLeft();
  }

  public boolean getWantsFullIntake() {
    return this.getRawButton(1);
  }

  public boolean getWantsIntake() {
    return this.getRawButton(8);
  }

  public boolean getWantsEject() {
    return this.getRawButton(2);
  }

  public boolean getWantsSource() {
    return this.getRawButton(7);
  }

  public boolean getWantsStow() {
    return this.getRawButton(4);
  }

}
