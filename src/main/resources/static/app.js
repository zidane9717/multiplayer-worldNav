var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings/'+$("#number").val()+"/"+$("#name").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/greetings/'+$("#number").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        sendName("firstTime");
    });
}

function sendName(text) {
    stompClient.send("/app/hello/"+$("#number").val(), {}, JSON.stringify({'name': $("#name").val(),'content':text}));
}

function sendCommand() {
    stompClient.send("/app/hello/"+$("#number").val()+"/"+$("#name").val(), {}, JSON.stringify({'name': $("#name").val(),'number':$("#number").val(),'content':$("#commandInput").val()}));
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
    });
    $( "#hostBtn" ).click(function() { checkNumber(0); });
    $( "#joinBtn" ).click(function() { checkNumber(1); });
    $( "#start" ).click(function() {
    startGame();
     });
    $( "#send" ).click(function() { sendCommand(); });

});

function startGame(){
$("#start").prop("disabled", true);
sendName($("#number").val());
}

function buttonsDisable(){

    $("#name").prop("disabled", true);
    $("#number").prop("disabled", true);

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
            message: $("#name").val()+" "+$("#number").val()
        },
        success: function(text) {
            buttonsDisable();
            connect();
        },
        error: function (jqXHR) {
            $( "#validateAtIndex" ).text("The name is already used..");
        }
    });
}

function checkNumber(type){
    if(type<1){
    var urlType = '/validate/hostNumber';
    }else{
    var urlType= '/validate/joinNumber'
    }
    $.ajax({
        type: 'GET',
        url: urlType,
        data: {
            message: $("#number").val()
        },
         success : function(text){
         checkName();
         $( "#validateAtIndex" ).text("");
         if(type<1){
          $("#start").prop("disabled", false);
          }
         },
        error: function (jqXHR) {
            if(type<1){
            $( "#validateAtIndex" ).text("The number is already used..");
             }else{
            $( "#validateAtIndex" ).text("Game already started/ Game not available..");
             }
        }
    });
}

function myFunction(){
window.scrollTo(0,document.body.scrollHeight);
}
