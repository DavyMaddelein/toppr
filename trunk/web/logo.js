var req;

//This function will groups two checkboxes. If one checkbox is checked, the value will be set to "use". The other checkbox
// will be unchecked, and the value will be set to "notuse".
function checkUncheck(controller,changer) {
	var theController = document.getElementById(controller);
     var theChanger = document.getElementById(changer);
     var tags = document.getElementsByTagName('input');
     for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == theController.id) {
            if(tags[i].type){
                if (tags[i].type=='checkbox') {
                    tags[i].checked=theController.checked;
                    tags[i].value='use';
                }
            }
        }
        if (tags[i].id == theChanger.id) {
            if(tags[i].type){
                if (tags[i].type=='checkbox') {
                    tags[i].checked=!theController.checked;
                    tags[i].value='notuse';
                }
            }
        }
    }
}

function setUse(controller){
    var theController = document.getElementById(controller);
    var tags = document.getElementsByTagName('input');
     for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == theController.id) {
            if(tags[i].type){
                if (tags[i].type=='checkbox') {
                    if(theController.checked){
                        tags[i].value='use';
                    } else {
                        tags[i].value='notuse';
                    }
                }
            }
        }
    }

}


//This function will post a request to the logo servlet on the server
function postLogo(){
    var sendRequest = true;
    var posTreatment = '';
    var negTreatment = '';
    for (var i=0; i <= document.getElementById("positive").options.length-1;i++) {
        if (document.getElementById("positive").options[i].selected) {
            posTreatment = posTreatment + document.getElementById("positive").options[i].value + 'XsplitX';
        }
    }
    for (var i=0; i <= document.getElementById("negative").options.length-1;i++) {
        if (document.getElementById("negative").options[i].selected) {
            negTreatment = negTreatment + document.getElementById("negative").options[i].value + 'XsplitX';
        }
    }
    //get yAxis value
    var yAxis=document.getElementById("yAxis").value;
    //get the negative set checkboxes
    var negSeqChb=document.getElementById("negSeqChb").value;
    var negSwChb=document.getElementById("negSwChb").value;
    var species=document.getElementById("species").value;
    //get the species, aaparam and substitution matrix
    var selectedNegative =document.getElementById("selectedNegative").value;
    var selectedPositive =document.getElementById("selectedPositive").value;
    var session =document.getElementById("session").value;

    //Check if we have to send the request
    if(sendRequest){
        document['logoImg'].src = "images/ajax-loader.gif";

        //everything seems ok, build the request
        var parm="yAxis="+yAxis + "&negSeqChb="+negSeqChb+"&negSwChb=" + negSwChb + "&species="+species +"&positive=" + posTreatment + "&negative=" + negTreatment + "&session=" +session + "&selectedNegative=" + selectedNegative + "&selectedPositive=" + selectedPositive;
        var url = "/toppr/LogoServlet";
        if (window.XMLHttpRequest) {
            req = new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }
        req.onreadystatechange = showLogo;
        req.open("POST", url, true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.setRequestHeader("Content-length", parm.length);
        req.setRequestHeader("Connection", "close");
        req.send(parm);
    }
}

//This fucntion shows the logo that is saved on the server.
var imageName;
function showLogo(){
    if (req.readyState == 4) {
        if (req.status == 200) {
            imageName = req.responseText;
            if(imageName.indexOf("Error")>= 0){
                document['logoImg'].src = 'images/white.gif';
                alert(imageName);
            } else {
                document['logoImg'].src = 'logo/' + req.responseText + '.jpeg';
            }
        }
    }
}

