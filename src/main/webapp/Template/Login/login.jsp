<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="Static/CSS/login.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
        <title>Login or Sign up</title>
        <style>
            .error-border {
                border: 1px solid red !important; /* !important ?? ghi ?è các quy t?c CSS khác */
            }
            .spinner-border {
                display: none;
            }
        </style>
    </head>

    <body>
        <div class="background-container">
            <div class="col-md-4 offset-md-4 background-login">
                <div class="logoLogin">
                    <!-- <img src="" class="imglogo"> -->
                </div>
                <div class="container-formLogin">
                    <div class="textDangNhap">SGU SOCIAL MEDIA</div>
                    <div class="TenDangNhap_Pass mb-3 mx-3">
                        <input type="text" placeholder="Email address or phone number" class="form-control cssTxtTenDangNhap"
                               id="txtUsername" style="margin-bottom: 0px;" oninput="changeUsernameLogin()">
                        <div id="usernameError" class="error-message float-end" style="color: red; margin-bottom: 3%;"></div>
                    </div>
                    <div class="TenDangNhap_Pass mb-3 mx-3">
                        <input type="password" placeholder="password" class="form-control cssTxtTenDangNhap" id="txtPassword"
                               style="margin-bottom: 2%;" oninput="changePasswordLogin()">
                        <input type="checkbox" id="showPasswordCheckbox" onchange="togglePasswordVisibility()">
                        <label style="font-weight: bold;">Show password</label>
                        <div id="passwordError" class="error-message float-end" style="color: red; margin-bottom: 3%;"></div>
                    </div>
                    <div id="loginError" style="color: red; margin-left: 0 !important;"></div>
                    <button class="btn btn-primary ButtonDangNhap" id="btnlogin" onclick="Login()">
                        Log in</button>
                    <a href="/Template/Forgot_Password/forgotpassword.jsp" class="Quenmatkhau ">Forgotten password?</a>
                    <div class="_8icz"></div>
                    <div class="container-dangky">
                        <button class="btn btn-success" id="btnRegister">Create new account</button>
                    </div>
                </div>
            </div>
            <div class="col-md-4"></div>
        </div>

        <div class="modal-backdrop" id="modalBackdrop"></div>

        <div class="modal-content" id="modalContent">
            <button class="close-button" id="closeModal">&times;</button>
            <h3>Create a new account</h3>
            <div class="lineFull"></div>
            <form>
                <div class="d-flex"> 
                    <input type="text" placeholder="First Name" class="form-control cssTxtTenDangNhap"
                           id="txtFirstName" style="margin-bottom: 0px;" oninput="changeFirstName()"> 
                    <input type="text" placeholder="SurName" class="form-control cssTxtTenDangNhap"
                           id="txtSurName" style="margin-bottom: 0px;" oninput="changeSurName()"> 
                </div>
                <input type="text" placeholder="Email address or phone number" class="form-control cssTxtTenDangNhap"
                       id="txtUsernameSignUp" style="margin-bottom: 0px;" oninput="changeUsernameSignup()">
                <input type="password" placeholder="New password" class="form-control cssTxtTenDangNhap" id="txtPasswordSignUp"
                       style="margin-bottom: 2px !important;" oninput="changePasswordSignup()">
                <div class="d-flex" style="justify-content: end;"> 
                    <input type="checkbox" id="showPasswordCheckboxSignUp" onchange="togglePasswordSignInVisibility()">
                    <label style="font-weight: bold;" class="mx-2">Show password</label>
                </div>
                <div class="form-label">Date of birth:</div>
                <div class="d-flex"> 
                    <select name="birthday_day" id="day" class="form-select" onchange="changeBirthday()">
                    </select>
                    <select name="birthday_month" id="month" class="form-select mx-3" onchange="changeBirthday()">
                        <option value="1">Jan</option>
                        <option value="2">Feb</option>
                        <option value="3">Mar</option>
                        <option value="4">Apr</option>
                        <option value="5">May</option>
                        <option value="6">Jun</option>
                        <option value="7">Jul</option>
                        <option value="8">Aug</option>
                        <option value="9">Sep</option>
                        <option value="10">Oct</option>
                        <option value="11">Nov</option>
                        <option value="12">Dec</option>
                    </select>
                    <select name="birthday_year" id="year" class="form-select" onchange="changeBirthday()">
                    </select>
                </div>
                <div class="form-label">Gender:</div>
                <div class="d-flex" onchange="changeGender()"> 
                    <span class="genderbox" id="genderboxFemale">
                        <input type="radio" name="sex" value="nu" id="female">
                        <label class="ps-1">Female</label>
                    </span>
                    <span class="mx-1 genderbox" id="genderboxMale">
                        <input type="radio" name="sex" value="nam" id="male">
                        <label class="ps-1">Male</label>
                    </span>
                    <span class="genderbox" id="genderboxOther">
                        <input type="radio" name="sex" value="other" id="Other">
                        <label class="ps-1">Other</label>
                    </span>
                </div>
                <p style="font-size: 12px; margin-top: 5%;">
                    People who use our service may have uploaded your contact information to SGU. Learn more.
                </p>
                <p style="font-size: 12px;">
                    By clicking Sign Up, you agree to our Terms, Privacy Policy and Cookies Policy. You may receive SMS notifications from us and can opt out at any time                </p>
                <div class="container-dangky">
                    <button type="button" class="btn btn-success" id="btnRegister" style="width: 50%;" onclick="SignUp()">
                        <span class="spinner-border spinner-border-sm" id="Spinner" role="status" aria-hidden="true"></span>
                        <span id="signUpButtonText">Sign Up</span>
                    </button>
                </div>  
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="Static/JS/sign_up.js"></script>
        <script src="Static/JS/login.js"></script>
    </body>

</html>
