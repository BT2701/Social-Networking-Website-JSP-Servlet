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
    <jsp:include page="../Header/header.jsp" />

    <div class="container-a">
        <div class="header">
            <div class="header__info">
                <div class="image__container">
                    <img class="profile__avatar" alt="avatar" src="/uploads/${user.avt}">
                    <div id="editAvatar"><i class="fa-solid fa-pen"></i></div>
                </div>
                <%
                    if(session == null) {
                        out.println("<div class=\"info\" data-user-id=\"-1\">");
                        return;
                    }

                    Object userIdObj = session.getAttribute("userId");
                    if (userIdObj != null) {
                        String userId = userIdObj.toString();
                        out.println("<div class=\"info\" data-user-id=\"" + userId + "\">");
                    }
                %>
                    <h2 class="info--name">${user.name}</h2>
                    <p>${friendsCount} friends</p>
                    <p class="info--desc">${user.desc}</p>
                </div>
            </div>
            <div class="header__option">
                <button id="editProfileBtn"><i class="fa-solid fa-pen"></i> Edit profile</button>
            </div>
        </div>
        <p class="separator"></p>
        <section class="content">
            <aside class="content__leftside">
                <div class="info__container">
                    <div>
                        <span class="sp-title">Date of birth: </span>
                        <span class="info--birth">${user.birth}</span>
                    </div>
                    <div>
                        <span class="sp-title">Phone number: </span>
                        <span class="info--phone">${user.phone}</span>
                    </div>
                    <div>
                        <span class="sp-title">Email: </span>
                        <span class="info--email">${user.email}</span>
                    </div>
                    <div>
                        <span class="sp-title">Gender: </span>
                        <span class="info--gender">${user.gender}</span>
                    </div>
                    <div>
                        <span class="sp-title">Education: </span>
                        <span class="info--education">${user.education}</span>
                    </div>
                    <div>
                        <span class="sp-title">Relationship: </span>
                        <span class="info--relationship">${user.relationship}</span>
                    </div>
                    <div>
                        <span class="sp-title">Address: </span>
                        <span class="info--address">${user.address}</span>
                    </div>
                    <div>
                        <span class="sp-title">Social network: </span>
                        <a class="info--social" href="${user.social}" target="_blank">${user.name}</a>
                    </div>
                </div>
                <div class="friends__container">
                    <div class="friends__container--header">
                        <div>
                            <h2>Friends</h2>
                            <p>${friendsCount} Friends</p>
                        </div>
                        <div>
                            <button id="friendsBtn">See all friends</button>
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
                                    <h3 class="info--name-post">${post.user.name}</h3>
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
                            <div class="like-button ${post.likedByCurrentUser ? '' : 'activeBtn'}">
                                <button><i class="fa-regular fa-heart"></i> Like (<span class="like-preview">${fn:length(post.reactions)}</span>)</button>
                            </div>
                            <div class="unlike-button ${post.likedByCurrentUser ? 'activeBtn' : ''}">
                                <button><i class="fa-solid fa-heart"></i> Unlike (<span class="unlike-preview">${fn:length(post.reactions)}</span>)</button>
                            </div>

                            <div>
                                <button class="commentBtn"><i class="fa-regular fa-comment"></i> Comment (<span class="comment-preview">${fn:length(post.comments)}</span>)</button>
                            </div>
                        </div>
                        <div class="post__comment display-none">
                            <div class="post__comment-input">
                                <input placeholder="Nhập bình luận..." type="text">
                                <button class="submit-comment-Btn">Submit</button>
                            </div>
                            <div class="post__comment-list">
                                <c:forEach var="comment" items="${post.comments}">
                                    <div class="post__comment-item">
                                        <a href="${pageContext.request.contextPath}/profile?userId=${comment.user.id}"><img src="/uploads/${comment.user.avt}" alt="post__comment"></a>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/profile?userId=${comment.user.id}">
                                                <h4 class="post__comment--user-name">${comment.user.name}</h4>
                                            </a>
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
            <p>Do you want to delete this post?</p>
            <div class="deleteModal-btn-wrapper">
                <button id="confirmDelete">Yes</button>
                <button id="cancelDelete">Cancel</button>
            </div>
        </div>
    </div>

    <!-- The Modal see list friend-->
    <div id="friendsModal" class="modal">
        <div class="friendsModal-container">
            <div class="my-modal-content">
                <span class="close">&times;</span>
                <h3>Friends List</h3>
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
                <h3>Edit personal information</h3>
                <form id="editProfileForm2">
                    <div class="input-wrapper">
                        <label for="name">Name:</label>
                        <input type="text" id="name" name="name" value="${user.name}" ><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="birth">Date of birth:</label>
                        <input type="date" id="birth" name="birth" value="${user.birth}" ><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="phone">Phone number:</label>
                        <input type="text" id="phone" name="phone" value="${user.phone}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" value="${user.email}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="gender">Gender:</label>
                        <select id="gender" name="gender" value="${user.gender}">
                            <option value="">Select gender</option>
                            <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
                            <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
                        </select><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="desc">Description:</label>
                        <textarea id="desc" name="desc">${user.desc}</textarea><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" value="${user.address}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="social">Social network:</label>
                        <input type="text" id="social" name="social" value="${user.social}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="education">Education:</label>
                        <input type="text" id="education" name="education" value="${user.education}"><br><br>
                    </div>

                    <div class="input-wrapper">
                        <label for="relationship">Relationship:</label>
                        <select id="relationship" name="relationship" value="${user.relationship}">
                            <option value="">Chọn tình trạng</option>
                            <option value="Single" ${user.relationship == 'Single' ? 'selected' : ''}>Single</option>
                            <option value="Dating" ${user.relationship == 'Dating' ? 'selected' : ''}>Dating</option>
                            <option value="Married" ${user.relationship == 'Married' ? 'selected' : ''}>Married</option>
                            <option value="Complicated" ${user.relationship == 'Complicated' ? 'selected' : ''}>Complicated</option>
                        </select>
                    </div>

                    <button class="editProfileForm2-btn-submit" type="submit">Lưu</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal for editing avatar -->
    <div id="editAvatarModal" class="modalEditAvatar">
        <div class="modalEditAvatar-container">
            <div class="modal-content-EditAvatar">
                <span class="close-EditAvatar">&times;</span>
                <h2 class="editAvatar-title">Update profile avatar</h2>
                <form id="editAvatarForm" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="postImage"><strong>Picture:</strong></label>
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
                <h2 class="editPost-title">Edit post</h2>
                <form id="editPostForm" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="postContent"><strong>Content:</strong></label>
                        <textarea id="postContent" name="postContent" rows="4" cols="50"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="postImage"><strong>Picture:</strong></label>
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

