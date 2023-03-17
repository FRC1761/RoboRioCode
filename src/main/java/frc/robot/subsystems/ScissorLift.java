package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ScissorLift extends SubsystemBase {
    // CANSparkMax elevatorMax = new CANSparkMax(Constants.elevatorMax, MotorType.kBrushless);
    CANSparkMax elevatorSpark = new CANSparkMax(Constants.elevatorSpark, MotorType.kBrushless);
    XboxController auxController = new XboxController(Constants.xboxPort2);

    public double driveLimiter;
    


    public ScissorLift() {
        super();
        //get key value or use default 1.0;
        driveLimiter = Preferences.getDouble("ScissorLift Factor", 1.0);
        //Push value back to Preferences widget so it forces
        //correct key to show up with default value if not set.
        Preferences.setDouble("ScissorLift Factor",driveLimiter);
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
        
        ; // XOR gate
        
        elevatorSpark.set(auxController.getRightY()*driveLimiter);
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }

}


