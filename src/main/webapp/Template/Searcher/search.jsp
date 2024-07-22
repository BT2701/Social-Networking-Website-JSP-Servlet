<%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 7/12/2024
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/CSS/search.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lobster&family=Open+Sans:wght@400;600&display=swap" rel="stylesheet">
</head>
<body>
<div class="search-container">
    <div class="search-left">
        <div class="search-left-title">
            <h2>Kết quả tìm kiếm</h2>
        </div>
        <div class="search-left-content">
            <h3>Bộ lọc</h3>
            <button class="btn" id="all"><i class="fa-solid fa-border-all"></i> Tất cả</button>
            <button class="btn" id="posts"><i class="fa-solid fa-newspaper"></i>Bài viết</button>
            <button class="btn" id="people"><i class="fa-solid fa-user"></i> Người dùng</button>
        </div>
    </div>
    <div class="search-content">
        <!-- user -->
        <div class="search-content-user">
            <c:forEach var="item" items="${result}">
                <div class="search-content-user-box">
                    <div class="search-content-user-box-left">
                        <img src="${pageContext.request.contextPath}/Static/Images/pizzashop.png" alt="">
                    </div>
                    <div class="search-content-user-box-mid">
                        <div class="search-content-user-box-mid-name">
                            <a href="">Truong BT</a>
                        </div>
                        <div class="search-content-user-box-mid-bonus">
                            <li>100 Bạn bè</li>
                            <li>Trường đại học Sài Gòn</li>
                        </div>
                    </div>
                    <div class="search-content-user-box-right">
                        <button class="btn btn-primary">Thêm bạn bè</button>
                    </div>
                </div>
            </c:forEach>

        </div>

        </div>
        <!-- post -->
        <div class="search-content-post">
            <div class="search-content-post-box">
                <div class="search-content-post-header">
                    <div class="search-content-post-header-left">
                        <img src="${pageContext.request.contextPath}/Static/Images/pizzashop.png" alt="">
                    </div>
                    <div class="search-content-post-header-mid">
                        <a href="">Truong BT</a>
                        <li>25/2/2023</li>
                    </div>
                    <div class="search-content-post-header-right">
                        <button class="btn"><i class="fa-solid fa-ellipsis"></i></button>
                    </div>
                </div>
                <div class="search-content-post-mid">
                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolores, dignissimos magnam eum quibusdam nemo doloremque. Nisi tempore iure accusamus facere, error facilis ullam maiores, saepe optio cupiditate, consectetur aspernatur unde.</p>
                    <img src="${pageContext.request.contextPath}/Static/Images/pizzabanner.png" alt="">
                </div>
                <div class="search-content-post-footer">
                    <div class="search-content-post-footer-top">
                        <li><i class="fa-solid fa-heart"></i> <span>you and 100 others</span></li>
                        <li>500 comments</li>
                    </div>
                    <div class="search-content-post-footer-bot">
                        <button class="btn"><i class="fa-regular fa-heart"></i>Like</button>
                        <button class="btn"><i class="fa-regular fa-comment"></i>Comment</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/56362bb265.js" crossorigin="anonymous"></script>
</body>
</html>

