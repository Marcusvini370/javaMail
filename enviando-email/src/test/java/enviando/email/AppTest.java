package enviando.email;


public class AppTest {
	
	
	
	
	
		@org.junit.Test
		public void testeEmail() throws Exception {
			
			StringBuilder stringBuilderTextoEmail = new StringBuilder();
			
			stringBuilderTextoEmail.append("Olá, <br/><br/> "); 
			stringBuilderTextoEmail.append("VocÊ está recebendo acesso ao curso de Java. <br/><br/>"); 
			stringBuilderTextoEmail.append("Para ter acesso clique no botão abaixo. <br/><br/>"); 
			
			stringBuilderTextoEmail.append("<b>Login:</b> marcusvini@asdasdas.com </br>"); 
			stringBuilderTextoEmail.append("<b>Senha:</b> asdas14da12s1dasdm </br><br/>"); 
	
			
			stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"https://www.projetojavaweb.com/certificado-aluno/login\" style=\"color:#2525a7;  padding: 14px 24px; text-align:center; text-decoration:none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border:3px solid green; background-color: #99DA39;\"> Acessar Portal do Aluno </a><br><br/>");
			
			stringBuilderTextoEmail.append("<span style=\"font-size:8px;\">Ass.: Marcus Vinicius Martins </span>"); 
			
			ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("marcus.matos@cnj.jus.br", 
																"Marcus Vinicius",
																"Testando e-mail com java",
																stringBuilderTextoEmail.toString());
			
			enviaEmail.enviarEmailAnexo(true);
			
			Thread.sleep(10000);
		}
			
}

    
