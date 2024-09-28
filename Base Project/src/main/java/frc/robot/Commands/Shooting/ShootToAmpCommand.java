package frc.robot.Commands.Shooting;

import static frc.robot.Constants.ShootingConstants.AMP_VELOCITY;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ShootingSubsystem;

public class ShootToAmpCommand extends Command {
    ShootingSubsystem shootingSubsystem;

    public ShootToAmpCommand(ShootingSubsystem shootingSubsystem){
        shootingSubsystem = this.shootingSubsystem;
        addRequirements(shootingSubsystem);

    }

    @Override
    public void initialize() {
        shootingSubsystem.shoot(AMP_VELOCITY);
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
