<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="../../Static/CSS/forgotpassword.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <title>Forgot Password</title>
        <style>
            .countdown {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>

    <body>                
        <div class="container" id="FormFindAccount">
            <h3>Find Your Account</h3>
            <p>Please enter your email address or mobile number to search for your account.</p>
            <div class="mb-3 mx-3">
                <input type="text" placeholder="Email address or phone number" class="form-control"
                       id="txtUsername" style="margin-bottom: 0px;" oninput="changeUsername()">
                <div id="usernameError" class="error-message float-end mb-3" style="color: red;"></div>
            </div>
            <div class="lineFull mt-4 mb-3"></div>
            <div class="d-flex justify-content-end">
                <button class="btn w-25 m-2" id="btnCancel" style="background-color: #e4e6eb" onclick="goToLoginPage()">Cancel</button>
                <button class="btn btn-primary w-25 m-2" id="btnSearch" onclick="FindAccount()">Search</button>
            </div>
        </div>


        <div class="container d-none" id="FormInfoAccount">
            <h4>Profile Information</h4>
            <div class="lineFull mt-4 mb-3"></div>
            <div class="d-flex justify-content-center">
                <img class="imgCustom w-25 h-25" id="profileImage" src="" alt="Profile Image">
            </div>
            <div class="d-flex justify-content-center">
                <h3 id="profileName" ></h3>
            </div>


            <div class="d-none" id="FormSendOtp">
                <div class="lineFull mt-4 mb-3"></div>
                <div class="d-flex justify-content-end">
                    <button class="btn w-25 m-2"  style="background-color: #e4e6eb" onclick="GotoForgotpassword()">Cancel</button>
                    <button class="btn btn-primary w-30 m-2" id="btnSendOTP" onclick="SendOTP()">
                        <span class="spinner-border spinner-border-sm" id="SendOTPSpinner" role="status" aria-hidden="true"></span>
                        <span id="SendOTPText">Reset Password</span>

                    </button>
                </div>
            </div>


            <div class="d-none" id="FormEnterOTP">
                <div class="lineFull mt-4 mb-3"></div>
                <div >OTP expires after: <span class="countdown" id="countdownOTP"> </span> Second</div>
                <div class="mb-3"style="width: 100%;">
                    <input type="text" placeholder="Please enter Your OTP" class="form-control"
                           id="txtOTP" style="margin-bottom: 0px;" oninput="changeOTP()">
                    <!--                    oninput="changeOTP()"-->
                    <div id="OTPError" class="error-message mb-3" style="color: red;"></div>
                </div>
                <div class="d-flex justify-content-end">
                    <button class="btn w-25 m-2"  style="background-color: #e4e6eb" onclick="GotoForgotpassword()">Cancel</button>
                    <button class="btn btn-primary w-30 m-2" id="btnResetPassword" onclick="ResetPassword()">
                        <span class="spinner-border spinner-border-sm" id="ConfirmOTPSpinner" role="status" aria-hidden="true"></span>
                        <span id="ConfirmOTPText">Reset Password</span>
                    </button>
                </div>
            </div>

        </div>

        <div class="container d-none" id="FormRedirect">
            <h3>Reset password success</h3>
            <p>New password has been sent to your email</p>
            <div class="d-flex justify-content-center">
                <a href="/login">back to the login page later <span class="countdown" id="countdown"> </span> Second</a>
            </div>
            <div class="lineFull mt-4 mb-3"></div>
            <div class="d-flex justify-content-end">
            </div>
        </div>



        <!-- Truong an luu email ,id-->
        <input type="hidden" id="userEmail" value="">
        <input type="hidden" id="userId" value="">


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="/Static/JS/login.js"></script>
        <script src="/Static/JS/forgotpassword.js"></script>

    </body>


</html>

