package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

public class ShooterSubsystem extends SubsystemBase {
        private CANSparkMax shooterLeft;
        private CANSparkMax shooterRight;
        private XboxController controller;
        
    public ShooterSubsystem() {
        shooterLeft = new CANSparkMax(DriveConstants.kLeftShooterCanId, MotorType.kBrushless);
        shooterRight = new CANSparkMax(DriveConstants.kRightShooterCanId, MotorType.kBrushless);
        shooterLeft.setInverted(false);
        shooterRight.setInverted(true);
        controller = new XboxController(0);
    }


    public void periodic() {
        this.drive((controller.getLeftBumper() == true) ? 1 : 0);
        System.out.println(controller.getLeftBumper());
    }

    public void drive(double speed){
        shooterLeft.set(speed);
        shooterRight.set(speed);
        
        System.out.println(speed);
    }

    
}