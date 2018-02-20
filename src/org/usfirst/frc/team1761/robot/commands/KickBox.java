package org.usfirst.frc.team1761.robot.commands;

import org.usfirst.frc.team1761.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class KickBox extends Command {

    public KickBox() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.m_fourbar);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//arms must be retracted
    	Robot.m_fourbar.retractArms();
    	//keep arms retracted while we fire kicker
    	Robot.m_fourbar.kick();
    	//wait about .5 seconds
    	Timer.delay(.5);
    	//release arms
    	Robot.m_fourbar.extendArms();    	
    	//retract kicker
    	Robot.m_fourbar.windup();
       	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
