package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Solenoid extends SubsystemBase {

    XboxController driverController = new XboxController(Constants.xboxPort);
    public boolean leftBumper = driverController.getLeftBumper();
    public boolean rightBumper = driverController.getRightBumper();

    private final DoubleSolenoid left_doubSol =
    new DoubleSolenoid(Constants.left_dS, PneumaticsModuleType.REVPH, 4, 5);
    private final DoubleSolenoid right_doubSol =
    new DoubleSolenoid(Constants.right_dS, PneumaticsModuleType.REVPH, 4, 5);

    public Solenoid() {
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
  
    @Override
    public void periodic() {
      if (leftBumper || rightBumper) {
        left_doubSol.set(Value.kForward);
        right_doubSol.set(Value.kForward);
      }
      else {
        left_doubSol.set(Value.kReverse);
        right_doubSol.set(Value.kReverse);
      }
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }

}

