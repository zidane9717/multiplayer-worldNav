var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#name").prop("disabled", connected);
    $("#send").prop("disabled", !connected);
    $("#commandInput").prop("disabled", !connected);
    $("#conversation").show();
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/greetings/'+$("#name").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
            sendName();
    });
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val(),'content': $("#commandInput").val()}));
}

function sendCommand() {
    stompClient.send("/app/hello/"+$("#name").val(), {}, JSON.stringify({'name': $("#name").val(),'content': $("#commandInput").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { checkName() });
    $( "#send" ).click(function() { sendCommand(); });
});

function checkName(){
      	$.ajax({
            type: 'GET',
            url: '/examples/echo-message',
            data: {
                message: $("#name").val()
            },
            success: function(text) {
                $( "#boddd" ).text(text);
                connect();
            },
            error: function (jqXHR) {
                $( "#boddd" ).text("The name is already used..");
            }
        });
   }

function myFunction(){
window.scrollTo(0,document.body.scrollHeight);
}