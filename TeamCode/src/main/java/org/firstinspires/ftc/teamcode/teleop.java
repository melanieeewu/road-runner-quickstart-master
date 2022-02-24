/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="teleop", group="Iterative Opmode")
@Config
public class teleop extends OpMode

{
    boolean changed = false;
    double top = 0;
    double pulleyPower = 0;     //power for pulley
    double intakePower = 0;

    public DcMotor bottomIntake; //bottom
    public DcMotor pulley; //topintake-
    public  DcMotor topwheel; //greenwheel
    public  DcMotor intakeExtension;



    //servos----------------------------------
    public Servo intakegate; //open close

    public CRServo pivot;
    public CRServo turn;
    public CRServo tilt;

    public CRServo e1;
    public CRServo e2;



    private SampleMecanumDrive drive;
    @Override
    public void init() {

//intake
        bottomIntake = hardwareMap.dcMotor.get("bottomIntake");
        pulley = hardwareMap.dcMotor.get("pulley");
        intakegate = hardwareMap.servo.get("intakegate");
        intakeExtension = hardwareMap.dcMotor.get("intakeExtension");


        drive = new SampleMecanumDrive(hardwareMap);

//topwheel duck

        topwheel = hardwareMap.dcMotor.get("topwheel");


        turn = hardwareMap.crservo.get("turn");
        pivot = hardwareMap.crservo.get("pivot");
        tilt = hardwareMap.crservo.get("tilt");

        e1 = hardwareMap.crservo.get("e1");
        e2 = hardwareMap.crservo.get("e2");

    }

    @Override
    public void loop() {
        float x;
        float y;
        float z;

        drive.setWeightedDrivePower(
                new Pose2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x
                )
        );

        drive.update();


        //GAMEPAD 2 ------------------------------------------
        //drivetrain
        if (Math.abs(gamepad1.left_stick_y) > .2) {
            y = -gamepad1.left_stick_y;
        } else {
            y = 0;
        }

        if (Math.abs(gamepad1.left_stick_x) > .2) {
            x = -gamepad1.left_stick_x;
        } else {
            x = 0;
        }

        if (Math.abs(gamepad1.right_stick_x) > .2) {
            z = -gamepad1.right_stick_x;
        } else {
            z = 0;
        }

        //topwheel
        if (Math.abs(gamepad1.left_trigger) > .1) {
            top = -1;

        } else if (Math.abs(gamepad1.right_trigger) > .1) {
            top = 1;

        } else {
            top = 0;

        }



        //GAMEPAD 1 -------------------------------------------------
        // top intake
             /*   pulley linear slide lift
        left trigger = goes up
        right trigger = goes down */

        if (Math.abs(gamepad2.left_trigger) > .1) {
            pulleyPower = -1;

        } else if (Math.abs(gamepad2.right_trigger) > .1) {
            pulleyPower = 1;

        } else {
            pulleyPower = 0;

        }

        if (gamepad2.dpad_down) {
            tilt.setPower(-0.1);
        } else if (gamepad2.dpad_up) {
            tilt.setPower(0.1);
        } else {tilt.setPower(0);
        }

        if (gamepad2.dpad_right) {
            pivot.setPower(0.3);
        } else if (gamepad2.dpad_left) {
            pivot.setPower(-0.3);
        } else {
            pivot.setPower(0);
        }

        turn.setPower(-gamepad2.right_stick_y);
//servogate
        if (gamepad2.y && !changed) {
            if(intakegate.getPosition() == 0 )
                intakegate.setPosition(0.6);
            else {
                intakegate.setPosition(0);
            }
            changed = true;
        } else if (!gamepad2.y) {
            changed = false;
        }

//bottom intake
        //intakePower = gamepad2.left_stick_y;
        if(Math.abs(gamepad2.left_stick_y)>.2) {
            intakePower = gamepad2.left_stick_y;
        } else {
            intakePower = 0;
        }

        //EMERGENCY SERVO



        if (gamepad2.b) {
            e2.setPower(1);
            e1.setPower(-1);
        } else if (gamepad2.a){
            e2.setPower(-1);
            e1.setPower(1);
        } else {
            e2.setPower(0);
            e1.setPower(0);
        }

        topwheel.setPower(top*0.5);
        pulley.setPower(pulleyPower);
        bottomIntake.setPower(intakePower);





    }
}