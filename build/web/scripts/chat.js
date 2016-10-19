/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var webSocket;
var userEmail;
var activeRoomID;
var activeRoomName;
var listOfRooms;
var clicked;

function openSocket() {
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        return;
    }
    webSocket = new WebSocket("ws://localhost:8080/web/chat");

    webSocket.onopen = function (event) {

        var content =
                {
                    "@class": "domain.TextMessage",
                    "text": ""
                };

        setStartParametres();
        send("LOGIN", content, activeRoomID, userEmail);

        //vidi sta ovo zapravo radi i skloni ga posto kapiram da ne radi nista
        if (event.data === undefined)
            return;
    };

    webSocket.onmessage = function (event) {
        writeResponse(event.data);
    };

    webSocket.onclose = function (event) {
        console.log(event.data);
    };
}

function send(type, text, roomID, email) {

    var sends =
            {
                "messageID": null,
                "type": type,
                "content": text,
                "from1":
                        {
                            "email": email,
                            "nickname": null,
                            "password": null,
                            "roomList": null,
                            "messageList": null,
                            "session": null
                        },
                "in1":
                        {
                            "roomID": roomID,
                            "name": null,
                            "userList": null,
                            "messageList": null
                        },
                "received": null
            };

    webSocket.send(JSON.stringify(sends));
}

function closeSocket() {
    webSocket.close();
}

function writeResponse(text) {
    var chatMessage = JSON.parse(text);
    console.log(chatMessage.type);
    if (chatMessage.type === "ADD_USER_TO_ROOM") {
        var userAdded = chatMessage.from1.email;

        if (userAdded === userEmail) {
            //potpuno nova soba za dodatog usera
            makeNewRoom(chatMessage.in1);
            return;
        } else {
            //salje poruku user added to room
            alertMessage(chatMessage);
        }
    }
    if (chatMessage.type === "CREATE_ROOM") {
        makeNewRoom(chatMessage.in1);
    }
    if (chatMessage.type === "DELETE_ROOM") {
        var sender = chatMessage.from1.email;

        if (sender === userEmail) {
            //metoda delete room
            //deleteRoom();
        } else {
            //salje poruku user left room
            //ako je privatna konverzacija ne salje nista proveri posle
            alertMessage(chatMessage);
        }
    }
    if (chatMessage.type === "SEND_MESSAGE") {
        //ispisuje primljenu textualnu poruku
        newMessage(chatMessage);
    }
    if (chatMessage.type === "SEND_PICTURE") {
        //metoda za ispisivanje slike
        newPicture(chatMessage);
    }
    if (chatMessage.type === "SEND_FILE") {
        //metoda za ispisivanje fajla
        newFile(chatMessage);
    }
}

function addRoomBySearchValue() {
    var roomName = document.getElementById("search").value;

    if (roomName === "" || roomName === "Room name/User email: ")
        return;

    var room = {
        "roomID": 55,
        "name": roomName,
        "userList": [userEmail],
        "messageList": []
    };

    var content = {
        "@class": "domain.TextMessage",
        "text": ""
    }

    send("CREATE_ROOM", content, room, userEmail);
    //kontaktiraj server a ne direktno make room
    //makeNewRoom(room);
}

function makeNewRoom(room) {
    //proveri da li postoji vec ta soba (al ne ovde, samo se seti)
    document.getElementById("who").innerHTML = room.name;

    var li = document.createElement("li");
    li.className = "person active";
    li.dataset.chat = room.roomID;

    li.onclick = function () {
        clickRoom(li);
    };

    li.onmousedown = function () {
        hideMenu();
    };

    li.onmouseup = function () {
        hideMenu();
    };

    li.oncontextmenu = function () {
        showMenu(this, event);
    };

    var text1 = document.createElement("text");
    var text2 = document.createElement("text");
    var text3 = document.createElement("text");
    var text4 = document.createElement("text");
    var text5 = document.createElement("text");

    var input = document.createElement("input");
    input.className = "hiddenRoom";
    input.type = "hidden";
    input.value = room;

    var img = document.createElement("img");
    img.src = "/web/img/images.png";
    img.alt = "";

    var span = document.createElement("span");
    span.className = "name";
    span.innerHTML = room.name;

    var createdAt = new Date();
    var date = formatDate(createdAt, "MINUTES");

    var span2 = document.createElement("span");
    span2.className = "time";
    span2.innerHTML = date;

    changeActive();

    var right = document.getElementsByClassName("right")[0];
    var div = document.createElement("div");
    div.id = room.roomID;
    div.className = "chat active-chat";
    div.dataset.chat = room.roomID;

    var innerDiv = document.createElement("div");
    innerDiv.className = "conversation-start";

    var date = formatDate(createdAt, "MINUTES");

    var s = document.createElement("span");
    s.innerHTML = date;

    var messageDiv = document.createElement("div");
    messageDiv.id = "messages" + room.roomID;

    right.insertBefore(div, right.childNodes[right.childElementCount + 3]);

    li.appendChild(text1);
    li.appendChild(input);
    li.appendChild(text2);
    li.appendChild(img);
    li.appendChild(text3);
    li.appendChild(span);
    li.appendChild(text4);
    li.appendChild(span2);
    li.appendChild(text5);

    listOfRooms.appendChild(li);

    innerDiv.appendChild(s);

    div.appendChild(innerDiv);
    div.appendChild(messageDiv);
    //send("", "CREATE_ROOM", room.name, 0, "");
}
//ovo nije dobro
function addUserToRoom() {
    //u doradi, bice drugi input koji se pojavljuje kada se klikne add context menu
    var userToAdd = document.getElementById("search").value;

    if (userToAdd === "" || userToAdd === "Room name/User email: ") {
        return;
    }

    var liElements = document.getElementsByClassName("ui-autocomplete")[0].childNodes;
    var correct = false;
    for (i = 0; i < liElements.length; i++) {
        if (liElements[i].childNodes[0].innerHTML === user) {
            correct = true;
        }
    }

    if (!correct)
        alertMessage("Incorrect user email");

    var content =
            {
                "@class": "domain.TextMessage",
                "text": ""
            }
    send("ADD_USER_TO_ROOM", content, null, userToAdd);
}

function sendMessage() {
    var message = document.getElementById("messageinput").value;

    if (message === "")
        return;

    var room = document.getElementById("who").innerHTML;

    if (room === "")
        return;

    var messageList = document.getElementById("messages" + activeRoomID);

    var formated = formatDate(new Date(), "MINUTES");

    var div = document.createElement("div");
    div.className = "bubble me";
    div.innerHTML = message + '<small style="color: #999; font-size: 10px;">' + formated + '</small>';
    messageList.appendChild(div);

    var content =
            {
                "@class": "domain.TextMessage",
                "text": message
            }
    send("SEND_MESSAGE", content, activeRoomID, userEmail);
    deleteInput(document.getElementById("messageinput"));
}

function deleteInput(element) {
    element.value = "";
}

function sendMessageEnter(e) {
    if (e.keyCode === 13) {
        sendMessage();
    }
}

function loadfiles(event)
{
    var file = event.target.files[0];
    var fileType = file["type"];
    var fileName = file.name;

    var imageTypes = ["image/gif", "image/jpeg", "image/png"];

    var messagePlaceholder = document.getElementById("messages" + activeRoomID);

    var formated = formatDate(new Date(), "MINUTES");

    var div = document.createElement("div");
    div.className = "bubble me";

    var content;

    var reader = new FileReader();
    reader.onload = function (event) {
        if ($.inArray(fileType, imageTypes) < 0) {
            //zasto ne radi download?
            div.innerHTML = '<a href=' + event.target.result + ' download=' + fileName + '>' + '<img src="/web/img/file.png" width="30px" height="30px"/> </a>' + fileName + '<small style="color: #999; font-size: 10px;">' + formated + '</small>';
            //var data = event.target.result.replace("data:" + file.type + ";base64,", '');
            console.log(event.target.result);
            content =
                    {
                        "@class": "domain.File",
                        "name": fileName,
                        "fileType": fileType,
                        "base64Content": event.target.result
                    }
            send("SEND_FILE", content, activeRoomID, userEmail);
        } else {

            div.innerHTML = '<a href=' + event.target.result + ' download=' + fileName + '>' + '<img src=' + event.target.result + ' style="width: 300px; hight: 300px"> </a>' + '<small style="color: #999; font-size: 10px;">' + formated + '</small>';
            //var data = event.target.result.replace("data:" + file.type + ";base64,", '');

            content =
                    {
                        "@class": "domain.Picture",
                        "name": fileName,
                        "pictureType": fileType,
                        "base64Content": event.target.result
                    }
            send("SEND_PICTURE", content, activeRoomID, userEmail);
        }


    };
    reader.readAsDataURL(file);
    messagePlaceholder.appendChild(div);

}

function clickRoom(element) {
    if (element.className === "person active") {
        return;
    }

    console.log(element.childNodes)
    activeRoomID = element.childNodes[1].value;
    console.log(activeRoomID);
    activeRoomName = element.childNodes[5].innerHTML;
    console.log(activeRoomName);

    changeActive();

    element.className = "person active";
    document.getElementById(activeRoomID).className = "chat active-chat";
    document.getElementById("who").innerHTML = activeRoomName;
}

function loadProfilePicture(event)
{
    var file = event.target.files[0];
    var fileType = file["type"];
    var fileName = file.name;
    var imageTypes = ["image/gif", "image/jpeg", "image/png"];

    var read = new FileReader();
    read.onload = function (event) {
        if ($.inArray(fileType, imageTypes) < 0) {
            return;
        }
        var img = document.getElementById("profile");
        img.src = event.target.result;

        //u doradi
        //var data = event.target.result.replace("data:" + file.type + ";base64,", '');
        var content =
                {
                    "@class": "domain.Picture",
                    "name": fileName,
                    "pictureType": fileType,
                    "base64Content": event.target.result
                }
        //send("CHANGE_PROFILE_PICTURE", content);
    };
    read.readAsDataURL(file);
}

function showMenu(element, e) {
    var posx = e.clientX + 'px';
    var posy = e.clientY + 'px';
    document.getElementById('contextMenu').style.position = 'absolute';
    document.getElementById('contextMenu').style.display = 'inline';
    document.getElementById('contextMenu').style.left = posx;
    document.getElementById('contextMenu').style.top = posy;
    clicked = element;
    console.log(clicked);
}
function hideMenu() {
    document.getElementById('contextMenu').style.display = 'none';
}

function deleteRoom() {
    clicked.parentNode.removeChild(clicked);
    var room = clicked.dataset.chat;
    var messages = document.getElementById("messages" + room);
    var deleting = messages.parentNode;
    deleting.parentNode.removeChild(deleting);

    var content =
            {
                "@class": "domain.TextMessage",
                "text": ""
            }
    send("DELETE_ROOM", content, activeRoomID, userEmail);
}

function alertMessage(message) {

    var div = document.createElement("div");
    div.className = "bubble me";
    div.style = "color: grey; background-color: white;";
    div.innerHTML = message.content.text;
    var messages = document.getElementById("messages" + message.in1.name);
    messages.appendChild(div);
}

function newMessage(message) {

    var messages = document.getElementById("messages" + message.in1.roomID);

    var date = formatDate(new Date(), "MINUTES");

    var div = document.createElement("div");
    div.className = "bubble you";
    div.innerHTML = message.content.text + '<small style="color: white; font-size: 10px;">' + date + '</small>';

    messages.appendChild(div);
}

function newPicture(message) {

    var messages = document.getElementById("messages" + messages.in1.roomID);

    var date = formatDate(new Date(), "MINUTES");

    var a = document.createElement('a');
    a.href = message.content.pictureType + ';base64,' + message.content.base64Content;
    a.download = message.content.name;

    var img = document.createElement('img');
    img.src = 'data:' + message.content.pictureType + ';base64,' + message.content.base64Content;
    img.style = "width: 300px; hight: 300px";

    var div = document.createElement("div");
    div.className = "bubble you";

    a.appendChild(img);
    div.appendChild(a);
    div.innerHTML += '<small style="color: white; font-size: 10px;">' + date + '</small>';
    messages.appendChild(div);
}

function newFile(message) {

    var messages = document.getElementById("messages" + messages.in1.roomID);
    var date = formatDate(new Date(), "MINUTES");

    var a = document.createElement('a');
    a.href = 'data:' + message.content.fileType + ';base64,' + message.content.base64Content;
    a.download = message.content.name;

    var img = document.createElement('img');
    img.src = "http://cliparts.co/cliparts/rTL/eqK/rTLeqKxT8.png";
    img.style = "width: 30px; hight: 30px";

    var div = document.createElement("div");
    div.className = "bubble you";

    a.appendChild(img);
    div.appendChild(a);
    div.innerHTML += message.content.name + '<small style="color: white; font-size: 10px;">' + date + '</small>';
    messages.appendChild(div);
}

function setStartParametres() {
    userEmail = document.getElementById("hidden").value;
    listOfRooms = document.getElementsByClassName("people")[0];
}

function changeActive() {
    var lastActive = document.getElementsByClassName("person active")[0];
    console.log(lastActive);
    if (lastActive !== undefined) {
        lastActive.className = "person";
        var lastActiveChat = document.getElementsByClassName("active-chat")[0];
        lastActiveChat.className = "chat";
    }
}

function formatDate(date, fullOrMinutes) {
    var date = " " + ("0" + date.getHours()).slice(-2) + ":" + ("0" + date.getMinutes()).slice(-2);
    if (fullOrMinutes === "FULL") {
        var date = ("0" + date.getDate()).slice(-2) + "." + ("0" + (date.getMonth() + 1)).slice(-2) + "." + date.getFullYear() + date;
    }
    return date;
}