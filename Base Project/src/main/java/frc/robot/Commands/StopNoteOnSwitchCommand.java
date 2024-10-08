package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Subsystems.TransferSubsystem;
import static frc.robot.Constants.*;

public class StopNoteOnSwitchCommand extends Command {
    private TransferSubsystem transferSubsystem;

    public StopNoteOnSwitchCommand(TransferSubsystem transferSubsystem) {
        this.transferSubsystem = transferSubsystem;
        addRequirements(transferSubsystem);
    }

    @Override
    public void initialize() {
        transferSubsystem.setMotor(0);
    }

    @Override
    public void end(boolean interrupted) {
        transferSubsystem.setMotor(Constants.TransferConstants.INTAKE_SPEED);
    }

    @Override
    public boolean isFinished() {
        return transferSubsystem.isNoteInTransfer();
    }

}
