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

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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

@@TeleOp(name="newteleop", group="Iterative Opmode")
public class newteleop extends OpMode


{
    boolean changed = false;
    double top = 0;
    double pulleyPower = 0;     //power for pulley
    double intakePower = 0;
    double intakeExtend = 0;
    //motors------------------------------------v
    public DcMotor leftFront;   //drivetrainwheels
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;

    public DcMotor bottomIntake; //bottom
    public DcMotor pulley; //topintake-
    public  DcMotor topwheel; //greenwheel
    public  DcMotor intakeExtension;



    //servos----------------------------------
    public Servo intakegate; //open close
    public Servo cap; //capping
    public Servo intake2;


    //COLOR SENSOR --------------------------
    ColorSensor color_sensor;

    @Override
    public void init() {

//intake
        bottomIntake = hardwareMap.dcMotor.get("bottomIntake");
        pulley = hardwareMap.dcMotor.get("pulley");
        intakegate = hardwareMap.servo.get("intakegate");
        intakeExtension = hardwareMap.dcMotor.get("intakeExtension");


//DRIVETRAIN
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");

        // rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);


        //  leftFront.setDirection(DcMotorSimple.Direction.REVERSE);


   /*   rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
*/

//topwheel duck

        topwheel = hardwareMap.dcMotor.get("topwheel");

        //cap
        cap = hardwareMap.servo.get("cap");

        cap.setDirection(Servo.Direction.REVERSE);

        intake2 = hardwareMap.servo.get("intake2");

        //COLOR SENSOR--------------------
        color_sensor = hardwareMap.colorSensor.get("color");

    }

    @Override
    public void loop() {
        float x;
        float y;
        float z;

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
            pulleyPower = -0.7;

        } else if (Math.abs(gamepad2.right_trigger) > .1) {
            pulleyPower = 0.7;

        } else {
            pulleyPower = 0;

        }

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

     /*  if((gamepad2.left_stick_y)>0.2) {
           intake2.setPosition(intake2.getPosition() + 0.1);
        }else {
            intake2.setPosition(intake2.getPosition() - 0.1);
        }
      */

        if((gamepad1.right_trigger)>0.1) {
            intake2.setPosition(intake2.getPosition()+0.1);
        } else if ((gamepad1.left_trigger)>0.1) {
            intake2.setPosition(intake2.getPosition()-0.1);
        } else {
            intake2.setPosition(0.5);
        }

        //   intake2.setPosition(gamepad2.left_stick_y);

        //  if(Math.abs(gamepad2.left_stick_y)>.2) {
        //   intakeExtend = gamepad2.left_stick_y;
        //  } else {
        //    intakeExtend = 0;
        //}


        //cap
        if (gamepad1.a) {
            cap.setPosition(1);
        }

        if (gamepad1.b) {
            cap.setPosition(0);
        }

        if (gamepad1.x) {
            cap.setPosition(0.5);
        }


        leftBack.setPower((y - x + z)*.75);
        leftFront.setPower((y + x + z)*.75);

        rightBack.setPower((y + x - z)*.75);
        rightFront.setPower((y - x - z)*.75);


        topwheel.setPower(top*0.5);
        pulley.setPower(pulleyPower);
        bottomIntake.setPower(intakePower);

        //COLOR SENSOR
        color_sensor.red();
        color_sensor.green();
        color_sensor.alpha();
        color_sensor.argb();

        //  intakeExtension.setPower(intakeExtend);

        telemetry.addData ("rightfront",
                rightFront.getCurrentPosition());
        telemetry.addData ("rightback",
                rightBack.getCurrentPosition());
        telemetry.addData ("leftback",
                leftBack.getCurrentPosition());
        telemetry.addData ("leftfront",
                leftFront.getCurrentPosition());

        telemetry.addData("Red", color_sensor.red());
        telemetry.addData("Green", color_sensor.green());
        telemetry.addData("Blue", color_sensor.blue());
        telemetry.update();




    }
}