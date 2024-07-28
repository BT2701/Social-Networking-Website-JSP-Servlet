function changeUsername() {
    document.getElementById("usernameError").innerHTML = "";
}
function goToLoginPage() {
    // Thay đổi window.location.href thành đường dẫn của trang login của bạn
    window.location.href = "../../login";
}
function FindAccount() {
    var username = document.getElementById("txtUsername").value;
    if (checkUsername(username)) {
        $.ajax({
            url: '/forgotPassword',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({action: "findAccountByUsername", username: username}),
            success: function (response) {
                if (response.success) {
                    $('#profileImage').attr('src', '/Static/Images/' + response.avt); // Cập nhật hình ảnh
                    $('#profileName').text('' + response.name); // Cập nhật tên
                    $('#userEmail').val('' + response.email);
                    $('#userId').val('' + response.id);


                    // Ẩn form tìm kiếm và hiển thị form thông tin
                    $('#FormFindAccount').hide(); // Ẩn form tìm kiếm
                    $('#FormInfoAccount').removeClass('d-none'); // Hiển thị form thông tin
                    $('#FormSendOtp').removeClass('d-none'); // Hiển thị form thông tin


                    $("#usernameError").text(""); // Xóa lỗi nếu tìm thấy tài khoản
                } else {
                    console.log("Lỗi:", response.message || "Không tìm thấy tài khoản.");
                    $("#usernameError").text(response.message || "Đã xảy ra lỗi.");
                    $('#FormInfoAccount').addClass('d-none'); // Ẩn form thông tin nếu không có thông tin
                }
            },
            error: function (xhr, status, error) {
                console.log("Có lỗi xảy ra khi tìm kiếm tài khoản:", error);
                $("#usernameError").text("Có lỗi xảy ra khi tìm kiếm tài khoản.");
            }
        });
    }
}

function SendOTP() {
    // Disable the button and show the spinner
    const resetPasswordButton = document.getElementById('btnSendOTP');
    const Spinner = document.getElementById('SendOTPSpinner');
    const SendOTPText = document.getElementById('SendOTPText');

    resetPasswordButton.disabled = true;
    Spinner.style.display = 'inline-block';
    SendOTPText.textContent = ''; // Set text của button là rỗng

    var email = $("#userEmail").val(); // Lấy email từ trường ẩn

    $.ajax({
        url: '/forgotPassword',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            action: 'sendOtp',
            email: email
        }),
        success: function (response) {
            if (response.success) {
                $('#FormSendOtp').addClass('d-none'); // Ẩn form thông tin nếu không có thông tin
                $('#FormEnterOTP').removeClass('d-none'); // Hiển thị form thông tin
                startCountdown("countdownOTP", 301);//show form nhap OTP


            } else {
                alert("Đã xảy ra lỗi: " + response.message);
            }
        },
        error: function (xhr, status, error) {
            console.error("Có lỗi xảy ra khi gửi yêu cầu:", error);
            alert("Có lỗi xảy ra khi gửi yêu cầu.");
        },
        complete: function () {
            // Enable the button and hide the spinner
            resetPasswordButton.disabled = false;
            Spinner.style.display = 'none';
            SendOTPText.textContent = 'Reset Password'; // Hiện lại text của button
        }
    });
}
function ResetPassword() {
    if (validateOTP()) {
        var id = $("#userId").val(); // Lấy id từ trường ẩn
        var otp = document.getElementById("txtOTP").value;
        var email = $("#userEmail").val(); // Lấy email từ trường ẩn

// Disable the button and show the spinner
        const resetPasswordButton = document.getElementById('btnResetPassword');
        const Spinner = document.getElementById('ConfirmOTPSpinner');
        const ConfirmOTPText = document.getElementById('ConfirmOTPText');

        resetPasswordButton.disabled = true;
        Spinner.style.display = 'inline-block';
        ConfirmOTPText.textContent = ''; // Set text của button là rỗng

        $.ajax({
            url: '/forgotPassword',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                action: 'ConfirmOtp',
                id: id,
                otp: otp,
                email: email
            }),
            success: function (response) {
                if (response.success) {
//                    alert("Mật khẩu mới đã được gửi đến email của bạn");
                    showRedirectForm();
                    // Hiển thị form nhập OTP hoặc chuyển hướng người dùng nếu cần
//                $('#FormInfoAccount').addClass('d-none'); // Ẩn form thông tin nếu không có thông tin
//                $('#FormEnterOTP').removeClass('d-none'); // Hiển thị form thông tin

                } else {
                    alert("Đã xảy ra lỗi: " + response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("Có lỗi xảy ra khi gửi yêu cầu:", error);
                alert("Có lỗi xảy ra khi gửi yêu cầu.");
            },
            complete: function () {
                // Enable the button and hide the spinner
                resetPasswordButton.disabled = false;
                Spinner.style.display = 'none';
                ConfirmOTPText.textContent = 'Reset Password'; // Hiện lại text của button
            }
        });
    }
}

function startCountdown(id, second) {
    var countdownElement = document.getElementById(id);
    var countdownValue = second;

    function updateCountdown() {
        countdownValue--;
        countdownElement.textContent = countdownValue;
        if (countdownValue <= 0) {
            clearInterval(interval);
            if (id === "countdown") {
                window.location.href = '/login';
            }
        }
    }

    var interval = setInterval(updateCountdown, 1000);
}
function showRedirectForm() {
    $('#FormRedirect').removeClass('d-none'); // Hiển thị form thông tin
    $('#FormInfoAccount').addClass('d-none'); // Ẩn form thông tin nếu không có thông tin
    startCountdown("countdown", 4);
}
function GotoForgotpassword() {
    $('#FormFindAccount').show(); // Hien form tìm kiếm
    $('#FormInfoAccount').addClass('d-none'); // Ẩn form thông tin nếu không có thông tin
    $('#FormRedirect').addClass('d-none'); //  Ẩn form thông tin
    $('#FormSendOtp').addClass('d-none');
    $('#FormEnterOTP').addClass('d-none');
}
function validateOTP() {
    var otp = document.getElementById('txtOTP').value;
    var otpError = document.getElementById('OTPError');
    var regex = /^\d{6}$/; // Regex kiểm tra OTP là 6 số

    if (!regex.test(otp)) {
        otpError.textContent = 'OTP phải là 6 số';
        return false;
    } else {
        otpError.textContent = '';
        return true;
    }
}
function changeOTP() {
    var otpError = document.getElementById('OTPError');
    otpError.textContent = '';

}

