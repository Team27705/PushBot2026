package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp(name = "Driving TeleOp", group = "TeleOp")
public class DrivingTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        Servo spotlight = hardwareMap.get(Servo.class, "spotlight");
        RevBlinkinLedDriver lightController = hardwareMap.get(RevBlinkinLedDriver.class, "light");

        // Store the servo position to maintain it when joystick is released
        double spotlightPosition = 0.5; // Start at center

        waitForStart();

        while (opModeIsActive()) {
            // If Left Stick Y is pushed forward, the robot should move forward. If it's pulled
            // back, the robot should move backward.
            double drive = gamepad1.left_stick_y;
            // If Left Stick X is pushed to the right, the robot should turn right. If it's
            // pushed to the left, the robot should turn left.
            double turn = gamepad1.left_stick_x;

            // Calculate the power for each motor based on the drive and turn inputs
            double leftPower = drive - turn;
            double rightPower = drive + turn;

            // Set the calculated power to the drivetrain motors
            drivetrain.setPower(leftPower, rightPower);

            // Control the spotlight servo with Right Stick X.
            // Only update position if the joystick is being moved (deadzone check)
            if (Math.abs(gamepad1.right_stick_x) > 0.1) {
                // Convert from [-1, 1] to [0, 1]
                spotlightPosition = (gamepad1.right_stick_x + 1) / 2;
            }
            spotlight.setPosition(spotlightPosition);

            // Change LED pattern based on button releases
            if (gamepad1.aWasReleased()) {
                lightController.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            } else if (gamepad1.bWasReleased()) {
                lightController.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            } else if (gamepad1.xWasReleased()) {
                lightController.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            } else if (gamepad1.yWasReleased()) {
                lightController.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            }
        }
    }
}
