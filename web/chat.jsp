
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : chat
    Created on : Aug 2, 2016, 4:27:50 PM
    Author     : Hachiko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="include_chat.jsp" />

        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <!--  <script src="/web/scripts/autocompleter.js"></script> -->
        <link rel="stylesheet" 
              href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
        <title></title>
    </head>
    <body onclick="hideMenu();" oncontextmenu="return false">
        <div class="wrapper">
            <div class="container">
                <div class="left">
                    <div class="top">
                        <div class="image-upload">
                            <label for="file-input">
                                <img id="profile" src="/web/img/images.png" alt=""/>
                            </label>

                            <input id="file-input" type="file" onchange = "loadProfilePicture(event)"/>
                        </div>
                        
                        <a href="javascript:;" class="search" onclick="addRoomBySearchValue()"></a>
                        <a href="javascript:;" class="adduser" onclick="addUser()"></a>
                        <a href="../action/logout" class="logout" onclick="closeSocket()"></a>
                        <input id="hidden" type="hidden" value="${user.email}">
                        <br>
                        <div class="search-container">
                            <div class="ui-widget">
                                <input id= "search" type="text" value = "Room name/User email: " name="search" class = "search" onclick="deleteInput(this)"/>
                            </div>
                        </div>
                    </div>
                    <ul class="people" style = "overflow-y: scroll; height: 400px">
                        <c:forEach var="room" items="${roomList}">
                            <li class="person" data-chat="${room.roomID}" onclick="clickRoom(this)" onmousedown="hideMenu();"
                                onmouseup="hideMenu();"
                                oncontextmenu="showMenu(this, event);">
                                
                                <input class="hiddenRoom" type="hidden" value=" ${room.roomID} ">
                                <img src="/web/img/images.png" alt="" />
                                <span class="name">${room.name}</span>
                                <span class="time">${room.messageList.get(messageList.size()).getFormatedTime()}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="right">
                    <div class="top"><span>To: <span id="who" class="name"></span></span></div>
                            <c:forEach var="room" items="${roomList}">
                        <div id="${room.roomID}" class="chat" data-chat="${room.roomID}" style="overflow-y: scroll; ">   
                            <div class="conversation-start">
                                <span>${room.messageList.get(0).getFormatedDate()}</span>
                            </div>
                            <div id="messages${room.roomID}" style="height: 300px;">
                                <c:forEach var="message" items="${room.messageList}">
                                    <c:choose>
                                        <c:when test="${message.from1.email eq user.email}">
                                            <div class ="bubble me">
                                                ${message.getContent()} <small style="color: #999; font-size: 10px;">${message.getFormatedTime()}</small>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="bubble you">
                                                ${message.getContent()} <small style="color: white; font-size: 10px;">${message.getFormatedTime()}</small>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach> 
                    <div class="write">
                        <div class="inputWrapper">
                            <input class="fileInput" type="file" name="file" id="file" onchange = "loadfiles(event)"/>
                        </div>
                        <input type="text" id="messageinput" onkeydown="sendMessageEnter(event)"/>
                        <a href="javascript:;" class="write-link send" onclick="sendMessage()"></a>
                    </div>
                </div>
            </div>
        </div>
        <div style="display:none; "   id="contextMenu">
            <table  border="0" cellpadding="0" cellspacing="0" bgcolor="white">
                <tr>
                    <td>
                        <div onclick="deleteRoom()" class="contextItem">Delete</div>
                    </td>
                </tr>
            </table>
        </div>
        <script>
            $(document).ready(function () {
                openSocket();
            });

        </script>
    </body>
</html>
