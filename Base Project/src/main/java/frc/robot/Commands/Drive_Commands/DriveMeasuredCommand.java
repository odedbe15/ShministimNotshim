package frc.robot.Commands.Drive_Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.DriveSubsystem;

public class DriveMeasuredCommand extends Command {

    DriveSubsystem m_driveSubsystem;
    double m_distance;

    public DriveMeasuredCommand(DriveSubsystem driveSubsystem, double distance) {

        m_driveSubsystem = driveSubsystem;
        m_distance = distance;

        addRequirements(driveSubsystem);

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    public void execute() {
        m_driveSubsystem.driveDistance(m_distance);
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        if (m_driveSubsystem.getAvgEncoderDistance() >= m_distance) {
            return true;
        } else {
            return false;
        }
    }

}
