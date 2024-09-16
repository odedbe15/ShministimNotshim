package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.IntakeRollerSubsystem;

public class IntakeRollerCommand extends Command{
    private double speed = 0.2;
    private IntakeRollerSubsystem intakeRollerSubsystem;
    public IntakeRollerCommand(boolean isIn, IntakeRollerSubsystem intakeRollerSubsystem) {
        if(isIn){
            speed=-speed;
        }
        this.intakeRollerSubsystem = intakeRollerSubsystem;

        addRequirements(intakeRollerSubsystem);
    }
    public void initialize(){
        intakeRollerSubsystem.setMotor(speed);
    }
    public void execute(){

    }
    public void end(){
        intakeRollerSubsystem.setMotor(0);
    }   
}
