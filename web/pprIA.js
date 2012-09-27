var req;
var iNumber;
var iSessionId;
var element;

function SetImage(id, filename) {
    var elem = document.getElementById(id);
    if (elem)
    {
        elem.src = filename;
    }
}

function predict(aProtein, aSessionId) {
    iSessionId = aSessionId;
    element = document.getElementById("sequence" + aProtein.toString());
    element.innerHTML = "<img src=\"images/ajax-loader.gif\">";
    var url = "/toppr/SecondaryStructurePrediction?protein=" + aProtein.toString() + "&session=" + escape(iSessionId);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}



function predictDomain(aProtein, aSessionId) {
    iSessionId = aSessionId;
    element = document.getElementById("bar" + aProtein.toString());
    element.innerHTML = "<img src=\"images/ajax-loader.gif\">";
    var url = "/toppr/ShowDomain?protein=" + aProtein.toString() + "&session=" + escape(iSessionId);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function conservationFinder(aNumber, aSessionId) {
    iNumber = aNumber;
    iSessionId = aSessionId;
    element = document.getElementById("con" + iNumber);
    element.innerHTML = "<img src=\"images/ajax-loader.gif\">";
    var url = "/toppr/ShowConservation?protein=" + escape(iNumber) + "&session=" + escape(iSessionId);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function getGo(aProtein, aSessionId) {
    iSessionId = aSessionId;
    element = document.getElementById("info" + aProtein.toString());
    element.innerHTML ="<img src=\"images/ajax-loader.gif\">";
    var url = "/toppr/ShowGo?protein=" + aProtein.toString() + "&session=" + escape(iSessionId);

    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);

    req.onreadystatechange = callback;
    req.send(null);
}

function getPdb(aNumber, aSessionId) {
    iNumber = aNumber;
    alert(iNumber);
    iSessionId = aSessionId;
    var url = "/toppr/Show3d?protein=" + escape(iNumber) + "&session=" + escape(iSessionId);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    alert(iNumber);
    req.onreadystatechange = callback;

    alert(iNumber);
    req.send(null);
}


function addProteinAccession(accession) {

    var url = "/toppr/ProteinCompilation?protein=" + accession;
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = compilationFound;
    hideElement('span',accession);
    req.send(null);
    //setCompilationBar();
}
function deleteProtein(aProtein) {

    var protein = aProtein
    var url = "/toppr/DeleteProteinFromCompilation?protein=" + protein;
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = compilationFound;
    showElement('span',protein);
    req.send(null);
    //setCompilationBar();
}

function hideElement(t, id) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == id) {
            tags[i].style.display = 'none';
        }
    }
}

function changeImageAnd(src){
        document['andNotOr'].src = src;
}

function changeImgSrc(name, src){
   document[name].src = src;
}


function showElement(t, id) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == id) {
            tags[i].style.display = 'inline';
        }
    }
}

function showPositionInfo(id, t) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == id) {
            tags[i].style.display = 'inline';
        }
    }
}

function replaceInfo(aNumber, info) {
    document.getElementById("info" + aNumber).innerHTML = info;
}
function replaceProteinInfo(aProtein, info) {
    document.getElementById("info" + aProtein).innerHTML = info;
}
function goTo(anAnchor) {
    location.hash = anAnchor;
}

function hidePositionInfo(id, t) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == id) {
            tags[i].style.display = 'none';
        }
    }
}

function showAllSites(t, id) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id.substring(0, tags[i].id.lastIndexOf('_')) == id) {
            tags[i].style.display = 'inline';
        }
    }
}
function showAllProccessingSites(t, id) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id.substring(0, tags[i].id.lastIndexOf('.')) == id) {
            tags[i].style.display = 'inline';
        }
    }
}
function hideAllProccessingSites(t, id) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id.substring(0, tags[i].id.lastIndexOf('.')) == id) {
            tags[i].style.display = 'none';
        }
    }
}

function hideAllSites(t, id) {
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id.substring(0, tags[i].id.lastIndexOf('_')) == id) {
            tags[i].style.display = 'none';
        }
    }
}
function showHidePosition(id, t) {
    var displays = document.getElementsByTagName('span');
    var style = 'inline';
    for (var j = 0; j < displays.length; j++) {
        if (displays[j].id == id + '.Navigator') {
            style = displays[j].innerHTML;
            if (style == 'none') {
                displays[j].innerHTML = 'inline';
            } else {
                displays[j].innerHTML = 'none';
            }
        }
    }
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == id) {
            if (style == 'none') {
                tags[i].style.display = 'inline';
            } else {
                tags[i].style.display = 'none';
            }
        }
    }
}
function showHideAllSites(t, id) {
    var displays = document.getElementsByTagName('span');
    var style = 'inline';
    for (var k = 0; k < displays.length; k++) {
        if (displays[k].id == id + 'AllSites') {
            style = displays[k].innerHTML;
            if (style == 'none') {
                displays[k].innerHTML = 'inline';
            } else {
                displays[k].innerHTML = 'none';
            }
        }
    }
    for (var j = 0; j < displays.length; j++) {
        if (displays[j].id.substring(0, displays[j].id.indexOf('_')) == id) {
            if (style == 'none') {
                displays[j].innerHTML = 'inline';
            } else {
                displays[j].innerHTML = 'none';
            }
        }
    }
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id.substring(0, tags[i].id.indexOf('_')) == id) {
            if (style == 'none') {
                tags[i].style.display = 'inline';
            } else {
                tags[i].style.display = 'none';
            }
        }
    }
}
function checkUnchecked(controller,changer) {
	 var theController = document.getElementById(controller);
     var theChanger = document.getElementById(changer);
     var tags = document.getElementsByTagName('input');
     for (var i = 0; i < tags.length; i++) {
        if (tags[i].id == theController.id) {
            if(tags[i].type){
                if (tags[i].type=='checkbox') {
                    tags[i].checked=theController.checked;
                }
            }
        }
        if (tags[i].id == theChanger.id) {
            if(tags[i].type){
                if (tags[i].type=='checkbox') {
                    tags[i].checked=theController.checked;
                }
            }
        }
    }
}
function showHideProcessingSites(t, id) {
    var displays = document.getElementsByTagName('span');
    var style = 'inline';
    for (var j = 0; j < displays.length; j++) {
        if (displays[j].id == id + '.Display') {
            style = displays[j].innerHTML;
            if (style == 'none') {
                displays[j].innerHTML = 'inline';
            } else {
                displays[j].innerHTML = 'none';
            }
        }
    }
    var tags = document.getElementsByTagName(t);
    for (var i = 0; i < tags.length; i++) {
        if (tags[i].id.substring(0, tags[i].id.lastIndexOf('.')) == id) {
            if (style == 'none') {
                tags[i].style.display = 'inline';
            } else {
                tags[i].style.display = 'none';
            }
        }
    }
}


function treatmentReplace(aNumber, treatment) {
    iNumber = aNumber;
    element = document.getElementById("seq" + iNumber);
    element.innerHTML = "<img src=\"images/searching.gif\">";
    var protein = document.getElementById("protein" + iNumber);
    var url = "/toppr/TreatmentSequence?protein=" + escape(protein.value) + "&treatment=" + escape(treatment);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = callbackSeq;
    req.send(null);
}

function showProcessingSites(aNumber, aSessionId) {
    iNumber = aNumber;
    iSessionId = aSessionId;
    element = document.getElementById("seq" + iNumber);
    element.innerHTML = "<img src=\"images/searching.gif\">";
    var url = "/toppr/ShowSitesInSequence?protein=" + escape(iNumber) + "&session=" + escape(iSessionId);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = callbackSeq;
    req.send(null);
}

function addToSelection(aNumber, aSessionId) {
    iNumber = aNumber;
    iSessionId = aSessionId;
    element = document.getElementById("seq" + iNumber);
    var url = "/toppr/Selection?protein=" + iNumber + "&session=" + escape(iSessionId);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = selectionOk;
    req.send(null);
}

function setCompilationBar(){
    element = document.getElementById("userCompilation");
    var url = "/toppr/CompilationBar";
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = compilationFound;
    req.send(null);
}
function compilationFound() {
    element = document.getElementById("userCompilation");
    if (req.readyState == 4) {
        if (req.status == 200) {
            element.innerHTML = req.responseText;
        }
    }
}
function selectSite(aProtein, aSite, aSessionId) {
    var iProtein = aProtein;
    var iSite = aSite;
    iSessionId = aSessionId;
    element = document.getElementById("seq" + iProtein);
    var url = "/toppr/SelectSite?protein=" + iProtein + "&site=" + iSite + "&session=" + escape(iSessionId);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("Get", url, true);
    req.onreadystatechange = selectionOk;
    req.send(null);
}

function selectionOk() {
    if (req.readyState == 4) {
        if (req.status == 200) {

        }
    }
}

function callbackSeq() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            element.innerHTML = "<tt>" + req.responseText + "</tt>";
        }
    }
}

function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            element.innerHTML = req.responseText;
        }
    }
}

function reloadIframe(){
    element = document.getElementById('performedSearches');
    element.location.reload(true);
}
var slideElement = null;

function slide(elementId, headerElement)
{
   
   slideElement = document.getElementById(elementId);
   if(slideElement.up == false || slideElement.down)
   {
      slideUpStep1();
      slideElement.up = true;
      slideElement.down = false;
      headerElement.innerHTML = '+';
   }
   else
   {
      slideDownStep1();
      slideElement.down = true;
      slideElement.up = false;
      headerElement.innerHTML = '-';
   }
}

function slideUpStep1()
{
   animate(slideElement.id, 0, 0, 20, 20, 250, null);
}


function slideDownStep1()
{
   animate(slideElement.id, 0, -280, 900, 300, 250, null);
}

//This code was created by the fine folks at Switch On The Code - http://blog.paranoidferret.com
//This code can be used for any purpose

function animate(elementID, newLeft, newTop, newWidth,
      newHeight, time, callback)
{
  var el = document.getElementById(elementID);
  if(el == null)
    return;

  var cLeft = parseInt(el.style.left);
  var cTop = parseInt(el.style.top);
  var cWidth = parseInt(el.style.width);
  var cHeight = parseInt(el.style.height);

  var totalFrames = 1;
  if(time> 0)
    totalFrames = time/40;

  var fLeft = newLeft - cLeft;
  if(fLeft != 0)
    fLeft /= totalFrames;

  var fTop = newTop - cTop;
  if(fTop != 0)
    fTop /= totalFrames;

  var fWidth = newWidth - cWidth;
  if(fWidth != 0)
    fWidth /= totalFrames;

  var fHeight = newHeight - cHeight;
  if(fHeight != 0)
    fHeight /= totalFrames;

  doFrame(elementID, cLeft, newLeft, fLeft,
      cTop, newTop, fTop, cWidth, newWidth, fWidth,
      cHeight, newHeight, fHeight, callback);
}

function doFrame(eID, cLeft, nLeft, fLeft,
      cTop, nTop, fTop, cWidth, nWidth, fWidth,
      cHeight, nHeight, fHeight, callback)
{
   var el = document.getElementById(eID);
   if(el == null)
     return;

  cLeft = moveSingleVal(cLeft, nLeft, fLeft);
  cTop = moveSingleVal(cTop, nTop, fTop);
  cWidth = moveSingleVal(cWidth, nWidth, fWidth);
  cHeight = moveSingleVal(cHeight, nHeight, fHeight);

  el.style.left = Math.round(cLeft) + 'px';
  el.style.top = Math.round(cTop) + 'px';
  el.style.width = Math.round(cWidth) + 'px';
  el.style.height = Math.round(cHeight) + 'px';

  if(cLeft == nLeft && cTop == nTop && cHeight == nHeight
    && cWidth == nWidth)
  {
    if(callback != null)
      callback();
    return;
  }

  setTimeout( 'doFrame("'+eID+'",'+cLeft+','+nLeft+','+fLeft+','
    +cTop+','+nTop+','+fTop+','+cWidth+','+nWidth+','+fWidth+','
    +cHeight+','+nHeight+','+fHeight+','+callback+')', 40);
}

function moveSingleVal(currentVal, finalVal, frameAmt)
{
  if(frameAmt == 0 || currentVal == finalVal)
    return finalVal;

  currentVal += frameAmt;
  if((frameAmt> 0 && currentVal>= finalVal)
    || (frameAmt <0 && currentVal <= finalVal))
  {
    return finalVal;
  }
  return currentVal;
}