package enviando.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	//deixar apenas a classe de AppTest
	
	//criando variavel que receberá o email e a senha a serem usados
	private String userName = "phoenixtesthc@gmail.com";
	private String senha = "lobao2131";
	
    /**
     * Rigorous Test :-)
     */
   /* @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    } 
     */
	
	//criando o metodo de teste com a anotação @org.junit.Test para rodar sem precisar de uma tela com dados
	@Test  //novo tipo de anotação	
	public void testeEmail() {
		/*È necessario olhar as configurações do SMTP do e-mail, logo é boa pratica criar um e-mail apenas para testes*/
		
		//usando um try catch para pegar alguma excessão 
		try {			

			//usando o smtp do Gmail
			Properties properties = new Properties();
			
			properties.put("mail.smtp.ssl.trust","*");/*autorização com ssl,"*" para todos*/
			properties.put("mail.smtp.auth","true");/*autorização*/
			properties.put("mail.smpt.starttls","true");/*autenticação, segurança*/
			properties.put("mail.smtp.host","smtp.gmail.com");/*servidor Gmail Google*/
			properties.put("mail.smtp.port", "465"); /*porta do servidor*/
			properties.put("mail.smtp.socketFactory.port", "465");/*especifica a porta a ser conctada pelo socket*/
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//classe socket de conexão ao SMTP*/

			Session session = Session.getInstance(properties, new Authenticator() {
				//chamaro metodo getPasswordAuthentication -Override method in Authentication, sobreescrevendo o metodo Authentication
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// onde estava: super.getPasswordAuthentication(), vai um novo password Authentication
					return new PasswordAuthentication(userName, senha);
				}
			});
			
			/*preparamdo para enviar par 1 ou mais e-mail (lista)*/
			Address[] toUser = InternetAddress.parse("hcxmoraes@gmail.com , phoenixtesthc@gmail.com");
			
			/*mensagem*/
			Message message = new MimeMessage(session); //recebe a session preparada para envio
			message.setFrom(new InternetAddress(userName,"LoboSoftware"));// quem eta enviando o e-mail
			message.setRecipients(Message.RecipientType.TO, toUser);/*email de destino, passa a lista de email*/
			message.setSubject("Chegou e-mail enviado com Java");/*Assunto*/
			message.setText("E-mail de teste do curso formação java web");/*mensagem do email*/
			
			Transport.send(message);/*transporta o email e poderia passar tudo aqui de forma direta, usasei o objeto message*/
			
			/*Caso o e-mail não esteja sendo enviado colocar um tempo de espera, porem só pode ser usado para teste*/
			//Thread.sleep(5000); //espera 5 segundo antes de matar o processo
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}
