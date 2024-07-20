<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/CSS/profile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <div class="header__info">
                <div class="image__container">
                    <img class="profile__avatar" alt="avatar" src="/uploads/${user.avt}">
                    <div id="editAvatar"><i class="fa-solid fa-pen"></i></div>
                </div>
                <div class="info">
                    <h2 class="info--name">${user.name}</h2>
                    <p>${friendsCount} bạn bè</p>
                    <p class="info--desc">${user.desc}</p>
                </div>
            </div>
            <div class="header__option">
                <button id="editProfileBtn"><i class="fa-solid fa-pen"></i> Chỉnh sửa thông tin cá nhân</button>
            </div>
        </header>
        <p class="separator"></p>
        <section class="content">
            <aside class="content__leftside">
                <div class="info__container">
                    <div>
                        <span class="sp-title">Ngày sinh: </span>
                        <span class="info--birth">${user.birth}</span>
                    </div>
                    <div>
                        <span class="sp-title">Số điện thoại: </span>
                        <span class="info--phone">${user.phone}</span>
                    </div>
                    <div>
                        <span class="sp-title">Email: </span>
                        <span class="info--email">${user.email}</span>
                    </div>
                    <div>
                        <span class="sp-title">Giới tính: </span>
                        <span class="info--gender">${user.gender}</span>
                    </div>
                    <div>
                        <span class="sp-title">Học vấn: </span>
                        <span class="info--education">${user.education}</span>
                    </div>
                    <div>
                        <span class="sp-title">Mối quan hệ: </span>
                        <span class="info--relationship">${user.relationship}</span>
                    </div>
                    <div>
                        <span class="sp-title">Địa chỉ: </span>
                        <span class="info--address">${user.address}</span>
                    </div>
                    <div>
                        <span class="sp-title">Mạng xã hội: </span>
                        <a class="info--social" href="${user.social}" target="_blank">${user.name}</a>
                    </div>
                </div>
                <div class="friends__container">
                    <div class="friends__container--header">
                        <div>
                            <h2>Bạn bè</h2>
                            <p>${friendsCount} bạn bè</p>
                        </div>
                        <div>
                            <button id="friendsBtn">Xem tất cả bạn bè</button>
                        </div>
                    </div>
                    <div class="friends__container--content">
                        <section class="danh-sach-ban-be">
                            <c:forEach var="friend" items="${friends}" varStatus="loop">
                                <c:if test="${loop.index < 6}">
                                    <a href="${pageContext.request.contextPath}/profile?userId=${friend.id}" class="friend__wrap">
                                        <div class="hinh-anh">
                                            <img src="/uploads/${friend.avt}" alt="Avatar">
                                        </div>
                                        <div class="thong-tin">
                                            <h3>${friend.name}</h3>
                                            <!-- <p>Hoạt động 2 giờ trước</p> -->
                                        </div>
                                    </a>
                                </c:if>
                            </c:forEach>
                        </section>
                    </div>
                </div>
            </aside>
            <main class="content__rightside">
                <c:forEach var="post" items="${posts}">
                    <div class="post" data-post-id="${post.id}">
                        <div class="post__info">
                            <div class="post__info--user">
                                <img class="profile__avatar" src="/uploads/${post.user.avt}" alt="avatar">
                                <div>
                                    <h3>${post.user.name}</h3>
                                    <small>${post.timeline}</small>
                                </div>
                            </div>
                            <div class="post__info--option">
                                <button class="btn-edit"><i class="fa-solid fa-pen"></i></button>
                                <button class="btn-delete"><i class="fa-solid fa-trash"></i></button>
                            </div>
                        </div>
                        <div class="post__desc">
                            <p>${post.content}</p>
                        </div>
                        <c:if test="${not empty post.image}">
                            <div class="post__image">
                                <img src="/uploads/${post.image}" alt="image">
                            </div>
                        </c:if>
                        <div class="post__action">
                            <div class="like-button active">
                                <button><i class="fa-regular fa-heart"></i> Thích (${fn:length(post.reactions)})</button>
                            </div>
                            <div class="unlike-button">
                                <button><i class="fa-solid fa-heart"></i> Bỏ thích (${fn:length(post.reactions)})</button>
                            </div>

                            <div>
                                <button class="commentBtn"><i class="fa-regular fa-comment"></i> Bình luận (${fn:length(post.comments)})</button>
                            </div>
                        </div>
                        <div class="post__comment display-none">
                            <div class="post__comment-input">
                                <input placeholder="Nhập bình luận..." type="text">
                                <button class="submit-comment-Btn">Gửi</button>
                            </div>
                            <div class="post__comment-list">
                                <c:forEach var="comment" items="${post.comments}">
                                    <div class="post__comment-item">
                                        <a href="${pageContext.request.contextPath}/profile?userId=${comment.user.id}"><img src="/uploads/${comment.user.avt}" alt="post__comment"></a>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/profile?userId=${comment.user.id}"><h4>${comment.user.name}</h4></a>
                                            <p>${comment.content}</p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
<%--                            <p class="comment__see-more">Xem thêm</p>--%>
                        </div>
                    </div>
                </c:forEach>
            </main>
        </section>
    </div>

    <!-- Modal xác nhận xóa -->
    <div id="deleteModal" class="modalDeletePost">
        <div class="modal-content-DeletePost">
            <p>Bạn có muốn xóa bài đăng này không ?</p>
            <div class="deleteModal-btn-wrapper">
                <button id="confirmDelete">Có</button>
                <button id="cancelDelete">Hủy</button>
            </div>
        </div>
    </div>

    <!-- The Modal see list friend-->
    <div id="friendsModal" class="modal">
        <div class="friendsModal-container">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h3>Danh sách bạn bè</h3>
                <div class="friend-list">
                    <c:forEach var="friend" items="${friends}">
                        <a href="${pageContext.request.contextPath}/profile?userId=${friend.id}" class="friend-item">
                            <img src="/uploads/${friend.avt}" alt="friend">
                            <p>${friend.name}</p>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <!-- The Modal edit profile -->
    <div id="editProfileModal2" class="modal2">
        <div class="editProfileModal2-container">
            <div class="modal-content2">
                <span class="close2" onclick="closeModal()">&times;</span>
                <h3>Chỉnh sửa thông tin cá nhân</h3>
                <form id="editProfileForm2">
                    <div class="input-wrapper">
                        <label for="name">Tên:</label>
                        <input type="text" id="name" name="name" value="${user.name}" ><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="birth">Ngày sinh:</label>
                        <input type="date" id="birth" name="birth" value="${user.birth}" ><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="phone">Số điện thoại:</label>
                        <input type="text" id="phone" name="phone" value="${user.phone}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="${user.email}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="gender">Giới tính:</label>
                        <select id="gender" name="gender" value="${user.gender}">
                            <option value="">Chọn giới tính</option>
                            <option value="Nam" ${user.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                            <option value="Nữ" ${user.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                        </select><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="desc">Mô tả:</label>
                        <textarea id="desc" name="desc">${user.desc}</textarea><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="address">Địa chỉ:</label>
                        <input type="text" id="address" name="address" value="${user.address}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="social">Mạng xã hội:</label>
                        <input type="text" id="social" name="social" value="${user.social}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="education">Học vấn:</label>
                        <input type="text" id="education" name="education" value="${user.education}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="relationship">Tình trạng quan hệ:</label>
                        <select id="relationship" name="relationship" value="${user.relationship}">
                            <option value="">Chọn tình trạng</option>
                            <option value="Độc thân" ${user.relationship == 'Độc thân' ? 'selected' : ''}>Độc thân</option>
                            <option value="Hẹn hò" ${user.relationship == 'Hẹn hò' ? 'selected' : ''}>Hẹn hò</option>
                            <option value="Đã kết hôn" ${user.relationship == 'Đã kết hôn' ? 'selected' : ''}>Đã kết hôn</option>
                        </select>
                    </div>

                    <button data-user-id="${user.id}" class="editProfileForm2-btn-submit" type="submit">Lưu</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal for editing avatar -->
    <div id="editAvatarModal" class="modalEditAvatar">
        <div class="modalEditAvatar-container">
            <div class="modal-content-EditAvatar">
                <span class="close-EditAvatar">&times;</span>
                <h2 class="editAvatar-title">Cập nhật ảnh đại diện</h2>
                <form id="editAvatarForm" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="postImage"><strong>Hình ảnh:</strong></label>
                        <input type="file" id="avatarImage" name="avatarImage">
                    </div>
                    <div class="avatar__image">
                        <img id="avatarImagePreview" src="https://c5com.com/wp/wp-content/uploads/2011/05/400x400.png" alt="avatar">
                    </div>
                    <div class="editAvatar__save-Btn--wrapper">
                        <button class="editAvatar__save-Btn" type="submit">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <!-- Modal for editing post -->
    <div id="editPostModal" class="modalEditPost">
        <div class="modalEditPost-container">
            <div class="modal-content-EditPost">
                <span class="close-EditPost">&times;</span>
                <h2 class="editPost-title">Chỉnh sửa bài viết</h2>
                <form id="editPostForm" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="postContent"><strong>Nội dung:</strong></label>
                        <textarea id="postContent" name="postContent" rows="4" cols="50"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="postImage"><strong>Hình ảnh:</strong></label>
                        <input type="file" id="postImage" name="postImage">
                    </div>
                    <div class="post__image">
                        <img id="postImagePreview" src="https://c5com.com/wp/wp-content/uploads/2011/05/400x400.png" alt="post-image">
                    </div>
                    <div class="editPost__save-Btn--wrapper">
                        <button class="editPost__save-Btn" type="submit">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/Static/JS/profile.js"></script>
</body>
</html>

