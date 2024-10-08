package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Subsystems.TransferSubsystem;

public class PassNoteToShootCommand extends Command {
    private boolean phase1;
    private boolean phase2;
    private boolean phase3;

    private TransferSubsystem transferSubsystem;

    public PassNoteToShootCommand(TransferSubsystem transferSubsystem) {
        this.transferSubsystem = transferSubsystem;
        phase1 = false;
        phase2 = false;
        phase3 = false;
        addRequirements(transferSubsystem);
    }

    @Override
    public void execute() {
        transferSubsystem.setMotor(Constants.TransferConstants.PASS_TO_SHOOT_SPEED);
        if (transferSubsystem.isNoteInTransfer()) {
            phase1 = true;
        }
        if (!transferSubsystem.isNoteInTransfer() && phase1) {
            phase2 = true;
        }
        if (transferSubsystem.isNoteInTransfer() && phase1 && phase2) {
            phase3 = true;
        }

    }

    @Override
    public boolean isFinished() {
        return phase1 && phase2 && phase3;
    }

    @Override
    public void end(boolean interrupted) {
        transferSubsystem.stopMotor();
    }

}
