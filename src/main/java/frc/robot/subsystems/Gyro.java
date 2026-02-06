// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 *    This class is intended to abstract the  
 *
 */
package frc.robot.subsystems;
//Analog Devices description
//https://wiki.analog.com/first/adis16470_imu_frc
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
//NavX add On Board gyro to RoboRio
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
//Gyro given during 2017 Kit of Parts
//https://wiki.analog.com/first/adxrs450_gyro_board_frc
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/** Add your docs here. */
public class Gyro {
    AHRS gyro1;
    ADIS16470_IMU gyro2;
    ADXRS450_Gyro gyro3;
    

}
