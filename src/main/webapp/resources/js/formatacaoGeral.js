function mascaraCpfCnpj(o, f) {
	v_obj = o
	v_fun = f
	setTimeout('execmascara()', 1)
}

function execmascara() {
	v_obj.value = v_fun(v_obj.value)
}

function cpfCnpj(v) {

	// Remove tudo o que não é dígito
	v = v.replace(/\D/g, "")

	if (v.length < 14) { // CPF
		// Coloca um ponto entre o terceiro e o quarto d�gitos
		v = v.replace(/(\d{3})(\d)/, "$1.$2");

		// Coloca um ponto entre o terceiro e o quarto d�gitos
		// de novo (para o segundo bloco de n�meros)
		v = v.replace(/(\d{3})(\d)/, "$1.$2");

		// Coloca um h�fen entre o terceiro e o quarto d�gitos
		v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");

	} else { // CNPJ
		v = v.replace(/^(\d{2})(\d)/, "$1.$2");
		v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
		v = v.replace(/\.(\d{3})(\d)/, ".$1/$2");
		v = v.replace(/(\d{4})(\d)/, "$1-$2");
	}

	return v
}