$(document).ready(function () {
    // Gọi hàm FirstLoadFriend khi tài liệu đã được tải hoàn tất
    FirstLoadFriend();
});
function FirstLoadFriend() {
    var limit = '8';
    getAllFriendRequestsByReceiverId(limit);
    getAllFriendRequestsBySenderId(limit);
    getListSuggestedFriends(limit);
}
function getAllFriendRequestsByReceiverId(limit) {
    $.ajax({
        url: '/friendRequest', // URL của API
        method: 'GET',
        data: {
            action: 'getAllFriendRequestsByReceiverId',
            limit: limit
        },
        success: function (data) {
            // In dữ liệu trả về ra console để kiểm tra
            console.log('Dữ liệu trả về từ API:', data);
            // Hiển thị dữ liệu trên trang
            displayFriendRequests(data, limit);

        },
        error: function (error) {
            console.error('Error loading friend requests:', error);
            $('#friendRequestContainer').html('<p>Error loading friend requests.</p>');
        }
    });
}
function displayFriendRequests(data, limit) {
    // Lấy phần tử chứa danh sách yêu cầu kết bạn
    const container = $('#friendRequestContainer');

    // Xóa nội dung cũ
    container.empty();

    // Kiểm tra nếu dữ liệu có yêu cầu kết bạn
    if (data.length === 0) {
        container.html('<h3 class="me-auto">All Friend Request</h3>' + '<p>No friend requests available.</p>');
        return;
    }
    let headerHtml;
    if (limit === 'all') {
        headerHtml = `<div class="d-flex align-items-center header">
            <h3 class="me-auto fs-4 custom-text1" onclick="FirstLoadFriend()"> < Back</h3>
            <h3 class="me-auto">All Friend Request</h3>
        </div>`;
    } else {
        // Tạo HTML cho tiêu đề và nút "See all"
        headerHtml = `
        <div class="d-flex align-items-center">
            <h3 class="me-auto">Friend Request</h3>
            <span class="fs-4 text-primary custom-text" onclick="seeAllFriendRequest()">See all</span>
        </div>
    `;
    }

    // Khởi tạo HTML cho danh sách yêu cầu kết bạn
    let html = headerHtml + '<div class="d-flex flex-wrap">'; // Sử dụng flex-wrap để các phần tử được phân phối đều

    // Tạo HTML cho mỗi yêu cầu kết bạn
    data.forEach((request, index) => {
        // Tạo HTML cho yêu cầu kết bạn
        const requestHtml = `
        <div class="itemFriend" id="itemFriend${request.id}">
            <div>
                <img class="imgCustom" src="Static/Images/${request.avt}" alt="Profile Image">
            </div>
            <h5 class="mx-3">${request.name}</h5>
            <p class="mx-3">${request.friendCount} bạn bè</p>
            <div class="d-flex justify-content-center" id="Confirm${request.id}">
                <button class="btn btn-primary m-1" onclick="confirmRequest(${request.id})" style="width:90%;">Confirm</button>
            </div>
            <div class="d-flex justify-content-center" id="Delete${request.id}">
                <button class="btn m-1 Button" onclick="deleteRequest(${request.id},'deletefr')" >Delete</button>
            </div>
            <div class="d-flex justify-content-center" >
                <button class="btn m-1 Button d-none" disabled id="Requestaccepted${request.id}">Request accepted</button>
            </div>
        </div>
        `;

        // Thêm HTML vào chuỗi chính
        html += requestHtml;

        // Thêm thẻ <div> phân cách sau mỗi 4 phần tử
        if ((index + 1) % 4 === 0) {
            html += '</div><div class="d-flex flex-wrap">';
        }
    });

    // Đóng thẻ <div> sau khi tất cả phần tử đã được thêm
    html += '</div>';

    // Thêm toàn bộ HTML vào container
    container.html(html);
}
function getAllFriendRequestsBySenderId(limit) {
    $.ajax({
        url: '/friendRequest', // URL của API
        method: 'GET',
        data: {
            action: 'getAllFriendRequestsBySenderId',
            limit: limit
        },
        success: function (data) {
            // In dữ liệu trả về ra console để kiểm tra
            console.log('Dữ liệu trả về từ API:', data);
            // Hiển thị dữ liệu trên trang
            displayFriendRequestsBySenderId(data, limit);

        },
        error: function (error) {
            console.error('Error loading friend requests:', error);
            $('#friendRequestContainer').html('<p>Error loading friend requests.</p>');
        }
    });
}
function displayFriendRequestsBySenderId(data, limit) {
    // Lấy phần tử chứa danh sách yêu cầu kết bạn
    const container = $('#friendSenderContainer');
    // Xóa nội dung cũ
    container.empty();

    // Kiểm tra nếu dữ liệu có yêu cầu kết bạn
    if (data.length === 0) {
        container.html('<h3 class="me-auto">Friends sent requests</h3><p>No friend requests available.</p>');
        return;
    }
    let headerHtml;
    if (limit === 'all') {
        headerHtml = `<div class="d-flex align-items-center header">
            <h3 class="me-auto fs-4 custom-text1" onclick="FirstLoadFriend()"> < Back</h3>
            <h3 class="me-auto">Friends sent requests</h3>
        </div>`;
    } else {
        // Tạo HTML cho tiêu đề và nút "See all"
        headerHtml = `
        <div class="d-flex align-items-center">
            <h3 class="me-auto">Friends sent requests</h3>
            <span class="fs-4 text-primary custom-text" onclick="seeAllFriendRequestsBySenderId()">See all</span>
        </div>
    `;
    }

    // Khởi tạo HTML cho danh sách yêu cầu kết bạn
    let html = headerHtml + '<div class="d-flex flex-wrap">'; // Sử dụng flex-wrap để các phần tử được phân phối đều

    // Tạo HTML cho mỗi yêu cầu kết bạn
    data.forEach((request, index) => {
        // Tạo HTML cho yêu cầu kết bạn
        const requestHtml = `
        <div class="itemFriend">
            <div>
                <img class="imgCustom" src="Static/Images/${request.avt}" alt="Profile Image">
            </div>
            <h5 class="mx-3">${request.name}</h5>
            <p class="mx-3">${request.friendCount} bạn bè</p>
            <div class="d-flex justify-content-center" id="Delete${request.id}">
                <button class="btn m-1 Button" onclick="deleteRequest(${request.id},'cancel')">Cancel request</button>
            </div>
             <div class="d-flex justify-content-center" >
                <button class="btn m-1 Button d-none" disabled id="Requestaccepted${request.id}">Request accepted</button>
            </div>
        </div>
        `;

        // Thêm HTML vào chuỗi chính
        html += requestHtml;

        // Thêm thẻ <div> phân cách sau mỗi 4 phần tử
        if ((index + 1) % 4 === 0) {
            html += '</div><div class="d-flex flex-wrap">';
        }
    });

    // Đóng thẻ <div> sau khi tất cả phần tử đã được thêm
    html += '</div>';

    // Thêm toàn bộ HTML vào container
    container.html(html);
}

function getListSuggestedFriends(limit) {
    $.ajax({
        url: '/friendRequest', // URL của API
        method: 'GET',
        data: {
            action: 'getListSuggestedFriends',
            limit: limit
        },
        success: function (data) {
            // In dữ liệu trả về ra console để kiểm tra
            console.log('getListSuggestedFriends data:', data);
            // Hiển thị dữ liệu trên trang
            displaySuggestedFriends(data, limit);

        },
        error: function (error) {
            console.error('Error loading friend requests:', error);
            $('#friendRequestContainer').html('<p>Error loading friend requests.</p>');
        }
    });
}

function displaySuggestedFriends(data, limit) {
    // Lấy phần tử chứa danh sách yêu cầu kết bạn
    const container = $('#SuggestedFriends');
    // Xóa nội dung cũ
    container.empty();

    // Kiểm tra nếu dữ liệu có yêu cầu kết bạn
    if (data.length === 0) {
        container.html('<h3 class="me-auto">People you may know</h3><p>No friend requests available.</p>');
        return;
    }
    let headerHtml;
    if (limit === '40') {
        headerHtml = `<div class="d-flex align-items-center header">
            <h3 class="me-auto fs-4 custom-text1" onclick="FirstLoadFriend()"> < Back</h3>
            <h3 class="me-auto">People you may know</h3>
        </div>`;
    } else {
        // Tạo HTML cho tiêu đề và nút "See all"
        headerHtml = `
        <div class="d-flex align-items-center">
            <h3 class="me-auto">People you may know</h3>
            <span class="fs-4 text-primary custom-text" onclick="seeMoreSuggestedFriends()">See more</span>
        </div>
    `;
    }

    // Khởi tạo HTML cho danh sách yêu cầu kết bạn
    let html = headerHtml + '<div class="d-flex flex-wrap">'; // Sử dụng flex-wrap để các phần tử được phân phối đều

    // Tạo HTML cho mỗi yêu cầu kết bạn
    data.forEach((request, index) => {
        // Tạo HTML cho yêu cầu kết bạn
        const requestHtml = `
        <div class="itemFriend">
            <div>
                <img class="imgCustom" src="Static/Images/${request.avt}" alt="Profile Image">
            </div>
            <h5 class="mx-3">${request.name}</h5>
            <p class="mx-3" id="banchung${request.user_id}">${request.common_friends_count} bạn chung</p>
            <p class="fw-bold mx-3 d-none" id="textSendRequest${request.user_id}">Đã gửi lời mời</p>
            <p class="fw-bold mx-3 d-none" id="textCancelRequest${request.user_id}">Đã hủy lời mời</p>
            <div class="d-flex justify-content-center" id="Addfriend${request.user_id}">
                <button class="btn m-1 ButtonAdd"  onclick="Addfriend(${request.user_id})">Add friend</button>
            </div>
            <div class="d-flex justify-content-center" id="Cancel${request.user_id}">
        
            </div>
        </div>
        `;

        // Thêm HTML vào chuỗi chính
        html += requestHtml;

        // Thêm thẻ <div> phân cách sau mỗi 4 phần tử
        if ((index + 1) % 4 === 0) {
            html += '</div><div class="d-flex flex-wrap">';
        }
    });

    // Đóng thẻ <div> sau khi tất cả phần tử đã được thêm
    html += '</div>';

    // Thêm toàn bộ HTML vào container
    container.html(html);
}

function Addfriend(user_id) {
    $.ajax({
        url: '/friendRequest', // URL của API
        method: 'GET',
        data: {
            action: 'Addfriend',
            userIdToAdd: user_id // Thêm tham số action
        },
        dataType: 'json', // Đảm bảo jQuery tự động phân tích cú pháp JSON
        success: function (response) {
            if (response.success) {
                showButtonCancel(response.requestId, user_id);
            } else {
                // Nếu không thành công, thông báo lỗi
                alert(response.message || 'Failed to delete friend request.');
            }
        },
        error: function (error) {
            console.error('Error loading friend requests:', error);
            $('#friendRequestContainer').html('<p>Error loading friend requests.</p>');
        }
    });
}

function confirmRequest(requestid) {
    $.ajax({
        url: '/friendRequest', // URL của API
        method: 'GET',
        data: {
            action: 'confirmRequest',
            requestid: requestid // Thêm tham số action
        },
        dataType: 'json', // Đảm bảo jQuery tự động phân tích cú pháp JSON
        success: function (response) {
            // Xử lý phản hồi sau khi phân tích cú pháp JSON tự động
            if (response.success) {
                hideButtonRequest(requestid, 'deletefr');
            } else {
                // Nếu không thành công, thông báo lỗi
                alert(response.message || 'Failed to confirm friend request.');
            }
        },
        error: function (error) {
            console.error('Error loading friend requests:', error);
            $('#friendRequestContainer').html('<p>Error loading friend requests.</p>');
        }
    });
}
function deleteRequest(requestid, action) {
    $.ajax({
        url: '/friendRequest', // URL của API
        method: 'GET',
        data: {
            action: 'deleteRequest',
            requestid: requestid // Thêm tham số action
        },
        dataType: 'json', // Đảm bảo jQuery tự động phân tích cú pháp JSON
        success: function (response) {
            switch (action) {
                case 'deletefr':
                    // Xử lý phản hồi sau khi phân tích cú pháp JSON tự động
                    if (response.success) {
                        hideButtonRequest(requestid, action);
                    } else {
                        // Nếu không thành công, thông báo lỗi
                        alert(response.message || 'Failed to delete friend request.');
                    }
                    break;
                case 'cancel':
                    // Xử lý phản hồi sau khi phân tích cú pháp JSON tự động
                    if (response.success) {
                        hideButtonRequest(requestid, action);
                    } else {
                        // Nếu không thành công, thông báo lỗi
                        alert(response.message || 'Failed to delete friend request.');
                    }
                    break;
                case 'cancelAddfr':
                    // Xử lý phản hồi sau khi phân tích cú pháp JSON tự động
                    if (response.success) {
                        console.log("cancel request thanh cong");
                    } else {
                        // Nếu không thành công, thông báo lỗi
                        alert(response.message || 'Failed to delete friend request.');
                    }
                    break;
                default:
                    console.error('Unknown action:', action);
            }

        },
        error: function (error) {
            console.error('Error loading friend requests:', error);
            $('#friendRequestContainer').html('<p>Error loading friend requests.</p>');
        }
    });
}




function seeAllFriendRequest() {
    clearContentFriend();
    var limit = 'all';
    getAllFriendRequestsByReceiverId(limit);
}
function seeAllFriendRequestsBySenderId() {
    clearContentFriend();
    var limit = 'all';
    getAllFriendRequestsBySenderId(limit);
}
function seeMoreSuggestedFriends() {
    clearContentFriend();
    var limit = '40';
    getListSuggestedFriends(limit);
}
function clearContentFriend() {
    // Lấy phần tử chứa danh sách yêu cầu kết bạn
    const container = $('#friendSenderContainer');
    // Xóa nội dung cũ
    container.empty();
    // Lấy phần tử chứa danh sách da gui yêu cầu kết bạn
    const container1 = $('#friendRequestContainer');
    // Xóa nội dung cũ
    container1.empty();
    // Lấy phần tử chứa danh sách da gui yêu cầu kết bạn
    const container2 = $('#SuggestedFriends');
    // Xóa nội dung cũ
    container2.empty();

}
function hideButtonRequest(requestid, action) {
    switch (action) {
        case 'deletefr':
            var idConfirm = 'Confirm' + requestid;
            var idDelete = 'Delete' + requestid;
            var idRequestaccepted = 'Requestaccepted' + requestid;

            document.getElementById(idDelete).classList.toggle("d-none");
            document.getElementById(idConfirm).classList.toggle("d-none");
            document.getElementById(idRequestaccepted).classList.remove("d-none");
            break;
        case 'cancel':
            //If it is a cancel action, just hide the cancel button
            var idDelete = 'Delete' + requestid;
            var idRequestaccepted = 'Requestaccepted' + requestid;

            document.getElementById(idDelete).classList.toggle("d-none");
            document.getElementById(idRequestaccepted).classList.remove("d-none");
            break;
        default:
            console.error('Unknown action:', action);
    }
}
function showButtonCancel(requestId, user_id) {
    var idcontainerCancel = 'Cancel' + user_id;
    var containerCancel = document.getElementById(idcontainerCancel);
    if (containerCancel) {
        var buttonCancel = `<button class="btn m-1 Button" onclick="cancelAddrequest(${requestId}, 'cancelAddfr',${user_id})">Cancel request</button>`
        containerCancel.innerHTML = buttonCancel;
    } else {
        console.log(`Element with id ${idcontainerCancel} not found.`);
    }
    //hide button add
    document.getElementById('Addfriend' + user_id).classList.add("d-none");
    document.getElementById('textCancelRequest' + user_id).classList.add("d-none");
    document.getElementById('banchung' + user_id).classList.add("d-none");


    document.getElementById('Cancel' + user_id).classList.remove("d-none");
    document.getElementById('textSendRequest' + user_id).classList.remove("d-none");

}
function cancelAddrequest(requestId, action, user_id) {
    deleteRequest(requestId, action);
    document.getElementById('Addfriend' + user_id).classList.remove("d-none");
    document.getElementById('textCancelRequest' + user_id).classList.remove("d-none");

    document.getElementById('Cancel' + user_id).classList.add("d-none");
    document.getElementById('textSendRequest' + user_id).classList.add("d-none");

}

