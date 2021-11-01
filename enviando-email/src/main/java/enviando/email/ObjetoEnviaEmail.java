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

public class ObjetoEnviaEmail {
	
    //criando variavel que receberá o email e a senha a serem usados
	private String userName = "phoenixtesthc@gmail.com";
	private String senha = "lobao2131";

	//atributo para receber o que se precisa para mandar os e-mails, deixando generico
	private String listaDestinatarios = ""; //  vai no Address[] toUser = InternetAddress.parse();
	private String nomeRemetente = ""; //vai em quem esta enviando
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	//criando um construtor para mandar os emails
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		//super();
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
	
	//metodo enviar email
	public void enviarEmail() throws Exception{		

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
		//Address[] toUser = InternetAddress.parse("hcxmoraes@gmail.com , phoenixtesthc@gmail.com");
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		/*mensagem*/
		Message message = new MimeMessage(session); //recebe a session preparada para envio
		message.setFrom(new InternetAddress(userName,nomeRemetente));// quem eta enviando o e-mail
		message.setRecipients(Message.RecipientType.TO, toUser);/*email de destino, passa a lista de email*/
		message.setSubject(assuntoEmail);/*Assunto*/
		message.setText(textoEmail);/*mensagem do email*/
		
		Transport.send(message);/*transporta o email e poderia passar tudo aqui de forma direta, usasei o objeto message*/
		
	}

	
}
