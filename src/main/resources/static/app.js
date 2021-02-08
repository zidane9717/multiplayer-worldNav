var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings/'+$("#hostNumber").val()+"/"+$("#name").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/greetings/'+$("#hostNumber").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
          showGreeting("'"+$("#name").val()+"' joined the game.");
    });
}

function sendName() {
    showPlayer($("#name").val());
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val(),'content': $("#commandInput").val()}));
}

function sendCommand() {
    showGreeting($("#name").val()+": "+$("#commandInput").val());
    stompClient.send("/app/hello/"+$("#name").val(), {}, JSON.stringify({'name': $("#name").val(),'content': $("#commandInput").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showPlayer(message) {
    $("#players").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
          $("hostBtn").onclick = $(function () {checkHostNumber();})
    });
});

function buttonsDisable(){

    $("#name").prop("disabled", true);
    $("#hostNumber").prop("disabled", true);

    $("#hostBtn").prop("disabled", true);
    $("#joinBtn").prop("disabled", true);

    $("#commandInput").prop("disabled", false);
    $("#send").prop("disabled", false);

    $("#conversation").show();
    $("#greetings").html("");
}

function checkName(){
    $.ajax({
        type: 'GET',
        url: '/validate/name',
        data: {
            message: $("#name").val()+" "+$("#hostNumber").val()
        },
        success: function(text) {
            buttonsDisable();
        },
        error: function (jqXHR) {
            $( "#validateAtIndex" ).text("The name is already used..");
        }
    });
}

function checkHostNumber(){
    $.ajax({
        type: 'GET',
        url: '/validate/hostNumber',
        data: {
            message: $("#hostNumber").val()
        },
         success : function(text){
          checkName();
          $("#start").prop("disabled", false);
         },
        error: function (jqXHR) {
            $( "#validateAtIndex" ).text("The number is already used..");
        }
    });
}

function myFunction(){
window.scrollTo(0,document.body.scrollHeight);
}
