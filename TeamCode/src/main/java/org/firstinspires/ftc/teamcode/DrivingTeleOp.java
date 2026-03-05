package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp(name = "Driving TeleOp", group = "TeleOp")
public class DrivingTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            // If Left Stick Y is pushed forward, the robot should move forward. If it's pulled
            // back, the robot should move backward.
            double drive = -gamepad1.left_stick_y; // Invert Y axis for intuitive control
            // If Left Stick X is pushed to the right, the robot should turn right. If it's
            // pushed to the left, the robot should turn left.
            double turn = gamepad1.left_stick_x;

            // Calculate the power for each motor based on the drive and turn inputs
            double leftPower = drive + turn;
            double rightPower = drive - turn;

            // Normalize the power values to ensure they are within the range of -1.0 to 1.0
            double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
            if (maxPower > 1.0) {
                leftPower /= maxPower;
                rightPower /= maxPower;
            }

            // Set the calculated power to the drivetrain motors
            drivetrain.setPower(leftPower, rightPower);
        }
    }
}
