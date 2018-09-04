//
// Copyright (c) 2000 CI&T. 
// All Rights Reserved 
//
//
function concatZero(val, qtd) {
	if (val.length < qtd) {
		val = '0' + val;
		val = concatZero(val, qtd);
	}
	return val;
}


// Sets cookie values. Expiration date is optional.
function setCookie(name, value, expire) {        
    document.cookie = name + "=" + escape(value) + ((expire == null) ? "" : ("; expires=" + expire.toGMTString()))
}

function onlyDigito(){
	if (event.keyCode < 45 || event.keyCode > 57) 
		event.returnValue = false;
}

function onlyInteger(){
	if (event.keyCode < 47 || event.keyCode > 58) 
		event.returnValue = false;
}
// Get cookie value. Return null if no cookie was found.
function getCookie(Name) {
    var search = Name + "="          
    if (document.cookie.length > 0) { 
        
        // if there are any cookies                    
        offset = document.cookie.indexOf(search)                     
        if (offset != -1) { 
            
            // if cookie exists                               
            offset += search.length    
                                       
            // set index of beginning of value                              
            end = document.cookie.indexOf(";", offset)  
                                        
            // set index of end of cookie value                              
            if (end == -1)                                         
                end = document.cookie.length                              
            return unescape(document.cookie.substring(offset, end))                    
        }           
    }
}

// Replace all occurrences of oldSubStr on str and return the
// new string. If there aren't any occurrence of oldSubStr in 
// received str, the str will be returned.
function replaceAll(str, oldSubStr, newSubStr) {
    var newStr = "";
    var strArray = str.split(oldSubStr);
    var length = strArray.length;
    var i = 0;
    for (i=0; i<(length-1); i++) {
       newStr += strArray[i] + newSubStr;
    }
    		
    if (length > 0) {
        // adds the last element
        newStr += strArray[length - 1];
    }
    return newStr;
}

// Insert right blanks on string.
function formatText(text, size) {
    var i;
    var newText = " " + text;
    for (i = text.length; i <= size; i++) {
        newText += " ";
    }
    return newText;
}

// Return true if parameter is a valid float number.
// Float number format must be equal to "nnnnnn,dd".
function checkFloat(floatnumber) {	

    // do not allow "." (dot) character into number
    floatnumber = replaceAll(floatnumber, ".", "X");
    
    // convert "," (comma) to "," (dot) character
    floatnumber = replaceAll(floatnumber, ",", ".");
    	    
    // verify if it is a valid float number
    var parsed = parseFloat(floatnumber);
    return (parsed == floatnumber || floatnumber == '');
}

// Return true if parameter is a valid positive float number
function checkPositiveFloat(floatNumber) {
    if (checkFloat(floatNumber)) {
        var array = floatNumber.split('-');
        if (array.length <= 1)
            return true;
    }
    return false;
}

// Return true if parameter is a valid integer number.
function checkInt(integernumber) {	
    var parsed = parseInt(integernumber, 10);
    return (parsed == integernumber || integernumber == '');
}

// Return true if parameter is a valid positive integer number
function checkPositiveInt(integerNumber) {
    if (checkInt(integerNumber)) {
        var array = integerNumber.split('-');
        if (array.length <= 1)
            return true;
    }
    return false;
}

// Remove right and left spaces from string.
function trim(originalString) {
    originalString += "";
    var array  = originalString.split(' ');
    var start  = 0;
    var end    = array.length - 1;
    var result = '';
    while (array[start] == '') {
        start++;
    }
    while (array[end] == '') {
        end--;
    }
    for (var i = start; i <= end; i++) {
        if (result == '') {
            result = array[i];
        } else {
            result += ' ' + array[i];
        }
    }
    return result;
}

//Return true if the parameter is a valid hour.
//Hour format must be equal to "HH:mm".
function checkHour(hour) {
	var splited = hour.split(':');
	var hh = parseInt(splited[0]);
	var mm = parseInt(splited[1]);
	if (hh > 23) {
		return false;
	}
	if (mm > 59) {
		return false;
	}
	return true;
}

// Return true if the parameter is a valid date.
// Date format must be equal to "dd/MM/yyyy".
function checkDate(date) {	

    var fullDate = parseDateString(date);

    if (fullDate == null) {
        return false;
    }

    if (fullDate[0] == "" ||
        checkInt(fullDate[0]) == false ||
        fullDate[1] == "" ||
        checkInt(fullDate[1]) == false ||
        fullDate[2] == "" ||
        checkInt(fullDate[2]) == false) {
        return false;
    }
	
    var dateDay   = parseInt(fullDate[0], 10);
    var dateMonth = parseInt(fullDate[1], 10);
    var dateYear  = parseInt(fullDate[2], 10);

    if (dateMonth > 12 || dateMonth < 1 || dateYear < 1000 || dateYear > 9999) {
        return false;
    }
	
    maxDateDay = 0;
    switch(dateMonth) {
    case 1: 
        maxDateDay = 31;
        break;
    case 2: 
        if ((((dateYear % 4) == 0) && ((dateYear % 100) != 0)) ||
             ((dateYear % 400) == 0)) {
            maxDateDay = 29;
        } else {
            maxDateDay = 28;
        }
        break;
    case 3: 
        maxDateDay = 31;
        break;
    case 4: 
        maxDateDay = 30;
        break;
    case 5: 
        maxDateDay = 31;
        break;
    case 6: 
        maxDateDay = 30;
        break;
    case 7: 
        maxDateDay = 31;
        break;
    case 8: 
        maxDateDay = 31;
        break;
    case 9: 
        maxDateDay = 30;
        break;
    case 10:
        maxDateDay = 31;
        break;
    case 11:
        maxDateDay = 30;
        break;
    case 12: 
        maxDateDay = 31;
        break;
    }
	
    if ((dateDay < 1) ||
        (dateDay > maxDateDay)) {
        return false;
    } else {
        return true;
    }
}

// Return true if date1 is equal to date2.
function equalDates(date1, date2) {
    var fullDate1 = parseDateString(date1);
    var dateDay1   = parseInt(fullDate1[0], 10);
    var dateMonth1 = parseInt(fullDate1[1], 10);
    var dateYear1  = parseInt(fullDate1[2], 10);

    var fullDate2 = parseDateString(date2);	
    var dateDay2   = parseInt(fullDate2[0], 10);
    var dateMonth2 = parseInt(fullDate2[1], 10);
    var dateYear2  = parseInt(fullDate2[2], 10);
	
    if ((dateDay1 == dateDay2) &&
        (dateMonth1 == dateMonth2) &&
        (dateYear1 == dateYear2)) {
        return true;
    } else {
        return false;
    }
}

// Return <0 if date1 is previous than date2,
// return 0 if date1 is equal than date2, or 
// return >0 if date1 is greater than date2.
function compareDates(date1, date2) {
    var fullDate1 = parseDateString(date1);
    var dateDay1   = parseInt(fullDate1[0], 10);
    var dateMonth1 = parseInt(fullDate1[1], 10);
    var dateYear1  = parseInt(fullDate1[2], 10);

    var fullDate2 = parseDateString(date2);
    var dateDay2   = parseInt(fullDate2[0], 10);
    var dateMonth2 = parseInt(fullDate2[1], 10);
    var dateYear2  = parseInt(fullDate2[2], 10);

    if (dateYear2 > dateYear1) {
        return -1;
    } else if (dateYear2 < dateYear1) {
        return +1;
    }

    // dateYear2 == dateYear1
    if (dateMonth2 > dateMonth1) {
        return -1;
    } else if (dateMonth2 < dateMonth1) {
        return +1;
    }

    // dateMonth2 == dateMonth1
    if (dateDay2 > dateDay1) {
        return -1;
    } else if (dateDay2 < dateDay1) {
        return +1;
    } else {
        return 0;
    }
}

//calc diference between dates.
//return diference in days.
//the second date is more than first date.
function verifyDiferenceOfMonths(date1, date2) {

    var fullDate1 = parseDateString(date1);
    var dateMonth1 = parseInt(fullDate1[1], 10);
    var dateYear1  = parseInt(fullDate1[2], 10);

    var fullDate2 = parseDateString(date2);
    var dateMonth2 = parseInt(fullDate2[1], 10);
    var dateYear2  = parseInt(fullDate2[2], 10);
    
    var diferenceInMonths = dateMonth2 - dateMonth1;
    var diferenceInYears  = dateYear2 - dateYear1;
    var diference = diferenceInMonths + (diferenceInYears * 12);
    
    return diference;
}

// Print current page.
function printPage() {
    if (window.print) {
        self.print();
    } else {
        alert('Seu browser n�o suporta impress�o direta. Utilize o menu para imprimir essa p�gina');
    }
}

// Round the number to the specified number of decimal digits.
function roundTo(number, decimals) {
    multiplier = Math.pow(10, decimals);
    return Math.round(number * multiplier) / (multiplier);
}

// Transform a date string into array of day, month and year value
// Returned array is:
//    parsedDate[0]: day
//    parsedDate[1]: month
//    parsedDate[2]: year
function parseDateString(dateString) {
    var fullDate = dateString.split("/");

    if (fullDate.length != 3) {
        return null;
    } else {
       var parsedDate = new Array();
       parsedDate[0] = fullDate[0];
       parsedDate[1] = fullDate[1];
       parsedDate[2] = fullDate[2];
       return parsedDate;	
    }
}

// ajusta mes/ano para o formato mm/aaaa. Se nao conseguiu, retorna null.
function normalizaMesAno(strMes) {
    var mesAno = strMes.split("/");
    if (mesAno.length != 2) {
        return null;
    } else {
        var strData = new String;
        if (mesAno[0].length == 2) {
            strData = mesAno[0];
        }
        else {
            if (mesAno[0].length == 1) {
                strData = "0" + mesAno[0];
            }
            else {
                return null;
            }
        }
        strData += "/";
        if (mesAno[1].length == 4) {
            strData += mesAno[1];
        }
        else {
            if (mesAno[1].length < 4) {
                var strTemp = "000" + mesAno[1];
                strData += "2" + strTemp.substring(strTemp.length-3,strTemp.length);
            }
            else {
                return null;
            }
        }
        return strData;	
    }
}

// ajusta dia/mes/ano para o formato dd/mm/aaaa. Se nao conseguiu, retorna null.
function normalizaDiaMesAno(strMes) {
    var wDiaMesAno = strMes.split("/");
    var i;
	
    if (wDiaMesAno.length != 3) {
        return null;
    } else {
        var strData = new String;
		// Trata o Dia
        if (wDiaMesAno[0].length == 2) {
            strData = wDiaMesAno[0];
        }
        else {
            if (wDiaMesAno[0].length == 1) {
                strData = "0" + wDiaMesAno[0];
            }
            else {
                return null;
            }
        }
        strData += "/";		
		// Trata o M�s
        if (wDiaMesAno[1].length == 2) {
            strData += wDiaMesAno[1];
        }
        else {
            if (wDiaMesAno[1].length == 1) {
                strData = "0" + wDiaMesAno[1];
            }
            else {
                return null;
            }
        }
        strData += "/";
        if (wDiaMesAno[2].length == 4) {
            strData += wDiaMesAno[2];
        }
        else {
            if (wDiaMesAno[2].length < 4) {
                var strTemp = "000" + wDiaMesAno[2];
                strData += "2" + strTemp.substring(strTemp.length-3,strTemp.length);
            }
            else {
                return null;
            }
        }
        return strData;	
    }
}

// checa se o mes digitado existe
function checkMesExiste(strMes) {
    var strData = normalizaMesAno(strMes);
    if (strData == null) {
        return false;
    }
    strData = "01/" + strData;
    if(checkDate(strData)) {
        return true;
    }
    else {
        return false;
    }
}

// transforma uma data no formato dd/mm/aaaa para o formato Date(aaaa,mm,dd)
function convertDataCompetenciaToCrystal(strData) {
    var diaMesAno = strData.split("/");
    var strDataCrystal = "Date("+diaMesAno[2]+','+diaMesAno[1]+','+diaMesAno[0]+")";
    return strDataCrystal;
}

var autoTAB = false;

function ativaTAB() {
  autoTAB = true;
}

function desativaTAB() {
  autoTAB = false;
}

function verificaTAB(object, form, maxlength) {
  
  if( ( object.value.length == maxlength ) && (autoTAB) ) {
    var intCount=0, indice=-1;
    for( intCount=0; intCount<form.elements.length; intCount++ ) {
      if( form.elements[intCount].name == object.name ) {
        indice = intCount;
        break;
      } 
    }
    if (indice == -1) {
      return;
    }
    while( ((form.elements[(indice+1)].type == "hidden") ||
		    (form.elements[(indice+1)].disabled == true) ||
		    (form.elements[(indice+1)].readOnly == true)) &&
           (indice < form.elements.length) ) { 
		
     	indice++;
    }
    
    if ((form.elements[(indice+1)].type != "select-one") && 
	    (form.elements[(indice+1)].type != "radio") && 
		(form.elements[(indice+1)].type != "checkbox") &&
		(form.elements[(indice+1)].type != "button") &&
		(form.elements[(indice+1)].type != "image")){
		
		form.elements[(indice+1)].select();	
	} 

    form.elements[(indice+1)].focus();
  }
}


var isNav4, isIE4;
if (parseInt(navigator.appVersion.charAt(0)) >= 4) {
    isNav4 = (navigator.appName == "Netscape") ? true : false
    isIE4 = (navigator.appName.indexOf("Microsoft" != -1)) ? true : false
}

 function isNull(form, tipo) {
    var intCount=0, indice=-1;
    
    for( intCount=0; intCount<form.elements.length; intCount++ ) {
        indice = intCount;
        //Testa o tipo passado e o type do elemento em foco
       	if ((tipo='text') && (form.elements[(indice)].type="text")){
       	
       		if (form.elements[(indice)].value== ""){
       		
       			alert("Um ou mais campos texto est�o em branco!");
       			return false;
       		}
       	}
       	//Implementem para o restante
        
    }

    form.elements[(indice+1)].focus();
}

// Verifica se foi digitado ENTER
function verificaENTER(e) {
    var keyCode;
    if (isNav4) {
        keyCode = e.which;
    } else if (isIE4) {
        keyCode = window.event.keyCode;
    }
    if (keyCode == 13) {
        return true;
    }
}

function somarDias(pDataRef, pNumDias) {

    var fullDate1 = parseDateString(pDataRef);
    var dateDay1   = parseInt(fullDate1[0], 10);
    var dateMonth1 = parseInt(fullDate1[1], 10);
    var dateYear1  = parseInt(fullDate1[2], 10);
	var dthResult;

	var wDtInicial = new Date(dateYear1, dateMonth1 - 1, dateDay1);
	
	var wDtFinal   = new Date( Date.UTC(wDtInicial.getYear(), wDtInicial.getMonth(), wDtInicial.getDate(), wDtInicial.getHours(), wDtInicial.getMinutes(), wDtInicial.getSeconds()) + (pNumDias * 24*60*60*1000));

	dthResult = (wDtFinal.getDate() > 9 ? "" + wDtFinal.getDate() : "0" + wDtFinal.getDate());
	dthResult += "/" + (wDtFinal.getMonth() > 8 ? "" + wDtFinal.getMonth() + 1 : "0" + (wDtFinal.getMonth() + 1));
    dthResult += "/" + wDtFinal.getYear();				
	return dthResult;
}

function abrirJanelaPopup(pNomJanela, pLargura, pAltura, pX, pY, pBarras, pResizable)  {
	var wJan;
	wJan = window.open('', pNomJanela, 'width=' + pLargura + ',height=' + pAltura + ',maximized=no,resizable=' + pResizable + ',scrollbars=' + pBarras + ',status=no,menubar=no,top=' + pY + ',left=' + pX);
	wJan.focus();
}

function formatarMonetario(pNum) {
	pNum = pNum.toString().replace(/\$|\, /g, '');
	if (isNaN(pNum)) {
		pNum = "0";
	}
	sign = (pNum == (pNum = Math.abs(pNum)));
	pNum = Math.floor(pNum * 100 + 0.50000000001);
	cents = pNum % 100;
	pNum = Math.floor(pNum/100).toString();
	if (cents < 10) {
		cents = "0" + cents;
	}
	for (var i = 0; i < Math.floor((pNum.length - (1 + i)) / 3); i++) {
		pNum = pNum.substring(0, pNum.length - (4 * i + 3)) + '.' +
		  	   pNum.substring(pNum.length - (4 * i + 3));
	}
	return (((sign) ? '' : '-') + pNum + ',' + cents);
}

/**
	Retira os seguintes caracteres do valor informado: ;*.-/
*/
function removerCaracterFormatacao(valor){
	if(valor != null && valor != "" && valor.length > 0){
		valor = valor.replace(/[\.\*\;\,\-]/ig,"");
		return valor;
	}
	return "";
}

function formataCPFCNPJ( indPfPj, cpf ){
	if( cpf == "" ) return "";
	
	while ( cpf.length < 16 ){
		cpf = "0" + cpf;
	}
	
	if ( indPfPj == "1" && cpf != "" ){
		cpf = cpf.substr(5,3) + "."   + cpf.substr(8,3 ) + "." +
			  cpf.substr(11,3) + "-" + cpf.substr(14, cpf.length);
	}
	else if ( indPfPj == "2" && cpf != "" ){
		cpf = cpf.substr(2,2) + "."   + cpf.substr(4,3 ) + "." +
			  cpf.substr(7,3) + "/" + cpf.substr(10, 4) + "-" +
			  cpf.substr(14, cpf.length);;
	}
	return cpf;
}

function desabilitarBotaoSubmit( pForm ){
	var intCount = 0;
	var len = pForm.elements.length;
	
	for( intCount=0; intCount<len; intCount++ ) {
		if( pForm.elements[intCount].type == "submit" ){
			pForm.elements[intCount].disabled = true;
		}
	}
}

function mudarCursorParaWait(){
	document.body.style.cursor='wait';
}

/*
<!-- Dynamic Version by: Nannette Thacker -->
<!-- http://www.shiningstar.net -->
<!-- Original by :  Ronnie T. Moore -->
<!-- Web Site:  The JavaScript Source -->
<!-- Use one function for multiple text areas on a page -->
<!-- Limit the number of characters per textarea -->
<!-- Begin -->
*/
function textCounter(field, cntfield, maxlimit) {
	if (field.value.length > maxlimit) {
		// if too long...trim it!
		field.value = field.value.substring(0, maxlimit);
	}
	else {
		// otherwise, update 'characters left' counter
		cntfield.value = maxlimit - field.value.length;
	}
}

var da = (document.all) ? 1 : 0;
var pr = (window.print) ? 1 : 0;
var mac = (navigator.userAgent.indexOf("Mac") != -1); 

function imprime() {
  if (pr) { // NS4, IE5
    window.print();
  } else if (da && !mac) { // IE4 (Windows)
    vbPrintPage();
  } else { // other browsers
    alert("Seu browser n�o suporta impress�o direta. Utilize o menu para imprimir essa p�gina");
  }
  // window.close();
}

if (da && !pr && !mac) with (document) {
  writeln('<OBJECT ID="WB" WIDTH="0" HEIGHT="0" CLASSID="clsid:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>');
  writeln('<' + 'SCRIPT LANGUAGE="VBScript">');
  writeln('Sub window_onunload');
  writeln('  On Error Resume Next');
  writeln('  Set WB = nothing');
  writeln('End Sub');
  writeln('Sub vbPrintPage');
  writeln('  OLECMDID_PRINT = 6');
  writeln('  OLECMDEXECOPT_DONTPROMPTUSER = 2');
  writeln('  OLECMDEXECOPT_PROMPTUSER = 1');
  writeln('  On Error Resume Next');
  writeln('  WB.ExecWB OLECMDID_PRINT, OLECMDEXECOPT_PROMPTUSER');
  writeln('End Sub');
  writeln('<' + '/SCRIPT>');
}


$.ready(function($) {
    $('input.date-picker-holder').datePicker();
});

loadFirstUrl = function(pLink) {
   arr = pLink.href.split("#");
   id = arr[1];
   links = jQuery("#" + id + " a");
   if (links.size() > 0) {
		window.location.href = links[0].href;
   }   
};

function valida_cnpj(cnpj)
{
	cnpj = cnpj.replace(/\D+/g, '');
	var numeros, digitos, soma, i, resultado, pos, tamanho, digitos_iguais;
	digitos_iguais = 1;
	if (cnpj.length < 14 && cnpj.length < 15)
      return false;
	for (i = 0; i < cnpj.length - 1; i++)
      if (cnpj.charAt(i) != cnpj.charAt(i + 1))
            {
            digitos_iguais = 0;
            break;
            }
	if (!digitos_iguais)
      {
      tamanho = cnpj.length - 2
      numeros = cnpj.substring(0,tamanho);
      digitos = cnpj.substring(tamanho);
      soma = 0;
      pos = tamanho - 7;
      for (i = tamanho; i >= 1; i--)
            {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                  pos = 9;
            }
      resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
      if (resultado != digitos.charAt(0))
            return false;
      tamanho = tamanho + 1;
      numeros = cnpj.substring(0,tamanho);
      soma = 0;
      pos = tamanho - 7;
      for (i = tamanho; i >= 1; i--)
            {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                  pos = 9;
            }
      resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
      if (resultado != digitos.charAt(1))
            return false;
      return true;
      }
	else
      return false;
} 
//  End -->