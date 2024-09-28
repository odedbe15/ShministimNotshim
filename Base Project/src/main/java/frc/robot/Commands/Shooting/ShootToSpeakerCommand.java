package frc.robot.Commands.Shooting;

import static frc.robot.Constants.ShootingConstants.SPEAKER_VELOCITY;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ShootingSubsystem;

public class ShootToSpeakerCommand extends Command {

    ShootingSubsystem shootingSubsystem;

    public ShootToSpeakerCommand(ShootingSubsystem shootingSubsystem){
        shootingSubsystem = this.shootingSubsystem;
        addRequirements(shootingSubsystem);

    }

    @Override
    public void initialize() {
        shootingSubsystem.shoot(SPEAKER_VELOCITY);
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        shootingSubsystem.stopMotor();
    }

    @Override
    public boolean isFinished() {
         return false;
    }
    
}
