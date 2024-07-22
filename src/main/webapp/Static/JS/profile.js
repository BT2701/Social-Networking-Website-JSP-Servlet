let postToDelete = null;
let postToUpdate = null;

// edit post
const modalEditPost = document.getElementById('editPostModal');
const modalEditPostContent = document.querySelector('.modal-content-EditPost');
const spanEditPost = document.querySelector(".close-EditPost");
const postImageInput = document.getElementById('postImage');
const postImagePreview = document.getElementById('postImagePreview');

// delete post
const modal = document.getElementById("deleteModal");
const modalContent = document.querySelector(".modal-content-DeletePost");
const cancelDelete = modal.querySelector("#cancelDelete");

document.querySelectorAll('.post').forEach(post => {
    const likeButton = post.querySelector('.like-button');
    const unlikeButton = post.querySelector('.unlike-button');

    likeButton.addEventListener('click', () => {
        likeButton.classList.remove('active');
        unlikeButton.classList.add('active');
    });

    unlikeButton.addEventListener('click', () => {
        unlikeButton.classList.remove('active');
        likeButton.classList.add('active');
    });

    // modal edit post
    const editPostButton = post.querySelector('.btn-edit');

    modalEditPost.addEventListener('click', () => {
        modalEditPost.style.display = "none";
        postImagePreview.src = "";
        postToUpdate = null;
    });

    modalEditPostContent.addEventListener('click', (e) => {
        e.stopPropagation();
    });

    editPostButton.addEventListener('click', (e) => {
        // const postId = post.getAttribute('data-post-id');
        const postContent = post.querySelector('.post__desc p').innerText;
        const postImage = post.querySelector('.post__image img') ? post.querySelector('.post__image img').src : '';

        document.getElementById('postContent').value = postContent;
        postImagePreview.src = postImage;

        postToUpdate = post;
        modalEditPost.style.display = "block";
    });

    spanEditPost.onclick = function () {
        postToUpdate = null;
        modalEditPost.style.display = "none";
        postImagePreview.src = "";
    };

    postImageInput.addEventListener('change', (event) => {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                postImagePreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });

    // comment
    const comment = post.querySelector(".post__comment");
    const commentBtn = post.querySelector(".commentBtn");
    commentBtn.addEventListener('click', (e) => {
        comment.classList.toggle("display-none");
    })

    //delete post
    const deleteBtn = post.querySelector(".btn-delete");
    modal.addEventListener('click', () => {
        postToDelete = null;
        modal.style.display = "none";
    });
    modalContent.addEventListener('click', (e) => {
        e.stopPropagation();
    });

    deleteBtn.addEventListener('click', () => {
        postToDelete = post;
        modal.style.display = "block";
    });

    cancelDelete.addEventListener('click', () => {
        modal.style.display = "none";
        postToDelete = null;
    });
});
// delete post
const confirmDelete = document.querySelectorAll("#confirmDelete");
confirmDelete.forEach(cfDl => {
    cfDl.addEventListener('click', () => {
        document.getElementById("deleteModal").style.display = "none";

        if (postToDelete) {
            const postId = postToDelete.getAttribute("data-post-id");

            fetch(`/api/post?postId=${postId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        postToDelete.remove();
                        postToDelete = null;
                    } else if (response.status === 404) {
                        alert("Post not found");
                    } else {
                        alert("An error occurred while deleting the post");
                    }
                })
                .catch(error => console.error('Error:', error));
        }
    });
})

// edit post
const btnSaveEditPost = document.querySelector(".editPost__save-Btn");
const formEditPost = document.getElementById("editPostForm");

btnSaveEditPost.addEventListener('click', (e) => {
    e.preventDefault();

    const content = document.getElementById("postContent").value;
    if (content === "") {
        alert("Nội dung không được để trống.");
        return;
    }

    const file = document.getElementById('postImage').files[0];
    const formData = new FormData();
    formData.append("postContent", content);

    if (file) {
        const maxSize = 5 * 1024 * 1024; // 5MB
        if (file.size > maxSize) {
            alert("Kích thước tệp vượt quá giới hạn 5MB.");
            return;
        }
        formData.append("postImage", file);
    }

    const postId = postToUpdate.getAttribute("data-post-id");
    fetch(`/api/post?postId=${postId}`, {
        method: 'PUT',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            if (data.message === "Upload successful") {
                modalEditPost.style.display = "none";
                postImagePreview.src = "";

                postToUpdate.querySelector(".post__desc p").innerText = data.content;
                if (data.fileName) {
                    postToUpdate.querySelector(".post__image img").src = `${window.location.origin}/uploads/${data.fileName}`;
                }
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            alert("Error: " + (error.message || "An unexpected error occurred"));
            console.error('Error:', error);
        });
});

// modal see all friends
const modalFriends = document.getElementById("friendsModal");
const btn = document.getElementById("friendsBtn");
const span = document.getElementsByClassName("close")[0];
const modalFriendsContainer = document.querySelector(".friendsModal-container");
const modalFriendsContent = document.querySelector(".modal-content");

modalFriendsContainer.onclick = () => {
    modalFriends.style.display = "none";
}

modalFriendsContent.onclick = function(e) {
    e.stopPropagation();
}

btn.onclick = function() {
    modalFriends.style.display = "block";
}

span.onclick = function() {
    modalFriends.style.display = "none";
}


// modal edit profile
const modalContainer = document.querySelector(".editProfileModal2-container");
const modalEditProfileContent = document.querySelector(".modal-content2");
const modalEditProfile = document.getElementById("editProfileModal2");
const btnEditProfile = document.getElementById("editProfileBtn");
const spanEditProfile = document.getElementsByClassName("close2")[0];

modalContainer.onclick = () => {
    modalEditProfile.style.display = "none";
}

modalEditProfileContent.onclick = function(e) {
    e.stopPropagation();
}

btnEditProfile.onclick = function() {
    modalEditProfile.style.display = "block";
}

spanEditProfile.onclick = function() {
    modalEditProfile.style.display = "none";
}

const editProfileForm = document.getElementById('editProfileForm2');
const userId = document.querySelector(".editProfileForm2-btn-submit").getAttribute("data-user-id");
editProfileForm.onsubmit = function(event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const birth = document.getElementById("birth").value;
    const phone = document.getElementById("phone").value;
    const email = document.getElementById("email").value;
    const desc = document.getElementById("desc").value;
    const address = document.getElementById("address").value;
    const social = document.getElementById("social").value;
    const gender = document.getElementById("gender").value;
    const relationship = document.getElementById("relationship").value;
    const education = document.getElementById("education").value;

    if (phone === "" || email === "" || desc === "" || address === "" || social === "" || education === "" || !birth) {
        alert("Tất cả các trường không được để trống.");
        return;
    }

    if (name.length < 5 && name.length > 50) {
        alert("Tên phải từ 5 chữ và tối đa là 50 chữ.");
        return;
    }

    // Kiểm tra số điện thoại
    if (!/^\d{10}$/.test(phone)) {
        alert("Số điện thoại phải đúng 10 số.");
        return;
    }

    // Kiểm tra định dạng email
    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
        alert("Email không đúng định dạng.");
        return;
    }

    // Kiểm tra chọn giới tính không được rỗng
    if (gender === "") {
        alert("Vui lòng chọn giới tính.");
        return;
    }

    // Kiểm tra mô tả tối đa 100 chữ
    if (desc.length > 100) {
        alert("Mô tả được tối đa 100 chữ.");
        return;
    }

    // Kiểm tra địa chỉ tối đa 50 chữ
    if (address.length > 50) {
        alert("Địa chỉ được tối đa 50 chữ.");
        return;
    }

    // Kiểm tra mạng xã hội tối đa 50 chữ
    if (social.length > 50) {
        alert("Mạng xã hội được tối đa 50 chữ.");
        return;
    }

    // Kiểm tra chọn tình trạng mối quan hệ không được rỗng
    if (relationship === "") {
        alert("Vui lòng chọn tình trạng mối quan hệ.");
        return;
    }

    if (education.length < 5 && education.length > 50) {
        alert("Tên trường phải từ 5 chữ và tối đa là 50 chữ.");
        return;
    }

    const birthDate = new Date(birth);
    const today = new Date();
    // Đặt thời gian của ngày hiện tại thành 00:00:00 để chỉ so sánh ngày
    today.setHours(0, 0, 0, 0);
    // So sánh ngày sinh với ngày hiện tại
    if (birthDate >= today) {
        alert("Ngày sinh không thể là ngày hôm nay hoặc một ngày trong tương lai.");
        return;
    }

    const formData = new FormData(editProfileForm);

    fetch(`/api/profile/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(Object.fromEntries(formData.entries()))
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
        })
        .then(data => {
            document.querySelector(".info--name").textContent = name;
            document.querySelector(".info--desc").textContent = desc;
            document.querySelector(".info--gender").textContent = gender;
            document.querySelector(".info--email").textContent = email;
            document.querySelector(".info--phone").textContent = phone;
            document.querySelector(".info--education").textContent = education;
            document.querySelector(".info--relationship").textContent = relationship;
            document.querySelector(".info--social").href = social;
            document.querySelector(".info--address").textContent = address;
            document.querySelector(".info--birth").textContent = birth;

            alert("Cập nhật thông tin thành công!");
            modalEditProfile.style.display = "none";
            // location.reload();
        })
        .catch(error => {
            console.log(`Có lỗi xảy ra: ${error.message}`);
        });
}


// chỉnh tên trong friends
document.addEventListener("DOMContentLoaded", function() {
    const listNameFriend = document.querySelectorAll(".friend__wrap .thong-tin h3");
    listNameFriend.forEach((nameFriend) => {
        if(nameFriend.textContent.length > 12) {
            nameFriend.textContent = nameFriend.textContent.substring(0, 12) + "...";
        }
    });
});


// modal edit avatar
const editAvatarButton = document.getElementById('editAvatar');
const modalEditAvatar = document.getElementById('editAvatarModal');
const modalEditAvatarContent = document.querySelector('.modal-content-EditAvatar');
const spanEditAvatar = document.querySelector(".close-EditAvatar");
const avatarImageInput = document.getElementById('avatarImage');
const avatarImagePreview = document.getElementById('avatarImagePreview');
const btnSaveEditAvatar = document.querySelector(".editAvatar__save-Btn");
const formEditAvatar = document.getElementById("editAvatarForm");

btnSaveEditAvatar.addEventListener('click', (e) => {
    e.preventDefault();
    const file = avatarImageInput.files[0];
    const maxSize = 5 * 1024 * 1024; // 5MB

    if (!file) {
        alert("Vui lòng chọn một tệp.");
        return;
    }
    if (file.size > maxSize) {
        alert("Kích thước tệp vượt quá giới hạn 5MB.");
        return;
    }

    const formData = new FormData(formEditAvatar);
    fetch(`/api/uploadAvatar?userId=${userId}`, {
        method: 'POST',
        body: formData
    })
            .then(response => response.json())
            .then(data => {
                if (data.message === "Upload successful") {
                    // alert("Sửa thành công! " + `${window.location.origin}/uploads/${data.fileName}`);
                    modalEditAvatar.style.display = "none";
                    avatarImagePreview.src = "";
                    document.querySelectorAll(".profile__avatar").forEach(ava => {
                        ava.src = `${window.location.origin}/uploads/${data.fileName}`;
                    })
                } else {
                    alert(data.message);
                }
            })
            .catch(error => console.error('Error:', error));
});

modalEditAvatar.addEventListener('click', () => {
    modalEditAvatar.style.display = "none";
    avatarImagePreview.src = "";
});

modalEditAvatarContent.addEventListener('click', (e) => {
    e.stopPropagation();
});

editAvatarButton.addEventListener('click', () => {
    avatarImagePreview.src = document.querySelector(".profile__avatar").src;

    modalEditAvatar.style.display = "block";
});

spanEditAvatar.onclick = function () {
    modalEditAvatar.style.display = "none";
    avatarImagePreview.src = "";
};

avatarImageInput.addEventListener('change', (event) => {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            avatarImagePreview.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});


