package enviando.email;

import org.junit.Test;
import enviando.email.ObjetoEnviaEmail;


/**
 * Unit test for simple App.
 */
public class AppTest {
	
	
	@Test  
	public void testeEmail() throws Exception{
		
		ObjetoEnviaEmail envEmail = new ObjetoEnviaEmail("phoenixtesthc@gmail.com",
									"LobuSoftware", 
									"Teste e-mail com Java",
									"Este é um texto para testar o envio do e-mail do Java");
		
		//metodo de envio 
		envEmail.enviarEmail();
		
		/*Caso o e-mail não esteja sendo enviado colocar um tempo de espera, porem só pode ser usado para teste*/
		//Thread.sleep(5000); //espera 5 segundo antes de matar o processo
		
	}	
	
}
