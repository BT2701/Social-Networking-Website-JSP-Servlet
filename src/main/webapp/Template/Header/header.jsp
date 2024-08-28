<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/CSS/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/CSS/base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/CSS/header.css">
</head>
<body>
<header class="text-white p-3 d-flex justify-content-between align-items-center">
  <div class="homepage-icon"><a href="homepage" id="homepage-icon"><i class="fa-brands fa-facebook" ></i></a></div>
  <div class="search-input">
    <input type="text" placeholder="search..." class="form-control">
    <a href="search"><i class="fa-solid fa-magnifying-glass mx-2 icon-M icon-affect"></i></a>
  </div>
  <div class="d-flex">
    <div class="friend-request">
      <a href="javascript:void(0);" onclick="navigateToFriendRequest()"><i class="fa-solid fa-users mx-2 icon-M icon-affect"></i></a>
    </div>
    <div class="messenger">
      <i class="fas fa-envelope mx-2 icon-M icon-affect" id="icon-messenger"></i>
      <div class="messenger-box">
        <div class="messenger-box-header">
          Chats
        </div>
        <div class="messenger-box-body custom-scrollbar">
          <div class="inbox">
            <img src="../../Static/Images/pizzabanner.png" alt="Contact 1"
                 class="rounded-circle border border-1 me-3" width="40" height="40">
            <div class="inbox-relative">
              <div class="friend-name">
                <span>Nguyen Nhat Truong</span>
              </div>
              <div class="newest-chat">
                <span class="truncate-1line">Xi di choi bida khong, mia nay chan vl</span>
              </div>
            </div>
          </div>
        </div>
        <div class="messenger-box-footer text-center">
          <button>See all chats</button>
        </div>
      </div>
    </div>

    <div class="notification">
      <div class="notification__icon-wrapper">
        <i class="fas fa-bell mx-2 icon-M icon-affect" id="icon-notification" data-user-id="${userId}"></i>
        <div class="notification-quantity">${notificationsQuantity}</div>
      </div>

      <div class="notification-box">
        <div class="notification-box-header">Notification</div>
        <div class="notification-box-body custom-scrollbar">
          <c:forEach var="notification" items="${notifications}">
            <div class="notification-item d-flex ${notification.is_read == 1 ? '' : 'notification-unread'}">
              <div class="notification__image-container">
                <img src="/uploads/${notification.user.avt}" alt="Contact 1">
              </div>
              <div class="notification__infor">
                <div class="notification__infor-user-name" >${notification.user.name}</div>
                <div class="notification-content truncate-2line">
                    ${notification.content}
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>

    </div>

    <div class="user-operation" onclick="displayUserOperator()">
      <i class="fas fa-user mx-2 icon-M icon-affect" id="icon-user-operation"></i>
      <ul class="user-operation-box">
        <li class="user-operation-box-item">
          <i class="fa-regular fa-user"></i>
          <%
            if(session == null) {
              out.println("<a href=\"login\"><span>Login</span></a>");
              return;
            }

            Object userIdObj = session.getAttribute("userId");
            if (userIdObj != null) {
              String userId = userIdObj.toString();
              out.println("<a href=\"profile?userId=" + userId + "\"><span>Profile</span></a>");
            }
          %>
        </li>
        <li class="user-operation-box-item">
          <i class="fa-solid fa-right-from-bracket"></i>
          <a href="login"><span>Logout</span></a>
        </li>
        <li class="user-operation-box-item">
          <i class="fa-solid fa-gear"></i>
          <span>Setting</span>
        </li>
      </ul>
    </div>
  </div>
</header>
<script src="${pageContext.request.contextPath}/Static/JS/header.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="../../Static/JS/home.js"></script>
</body>
</html>
