window.onload = function () {
    populateDays();
    populateYears();
};
document.getElementById('btnRegister').addEventListener('click', function () {
    document.body.classList.add('show-modal');
});

document.getElementById('closeModal').addEventListener('click', function () {
    document.body.classList.remove('show-modal');
});

document.getElementById('modalBackdrop').addEventListener('click', function () {
    document.body.classList.remove('show-modal');
});
function populateDays() {
    var daySelect = document.getElementById('day');
    for (var i = 1; i <= 31; i++) {
        var option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        daySelect.appendChild(option);
    }
}
function populateYears() {
    var yearSelect = document.getElementById('year');
    var currentYear = new Date().getFullYear();

    for (var i = currentYear; i >= currentYear - 120; i--) {
        var option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        yearSelect.appendChild(option);
    }
}
function togglePasswordSignInVisibility() {
    var passwordInput = document.getElementById("txtPasswordSignUp");
    var checkbox = document.getElementById("showPasswordCheckboxSignUp");

    if (checkbox.checked) {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}
function checkDataSignUp() {
    let isValid = true;
    if (changeFirstName() == false) {
        isValid = false;
    }
    if (changeSurName() == false) {
        isValid = false;
    }
    if (changeUsernameSignup() == false) {
        isValid = false;
    }
    if (changePasswordSignup() == false) {
        isValid = false;
    }
    if (changeBirthday() == false) {
        isValid = false;
    }
    if (changeGender() == false) {
        isValid = false;
    }

    return isValid;
}
function changeFirstName() {
    const firstName = document.getElementById("txtFirstName");
    if (!firstName.value.trim()) {
        firstName.classList.add("error-border");
        return false;
    } else {
        firstName.classList.remove("error-border");
        return true;
    }
}

function changeSurName() {
    const surName = document.getElementById("txtSurName");
    if (!surName.value.trim()) {
        surName.classList.add("error-border");
        return false;
    } else {
        surName.classList.remove("error-border");
        return true;
    }
}

function changeUsernameSignup() {
    const username = document.getElementById("txtUsernameSignUp");
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const phoneRegex = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/;

    if (!username.value.trim() || !(emailRegex.test(username.value) || phoneRegex.test(username.value))) {
        username.classList.add("error-border");
        return false;
    } else {
        username.classList.remove("error-border");
        return true;
    }
}

function changePasswordSignup() {
    const password = document.getElementById("txtPasswordSignUp");
    if (password.value.length < 8) {
        password.classList.add("error-border");
        return false;
    } else {
        password.classList.remove("error-border");
        return true;
    }
}

function changeBirthday() {
    const day = document.getElementById("day");
    const month = document.getElementById("month");
    const year = document.getElementById("year");

    const today = new Date();
    const birthDate = new Date(`${year.value}-${month.value}-${day.value}`);
    const age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    if (isNaN(birthDate) || age < 14) {
        day.classList.add("error-border");
        month.classList.add("error-border");
        year.classList.add("error-border");
        return false;
    } else {
        day.classList.remove("error-border");
        month.classList.remove("error-border");
        year.classList.remove("error-border");
        return true;
    }
}

function changeGender() {
    const genderFemale = document.getElementById("female");
    const genderMale = document.getElementById("male");
    const genderOther = document.getElementById("Other");
    const genderboxFemale = document.getElementById("genderboxFemale");
    const genderboxMale = document.getElementById("genderboxMale");
    const genderboxOther = document.getElementById("genderboxOther");

    if (!genderFemale.checked && !genderMale.checked && !genderOther.checked) {
        genderboxFemale.classList.add("error-border");
        genderboxMale.classList.add("error-border");
        genderboxOther.classList.add("error-border");
        return false;
    } else {
        genderboxFemale.classList.remove("error-border");
        genderboxMale.classList.remove("error-border");
        genderboxOther.classList.remove("error-border");
        return true;
    }
}
function SignUp() {
    if (checkDataSignUp()) {
        // Disable the button and show the spinner
        const signUpButton = document.getElementById('btnRegister');
        const signUpSpinner = document.getElementById('Spinner');
        const signUpButtonText = document.getElementById('signUpButtonText');

        signUpButton.disabled = true;
        signUpSpinner.style.display = 'inline-block';
        signUpButtonText.textContent = ''; // Set text của button là rỗng


        // Lấy dữ liệu từ các trường nhập liệu
        const firstName = $("#txtFirstName").val();
        const surName = $("#txtSurName").val();
        const username = $("#txtUsernameSignUp").val();
        const password = $("#txtPasswordSignUp").val();
        const day = $("#day").val().padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
        const month = $("#month").val().padStart(2, '0'); // Đảm bảo tháng có 2 chữ số
        const year = $("#year").val();
        const gender = $('input[name="sex"]:checked').val();

        // Tạo đối tượng dữ liệu để gửi
        const formData = {
            name: `${firstName} ${surName}`,
            username: username,
            password: password,
            birthDate: `${year}-${month}-${day}`, // Sử dụng birthDate thay vì birthday
            gender: gender
        };

        // Hiển thị dữ liệu gửi đi bằng alert
        console.log(`Dữ liệu gửi đi:
        Name: ${formData.name} (type: ${typeof formData.name})
        Username: ${formData.username} (type: ${typeof formData.username})
        Password: ${formData.password} (type: ${typeof formData.password})
        Birth Date: ${formData.birthDate} (type: ${typeof formData.birthDate})
        Gender: ${formData.gender} (type: ${typeof formData.gender})`);
        // Thực hiện yêu cầu AJAX
        $.ajax({
            url: '/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                if (data.success) {
                    alert('Đăng ký thành công!');
                    $('body').removeClass('show-modal'); // Đóng modal sau khi đăng ký thành công
                } else {
                    alert('Đăng ký thất bại: ' + (data.message || 'Không có thông báo lỗi'));
                }
            },
            error: function (xhr, status, error) {
                console.error('Có lỗi xảy ra:', error);
                alert('Đã xảy ra lỗi. Vui lòng thử lại sau.');
            },
            complete: function () {
                // Enable the button and hide the spinner
                signUpButton.disabled = false;
                signUpSpinner.style.display = 'none';
                signUpButtonText.textContent = 'Sign Up'; // Hiện lại text của button
            }
        });
    }
}
