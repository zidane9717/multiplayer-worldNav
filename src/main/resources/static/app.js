var stompClient = null;

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#hostBtn").click(function () {
        validate(1);
    });
    $("#joinBtn").click(function () {
        validate(2);
    });
    $("#start").click(function () {
        startGame();
    });
    $("#send").click(function () {
        sendCommand($("#commandInput").val());
        $("#send").reset;
    });

});

function validate(type) {

    var urlType = '/validate';

    $.ajax({
        type: 'GET',
        url: urlType,
        data: {
            number: $("#number").val(),
            name: $("#name").val(),
            state:type
        },
        success: function (text) {
            if (type === 1) {
                $("#start").prop("disabled", false);
                $("#validateAtIndex").text("");
                buttonsDisable();
                connect();
            }else if(type === 2 ){
                $("#validateAtIndex").text("");
                buttonsDisable();
                connect();
            }
            showGreeting(text);
        },
        error: function (jqXHR) {
            if (type < 1) {
                $("#validateAtIndex").text("The number/name is already used.");
            } else {
                $("#validateAtIndex").text("Game already started/Game not available..");
            }
        }
    });
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/greetings/' + $("#number").val() + "/" + $("#name").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });

        stompClient.subscribe('/topic/greetings/' + $("#number").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });

    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showPlayer(message) {
    $("#players").append("<tr><td>" + message + "</td></tr>");
}

function startGame() {
    $("#start").prop("disabled", true);
    validate(3);
}

function sendCommand(text) {
    stompClient.send("/app/hello/" + $("#number").val()+"/"+$("#name").val(), {}, JSON.stringify({
        'name': $("#name").val(),
        'number': $("#number").val(),
        'content': text
    }));
    $("#commandInput").val("");
}


function buttonsDisable() {

    $("#name").prop("disabled", true);
    $("#number").prop("disabled", true);

    $("#hostBtn").prop("disabled", true);
    $("#joinBtn").prop("disabled", true);

    $("#commandInput").prop("disabled", false);
    $("#send").prop("disabled", false);

    $("#conversation").show();
    $("#greetings").html("");
}

function myFunction() {
    window.scrollTo(0, document.body.scrollHeight);
}

