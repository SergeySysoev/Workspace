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
var messageList = [];
var userName='';
function run(){
	var chatArea = document.getElementsByClassName('chatArea')[0];
	chatArea.addEventListener('click', delegateEvent);
	var allMessages = restore();
	createAllMessages(allMessages);
}

function createAllMessages(allMessages){
	for(var i = 0; i <allMessages.length; i++)
		addMessage(allMessages[i]);
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

function onSendButtonClick(userName){
	if(userName=='')
		userName = "ANONIM";
	var textMessage = document.getElementById('message');
	var newMessage = theMessage(textMessage.value, userName);
	if(textMessage.value == '')
		return;
	addMessage(newMessage);
	textMessage.value = '';
	store(messageList);
}

function update(divItem, message){
	divItem.setAttribute('mes-id', message.id);
	divItem.lastChild.textContent = message.username+": "+ message.description;
}

function addMessage(message){
	var tmpMessage = createMessage(message);
	var messages = document.getElementsByClassName('messages')[0];
	messageList.push(message);
	messages.appendChild(tmpMessage);
}

function createMessage(message){
	var temp = document.createElement('div');
	var htmlAsText = '<div class="well" mes-id="идентификатор"> user name: Message text </div><br>';
	temp.innerHTML = htmlAsText;
	update(temp.firstChild, message);
	return temp.firstChild;
}
function store(listToSave){
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem("History", JSON.stringify(listToSave));
}
function restore() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	var item = localStorage.getItem("History");
	return item && JSON.parse(item);
}