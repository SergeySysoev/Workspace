'use strict';
var uniqueId = function() {
	var date = Date.now();
	var random = Math.random() * Math.random();

	return Math.floor(date * random).toString();
};
var theMessage = function(text,name) {
	return {
		username: name,
		description: text,
		id: uniqueId()
	};
};
var appState = {
	mainUrl : 'http://localhost:8888/chat',
	msgList:[],
	token : 'TN11EN'
};

var userName;
function run(){
	var chatArea = document.getElementsByClassName('chatArea')[0];
	chatArea.addEventListener('click', delegateEvent);
	restore();
}

function createAllMessages(allMessages){
	for(var i = 0; i <allMessages.length; i++)
		addMessage(allMessages[i]);
}


function addMessage(message){
	var tmpMessage = createMessage(message);
	var messages = document.getElementsByClassName('messages')[0];
	appState.msgList.push(message);
	messages.appendChild(tmpMessage);
}

function delegateEvent(evtObj){
		if(evtObj.type === 'click' && evtObj.target.classList.contains('send'))
		onSendButtonClick(userName);
		if(evtObj.type === 'click' && evtObj.target.classList.contains('nameApply'))
		onApplyButtonClick();
}

function onApplyButtonClick(){
	userName = document.getElementById('userName').value;
}

function onSendButtonClick(userName, anonim){
	if(userName=='')
		userName = "ANONIM";
	var textMessage = document.getElementById('message');
	var newMessage = theMessage(textMessage.value, userName);
	if(textMessage.value == '')
		return;
	sendMessage(newMessage);
	textMessage.value = '';
}

function sendMessage(message, continueWith) {
    post(appState.mainUrl, JSON.stringify(message), function(){
        restore();
    });
}

function update(divItem, message){
	divItem.setAttribute('mes-id', message.id);
	divItem.lastChild.textContent = message.username+": "+ message.description;
}

function createMessage(message){
	var temp = document.createElement('div');
	var htmlAsText = '<div class="userMessage" mes-id="идентификатор"> user name: Message text </div>';
	temp.innerHTML = htmlAsText;
	update(temp.firstChild, message);
	return temp.firstChild;
}

function restore(continueWith) {
	var url = appState.mainUrl + '?token=' + appState.token;

	get(url, function(responseText) {
		console.assert(responseText != null);

		var response = JSON.parse(responseText);

		appState.token = response.token;
		createAllMessages(response.messages);
		

		continueWith && continueWith();
	});
}
function defaultErrorHandler(message) {
	console.error(message);
}
function get(url, continueWith, continueWithError) {
	ajax('GET', url, null, continueWith, continueWithError);
}

function post(url, data, continueWith, continueWithError) {
	ajax('POST', url, data, continueWith, continueWithError);	
}

function del(url, data, continueWith, continueWithError) {
	ajax('DELETE', url, data, continueWith, continueWithError);	
}

function isError(text) {
	if(text == "")
		return false;
	
	try {
		var obj = JSON.parse(text);
	} catch(ex) {
		return true;
	}

	return !!obj.error;
}

function ajax(method, url, data, continueWith, continueWithError) {
	var xhr = new XMLHttpRequest();

	continueWithError = continueWithError || defaultErrorHandler;
	xhr.open(method || 'GET', url, true);

	xhr.onload = function () {
		if (xhr.readyState !== 4)
			return;

		if(xhr.status != 200) {
			continueWithError('Error on the server side, response ' + xhr.status);
			return;
		}

		if(isError(xhr.responseText)) {
			continueWithError('Error on the server side, response ' + xhr.responseText);
			return;
		}

		continueWith(xhr.responseText);
	};    

    xhr.ontimeout = function () {
    	ontinueWithError('Server timed out !');
    }

    xhr.onerror = function (e) {
    	var errMsg = 'Server connection error !\n'+
    	'\n' +
    	'Check if \n'+
    	'- server is active\n'+
    	'- server sends header "Access-Control-Allow-Origin:*"';

        continueWithError(errMsg);
    };

    xhr.send(data);
}