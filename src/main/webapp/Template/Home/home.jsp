<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>MySocial</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
                <link rel="stylesheet" href="../../Static/CSS/home.css">
                <link rel="stylesheet" href="../../Static/CSS/base.css">
            </head>

            <body>
                <jsp:include page="../Header/header.jsp" />

                <main class="container mt-4 mr-1">
                    <div class="row">
                        <div class="col-md-8 d-flex">
                            <div class="col-md-4 navigation">

                            </div>
                            <div class="col-md-8">
                                <!-- Main feed content -->
                                <section class="post-form mb-4 d-flex">
                                    <img src="/uploads/${existingUser.avt}" alt="Contact 3"
                                        class="rounded-circle border border-1  me-3" width="40" height="40">
                                    <input id="postContent" data-bs-toggle="modal" data-bs-target="#formCreatePost"
                                        class="form-control rounded-pill" rows="3"
                                        placeholder="What's on your mind?"></input>
                                </section>
                                <section class="feed" id="feed">
                                    <!-- Posts will appear here -->
                                    <c:forEach var="post" items="${posts}">
                                        <div class="post" data-post-id="${post.id}">
                                            <div class="post-header">
                                                <div class="avt-user">
                                                    <img src="/uploads/${post.user.avt}" alt="Contact 1"
                                                        class="rounded-circle border border-1 me-3" width="40"
                                                        height="40">
                                                </div>
                                                <div class="info-relative">
                                                    <div class="user-name">
                                                        <span>${post.user.name}</span>
                                                    </div>
                                                    <div class="posting-time">${post.timeline}</div>
                                                </div>


                                            </div>
                                            <div class="post__desc">
                                                <p>${post.content}</p>
                                            </div>
                                            <div class="post-body">
                                                <c:if test="${not empty post.image}">
                                                    <div class="post__image">
                                                        <img src="/uploads/${post.image}" alt="image">
                                                    </div>
                                                </c:if>
                                                <!-- <div class="overlay">
                                                    <div class="number-hidden-item">
                                                        +6
                                                    </div>
                                                </div> -->
                                            </div>
                                            <div class="post-footer">
                                                <div class="like-action">
                                                    <span class="numberLike">
                                                        ${fn:length(post.reactions)}
                                                    </span>
                                                    <button class="like-button ${post.likedByCurrentUser ? '' : 'appear'}"> <i
                                                            class="fa-regular fa-heart icon-L"></i></button>
                                                    <button class="unlike-button ${post.likedByCurrentUser ? 'appear' : ''}">
                                                        <i class="fa-solid fa-heart icon-L heart-red"></i></button>
                                                </div>
                                                <div class="comment-action">
                                                    <span class="numberComment">
                                                        ${fn:length(post.comments)}
                                                    </span>
                                                    <button class="comment-button"> <i
                                                            class="fa-regular fa-comment icon-L"></i> </button>
                                                </div>

                                            </div>
                                            <div class="comment-section">

                                                <div class="post__comment-input">
                                                    <input placeholder="Nhập bình luận..." type="text">
                                                    <button class="submit-comment-Btn">Submit</button>
                                                </div>
                                                <div class="post__comment-list">
                                                    <c:forEach var="comment" items="${post.comments}">
                                                        <div class="post__comment-item">
                                                            <a
                                                                href="${pageContext.request.contextPath}/profile?userId=${comment.user.id}"><img
                                                                    src="/uploads/${comment.user.avt}"
                                                                    alt="post__comment"></a>
                                                            <div>
                                                                <a
                                                                    href="${pageContext.request.contextPath}/profile?userId=${comment.user.id}">${comment.user.name}</a>
                                                                <p>${comment.content}</p>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </section>
                            </div>
                        </div>

                    </div>
                    <div class="col-md-4">
                        <!-- Contacts and group chats -->
                        <section class="contacts custom-scrollbar mb-4">
                            <h4>Contacts</h4>
                            <ul class="list-group">
                                <li class="list-group-item d-flex align-items-center border-0" id="user1"
                                    onclick="appearChatBox()">
                                    <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                        class="rounded-circle border border-1 me-3" width="40" height="40">
                                    <span class="rounded-circle border border-1 active"></span>
                                    <span>Nguyen Nhat Truong</span>
                                </li>
                                <li class="list-group-item d-flex align-items-center border-0">
                                    <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                        class="rounded-circle border border-1 me-3" width="40" height="40">
                                    <span>Nguyen Nhat Truong</span>
                                </li>

                            </ul>
                            <hr>
                            <h4>Group chat</h4>
                            <ul class="list-group">
                                <li class="list-group-item d-flex align-items-center border-0">
                                    <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                        class="rounded-circle border border-1 me-3" width="40" height="40">
                                    <span class="rounded-circle border border-1 active"></span>
                                    <span>Group fintech</span>
                                </li>
                                <li class="list-group-item d-flex align-items-center border-0">
                                    <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                                        class="rounded-circle border border-1 me-3" width="40" height="40">
                                    <span class="rounded-circle border border-1"></span>
                                    <span>Group fintech</span>
                                </li>

                                <li class="list-group-item d-flex align-items-center border-0">
                                    <i
                                        class="fa-solid fa-plus rounded-circle border border-1 me-2 p-2 ms-1 bg-secondary"></i>
                                    <span>Create new group</span>
                                </li>
                            </ul>
                        </section>
                    </div>

                    <!-- MODAL FORM CREATE POST -->
                    <div class="modal fade" id="formCreatePost" tabindex="-1" aria-labelledby="formCreatePostLabel"
                        aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title text-center" id="formCreatePostLabel">Create post</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="creator d-flex">
                                        <div class="avt-user">
                                            <img src="/uploads/${existingUser.avt}" alt="Contact 1"
                                                class="rounded-circle border border-1 me-3" width="40" height="40">
                                        </div>
                                        <div class="info-relative">
                                            <div class="name-user">
                                                <span>${existingUser.name}</span>
                                            </div>
                                            <div class="post-access">
                                                <button class="btn btn-secondary dropdown-toggle" type="button"
                                                    data-bs-toggle="dropdown" aria-expanded="false">
                                                    <i class="fa-solid fa-lock"></i>
                                                    Private
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <li><a class="dropdown-item" href="#">

                                                            <i class="fa-solid fa-earth-americas"></i>
                                                            Public
                                                        </a></li>
                                                    <li><a class="dropdown-item" href="#">
                                                            <i class="fa-solid fa-user-group"></i>
                                                            Friends
                                                        </a></li>
                                                    <li><a class="dropdown-item" href="#">
                                                            <i class="fa-solid fa-lock"></i>
                                                            Private
                                                        </a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                    <textarea class="status w-100 border-0" name="" id=""
                                        placeholder="What's on your mind?" rows="3"></textarea>
                                    <div class="contain-img-video">
                                    </div>
                                    <div class="addition d-flex border borde-1 p-2 rounded">
                                        <span class="me-3">Add photo/video</span>
                                        <i class="fa-regular fa-image icon-L" id="chooseFileIcon"></i>
                                        <input type="file" id="fileInput" accept="image/*">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary w-100"
                                        onclick="getDataModalCreatePost()">Post</button>
                                </div>

                            </div>
                        </div>
                    </div>

                    <div class="chat-box one-on-one" id="chatbox">
                        <div class=" chat-header">
                            <img src="../../Static/Images/pizzashop.png" alt="User Profile" class="profile-pic">
                            <span class="user-name">John Doe</span>
                            <i class="fas fa-times close-chat icon" title="Close"></i> <!-- Close icon -->
                        </div>
                        <div class="chat-messages">
                            <!-- Messages will be dynamically inserted here -->
                        </div>
                        <div class="chat-input">
                            <input type="text" placeholder="Type a message...">
                            <button>Send</button>
                        </div>
                    </div>
                </main>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script src="../../Static/JS/home.js"></script>
                <!--        xu ly add friend request vao main-->
                <script>
                    function loadFriendRequest() {
                        const mainContent = $('main');
                        $.ajax({
                            url: '/friendRequest',
                            method: 'GET',
                            success: function (data) {
                                mainContent.html(data);
                            },
                            error: function (error) {
                                console.error('Error loading friend request content:', error);
                            }
                        });
                    }

                    function navigateToFriendRequest() {
                        // Thay ??i URL m? kh?ng t?i l?i trang
                        history.pushState(null, '', '/friendRequest');
                        // T?i n?i dung friend_request.jsp v?o v?ng main
                        loadFriendRequest();
                    }

                    $(document).ready(function () {
                        // Ki?m tra URL v? t?i n?i dung friend request n?u URL kh?p
                        if (window.location.pathname === '/friendRequest') {
                            loadFriendRequest();
                        }

                        // X? l? n?t quay l?i ho?c ti?n t?i c?a tr?nh duy?t
                        window.onpopstate = function () {
                            if (window.location.pathname === '/friendRequest') {
                                loadFriendRequest();
                            } else {
                                // T?i l?i trang n?u URL kh?ng kh?p
                                location.reload();
                            }
                        };
                    });

                </script>
            </body>

            </html>