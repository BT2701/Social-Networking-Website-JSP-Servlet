<%@ page import="java.util.List" %>
<%@ page import="org.example.j2ee.Model.User" %><%--
  Created by IntelliJ IDEA.
  User: duong
  Date: 7/12/2024
  Time: 4:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
<jsp:include page="../Header/header.jsp" />
<div class="search-container" id="search-container">
    <div class="search-left">
        <div class="search-left-title">
            <h2>Search Results</h2>
        </div>
        <div class="search-left-content">
            <h3>Filters</h3>
            <button class="btn" datatype="all"><i class="fa-solid fa-border-all"></i> All</button>
            <button class="btn" datatype="post"><i class="fa-solid fa-newspaper"></i>Post</button>
            <button class="btn" datatype="user"><i class="fa-solid fa-user"></i> People</button>
        </div>
    </div>
    <div class="search-content" id="search-content">
        <!-- user -->
        <div class="search-content-user">
            <c:if test="${users.size() !=0}">
                <c:forEach var="item" items="${users}">
            <div class="search-content-user-box">
                <div class="search-content-user-box-left">
                    <img src="${pageContext.request.contextPath}/Static/Images/${item.avt}" alt="">
                </div>
                <div class="search-content-user-box-mid">
                    <div class="search-content-user-box-mid-name">
                        <a href="${pageContext.request.contextPath}/profile?userId=${item.id}">${item.name}</a>
                    </div>
                    <div class="search-content-user-box-mid-bonus">
                        <c:set var="friendNum" value="0 Friend"></c:set>
                        <c:if test="${item.friendsCount  <2}">
                            <c:set var="friendNum" value="${item.friendsCount} Friend"></c:set>
                        </c:if>
                        <c:if test="${item.friendsCount > 1}">
                            <c:set var="friendNum" value="${item.friendsCount} Friends"></c:set>
                        </c:if>
                        <li>${friendNum}</li>
                        <c:set var="education" value="Unknow"></c:set>
                        <c:if test="${item.education != null && item.education !=''}">
                            <c:set var="education" value="${item.education}"></c:set>
                        </c:if>
                        <li>${education}</li>
                    </div>
                </div>
                <div class="search-content-user-box-right">
                    <c:set var="text" value="Add friend" />
                    <c:if test="${friends.size() != 0}">
                        <c:forEach var="friend" items="${friends}">
                            <c:if test="${item.id == friend.id}">
                                <c:set var="text" value="Remove friend" />
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${responseStack.size() != 0}">
                        <c:forEach var="responseItem" items="${responseStack}">
                            <c:if test="${item.id == responseItem.sender}">
                                <c:set var="text" value="Confirm" />
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${requestList.size() != 0}">
                        <c:forEach var="requestItem" items="${requestList}">
                            <c:if test="${item.id == requestItem.receiver}">
                                <c:set var="text" value="Cancel request" />
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <c:if test="${text.equals('Confirm')}">
                        <button class="btn btn-warning refuse" data-friend-id="${item.id}">Refuse</button>
                    </c:if>
                    <button class="btn btn-primary handle-request" data-friend-id="${item.id}">${text}</button>
                </div>

            </div>
                </c:forEach>
            </c:if>
        </div>
        <!-- post -->
        <div class="search-content-post">
            <c:if test="${posts.size() !=0}">
                <c:forEach var="item" items="${posts}">
            <div class="search-content-post-box">
                <div class="search-content-post-header">
                    <div class="search-content-post-header-left">
                        <img src="${pageContext.request.contextPath}/Static/Images/${item.user.avt}" alt="">
                    </div>
                    <div class="search-content-post-header-mid">
                        <a href="${pageContext.request.contextPath}/profile?userId=${item.user.id}">${item.user.name}</a>
                        <li>${item.getTimePost()}</li>
                    </div>
                    <div class="search-content-post-header-right">
                        <c:if test="${item.owner}">
                        <button class="btn"><i class="fa-solid fa-ellipsis"></i></button>
                            <div class="search-handle-post" style="display: block">
                                <button class="search-handle-edit btn">
                                    <i class="fa-solid fa-pen-to-square"></i> Edit
                                </button>
                                <button class="search-handle-delete btn">
                                    <i class="fa-solid fa-trash"></i> Delete
                                </button>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="search-content-post-mid">
                    <p>${item.content}</p>
                    <img src="${pageContext.request.contextPath}/Static/Images/${item.image}" alt="">
                </div>
                <div class="search-content-post-footer">
                    <div class="search-content-post-footer-top">
                        <c:set var="numCmts" value="0 Comment"></c:set>
                        <c:if test="${item.getNumComments()<2}">
                            <c:set var="numCmts" value="${item.getNumComments()} Comment"></c:set>
                        </c:if>
                        <c:if test="${item.getNumComments()>1}">
                            <c:set var="numCmts" value="${item.getNumComments()} Comments"></c:set>
                        </c:if>
                        <c:set var="numReactions" value="0 Like"></c:set>
                        <c:if test="${item.getNumReactions()<2}">
                            <c:set var="numReactions" value="${item.getNumReactions()} Like"></c:set>
                        </c:if>
                        <c:if test="${item.getNumReactions()>1}">
                            <c:set var="numReactions" value="${item.getNumReactions()} Likes"></c:set>
                        </c:if>
                        <li><i class="fa-solid fa-heart"></i> <span>${numReactions}</span></li>
                        <li>${numCmts}</li>
                    </div>
                    <div class="search-content-post-footer-bot">
                        <button class="btn"><i class="fa-regular fa-heart"></i>Like</button>
                        <button class="btn"><i class="fa-regular fa-comment"></i>Comment</button>
                    </div>
                </div>
            </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://kit.fontawesome.com/56362bb265.js" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/Static/JS/search.js"></script>
</body>
</html>