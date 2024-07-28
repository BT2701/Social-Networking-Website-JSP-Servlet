// Lắng nghe sự kiện keydown trên trường nhập liệu mật khẩu
document
        .getElementById("txtPassword")
        .addEventListener("keydown", function (event) {
            // Kiểm tra nếu phím nhấn là "Enter" và không có phím môdifer nào được nhấn (Ctrl, Alt, Shift...)
            if (
                    event.key === "Enter" &&
                    !event.ctrlKey &&
                    !event.altKey &&
                    !event.shiftKey
                    ) {
                // Ngăn chặn hành động mặc định của phím Enter
                event.preventDefault();

                // Gọi hàm đăng nhập
                Login();
            }
        });
function togglePasswordVisibility() {
    var passwordInput = document.getElementById("txtPassword");
    var checkbox = document.getElementById("showPasswordCheckbox");

    if (checkbox.checked) {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
}

// Gọi hàm validateLoginForm khi người dùng nhấn nút Đăng Nhập
function Login() {
    var username = document.getElementById("txtUsername").value;
    var password = document.getElementById("txtPassword").value;
    if (checkUsername(username)) {
        if (checkPassword(password)) {
            checkLogin(username, password);
        }
    }
}


function checkUsername(username) {
    // Kiểm tra rỗng
    if (!username) {
        document.getElementById("usernameError").innerHTML =
                "TÊN ĐĂNG NHẬP không được để trống!";
        return false;
    }

    // Biểu thức chính quy để kiểm tra số điện thoại (đơn giản)
    const phoneRegex = /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/;

    // Biểu thức chính quy để kiểm tra email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // Kiểm tra nếu là số điện thoại hoặc email
    if (phoneRegex.test(username) || emailRegex.test(username)) {
        document.getElementById("usernameError").innerHTML = "";
        return true;
    } else {
        document.getElementById("usernameError").innerHTML =
                "TÊN ĐĂNG NHẬP sai định dạng!";
        return false;
    }
}
// Gọi hàm changeUsername khi người dùng thay đổi giá trị trong ô nhập liệu username
function changeUsernameLogin() {
    document.getElementById("usernameError").innerHTML = "";
}

function checkPassword(password) {
    if (password.trim() === "") {
        document.getElementById("passwordError").innerHTML = "Mật khẩu không được để trống!";
        return false;
    } else if (password.length < 8) {
        document.getElementById("passwordError").innerHTML = "Mật khẩu phải có ít nhất 8 ký tự!";
        return false;
    } else {
        document.getElementById("passwordError").innerHTML = "";
        return true;
    }
}
// Gọi hàm changePassword khi người dùng thay đổi giá trị trong ô nhập liệu password
function changePasswordLogin() {
    document.getElementById("passwordError").innerHTML = "";
}

function checkLogin(username, password) {
    $.ajax({
        type: "POST",
        url: "/login",
        data: {
            username: username,
            password: password
        },
        success: function (response) {
            if (response.success) {
                // Hỏi người dùng có muốn lưu thông tin đăng nhập không
                if (confirm("Bạn có muốn lưu thông tin đăng nhập để tự động đăng nhập lần sau không?")) {
                    // Tạo cookie với thông tin đăng nhập
                    document.cookie = "username=" + encodeURIComponent(username) + "; max-age=" + (30 * 24 * 60 * 60) + "; path=/";
                    document.cookie = "password=" + encodeURIComponent(password) + "; max-age=" + (30 * 24 * 60 * 60) + "; path=/";
                } else {
                    // Xóa cookie nếu có
                    document.cookie = "username=; max-age=0; path=/";
                    document.cookie = "password=; max-age=0; path=/";
                }

                // Chuyển hướng đến trang chủ
                window.location.href = "index.jsp";
            } else {
                alert(response.message); // Hiển thị thông báo lỗi từ server
            }
        },
        error: function (xhr, status, error) {
            console.error("AJAX error:");
            console.error("Status:", status);
            console.error("Error:", error);
            console.error("Response text:", xhr.responseText);
        }
    });
}
// Hàm để lấy giá trị cookie theo tên
function getCookie(name) {
    let value = "; " + document.cookie;
    let parts = value.split("; " + name + "=");
    if (parts.length === 2) {
        return decodeURIComponent(parts.pop().split(";").shift());
    }
    return null;
}

// Hàm tự động đăng nhập nếu có cookie
function autoLogin() {
    let username = getCookie('username');
    let password = getCookie('password');
    console.log("Username from cookie:", username);
    console.log("Password from cookie:", password);

    if (username && password) {
        // Gửi thông tin đăng nhập từ cookie đến server
        $.ajax({
            type: "POST",
            url: "/login",
            data: {
                username: username,
                password: password
            },
            success: function (response) {
                if (response.success) {
                    alert("auto login");
                    // Tạo cookie với thông tin đăng nhập
                    document.cookie = "username=" + encodeURIComponent(username) + "; max-age=" + (30 * 24 * 60 * 60) + "; path=/";
                    document.cookie = "password=" + encodeURIComponent(password) + "; max-age=" + (30 * 24 * 60 * 60) + "; path=/";
                    window.location.href = "index.jsp"; // Chuyển hướng đến trang testcookie.jsp
                } else {
                    // Xóa cookie nếu có
                    document.cookie = "username=; max-age=0; path=/";
                    document.cookie = "password=; max-age=0; path=/";
                    console.log("Cookie login failed:", response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX error:");
                console.error("Status:", status);
                console.error("Error:", error);
                console.error("Response text:", xhr.responseText);
            }
        });
    }
}

// Gọi hàm autoLogin khi trang được tải
$(document).ready(function() {
    autoLogin();
});





