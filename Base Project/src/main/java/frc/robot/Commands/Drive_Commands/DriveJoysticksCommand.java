package frc.robot.Commands.Drive_Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.DriveSubsystem;

import java.util.function.Supplier;

public class DriveJoysticksCommand extends Command {

    DriveSubsystem driveSubsystem;
    double speed;
    double rotation;

    public DriveJoysticksCommand(Supplier<Double> speed, Supplier<Double> rotation) {
        this.speed = speed.get();
        this.rotation = rotation.get();

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        driveSubsystem.arcadeDrive(speed, rotation);
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }

}
