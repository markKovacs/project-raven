var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $(".conversation").show();
        $(".offline").hide();
    }
    else {
        $(".conversation").hide();
        $(".offline").show();

    }
    /*$("#newmessages").html("");
    $("#newusers").html("");*/
}

function connect() {

    var socket = new SockJS('/raven');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/newsfeed', function (response) {
            console.log(response);
            var msg = JSON.parse(response.body);
            showMessage(msg);
        });

        stompClient.subscribe('/topic/newusers', function (response) {
            console.log(response);
            var msg = JSON.parse(response.body);
            showRegisteredUser(msg.user);
        });

        stompClient.subscribe('/app/subs', function (response) {
            console.log(response);
            var msg = JSON.parse(response.body);
            alert(msg.body);
        });

    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'content': $("#content").val()}));
}

function showMessage(userName, content, dateTimeString) {
    $("#newmessages").prepend(`
            <tr>
                <td>${msg.userName}</td>
                <td>${msg.content}</td>
                <td>${msg.dateTimeString}</td>
            </tr>
    `);
}

function showRegisteredUser(user) {
    $("#newusers").prepend(`
            <tr>
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.name}</td>
                <td>${user.office != null ? user.office : 'N/A'}</td>
                <td>${user.registeredAt}</td>
            </tr>
    `);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});