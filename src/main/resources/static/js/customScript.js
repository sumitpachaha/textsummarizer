function focusNameFun() { 
		document.getElementById("focusName").focus(); 
		document.getElementById('focusName').removeAttribute('readonly');
}

function focusEmailFun() { 
	document.getElementById("focusEmail").focus(); 
	document.getElementById('focusEmail').removeAttribute('readonly');
} 
function focusPassFun() { 
	document.getElementById("focusPass").focus(); 
	document.getElementById('focusPass').removeAttribute('readonly');
} 
function focusAddFun() { 
	document.getElementById("focusAddress").focus(); 
	document.getElementById('focusAddress').removeAttribute('readonly');
} 

function myEnCopyFun() {
  var copyText = document.getElementById("eoutputString");
  copyText.select();
  document.execCommand("copy");
  alert("Copied!!");
}

function myDeCopyFun() {
	  var copyText = document.getElementById("doutputString");
	  copyText.select();
	  document.execCommand("copy");
	  alert("Copied!!");
	}
