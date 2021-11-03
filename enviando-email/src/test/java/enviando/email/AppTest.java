package enviando.email;

import org.junit.Test;
//import enviando.email.ObjetoEnviaEmail;


/**
 * Unit test for simple App.
 */
public class AppTest {
	
	
	@Test  
	public void testeEmail() throws Exception{
		
		//para usar no html
		StringBuilder stringBuilderTestoEmail = new StringBuilder();
		
		stringBuilderTestoEmail.append("Olá! <br/><br/>");// texto com 2 quebra de linha em html
		stringBuilderTestoEmail.append("Este é um teste javamail com Html. Exemplo de email com botao e login <br/><br/>");
		stringBuilderTestoEmail.append("exemplo de link de pagina.Clik para abrir a pag. <br/><br/>");
		
		stringBuilderTestoEmail.append("<b>Login: </b> Alex@lobus.com.br <br/>");
		stringBuilderTestoEmail.append("<b>Senha: </b>ffRty23 <br/><br/>");
		//leva para a pagina de login do curso
		stringBuilderTestoEmail.append("<a target=\"_blank\" href=\" https://www.projetojavaweb.com/certificado-aluno/login\""
				+ "style=\"color:#2525a7; padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block;"
				+ "border-radius: 30px; font-size: 20px; font-family: courier; border: 3px solide green; background-color:#99DA39;"
				+ "\">Acesse portal do aluno</a> <br/><br/>");//botão com link
		
		stringBuilderTestoEmail.append("<span style=\"font-size: 8px\">Ass.: Lobão </span><br/>");//span para formatar o texto
		/*usando o html pode-se fazer o que se quiser usando tbm imagem, videos e os recurso que for disponivel com ele*/
		
		
		ObjetoEnviaEmail envEmail = new ObjetoEnviaEmail("phoenixtesthc@gmail.com",
									"LobuSoftware", 
									"Teste e-mail com Java",
									stringBuilderTestoEmail.toString()); //variavel com toString e constroi-se o Html
									//"Este é um texto para testar o envio do e-mail do Java 02/11/21");
		
		//metodo de envio 
		//envEmail.enviarEmail(true); //para usar o html
		
		envEmail.enviarEmailAnexo(true);
		
		/*Caso o e-mail não esteja sendo enviado colocar um tempo de espera, porem só pode ser usado para teste*/
		Thread.sleep(3000); //espera 5 segundo antes de matar o processo
		
	}	
	
}
