package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private TalonSRX shooter1,shooter2;
    private DoubleSolenoid trigger = new DoubleSolenoid(ShooterConstants.PCMaddress,
                                                        ShooterConstants.pcmType,
                                                        ShooterConstants.kSolenoid1,
                                                        ShooterConstants.kSolenoid2);
    public ShooterSubsystem() {
        shooter1 = new TalonSRX(ShooterConstants.kLeftShooterCanId);
        shooter2 = new TalonSRX(ShooterConstants.kRightShooterCanId);
        
        shooter1.setNeutralMode(NeutralMode.Coast);
        shooter2.setNeutralMode(NeutralMode.Coast);
    }

    public void drive(double speed){
        shooter1.set(TalonSRXControlMode.PercentOutput,-speed);
        shooter2.set(TalonSRXControlMode.PercentOutput,-speed);
    }

    public void fire(boolean isLoad){
        if(isLoad) trigger.set(DoubleSolenoid.Value.kForward);
        else trigger.set(DoubleSolenoid.Value.kReverse);
    }
    
}