package br.com.guilhermefausto.teavisei.exception;

public class ErroValidacaoFormularioDto {
	
		private String campo;
		private String erro;
		
		public ErroValidacaoFormularioDto(String campo, String erro) {
			this.campo = campo;
			this.erro = erro;
		}

		public String getCampo() {
			return campo;
		}

		public String getErro() {
			return erro;
		}

}
