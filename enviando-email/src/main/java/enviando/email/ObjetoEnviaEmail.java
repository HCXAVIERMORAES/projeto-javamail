package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

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
	public void enviarEmail(boolean envioHtml) throws Exception{		

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
		
		//condicional para e-mail com html
		if(envioHtml) {
			message.setContent(textoEmail, "text/html; charset = utf-8"); //evia se for com html
		} else {
			message.setText(textoEmail);/*mensagem do email*/ //envio se não for como html - ou seja, false.
		}
		
		
		
		Transport.send(message);/*transporta o email e poderia passar tudo aqui de forma direta, usasei o objeto message*/
		
	}
	
	/**
	 * Metodo para envio de e-mail com anexo 
	 * */

	public void enviarEmailAnexo(boolean envioHtml) throws Exception{		

		
		Properties properties = new Properties();		
		properties.put("mail.smtp.ssl.trust","*");
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smpt.starttls","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port", "465"); 
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(userName, senha);
			}
		});
		
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session); 
		message.setFrom(new InternetAddress(userName,nomeRemetente));
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assuntoEmail);
		
		/**
		 * o e-mail vai ser constituido de:
		 * 1ª parte do e-mail é a do texto e descrição dele. cria-se novas classes para o email.
		 * 2ª parte são os anexo 
		 * 3ª parte se uni as partes 1 e 2
		 * */
		MimeBodyPart corpoEmail = new MimeBodyPart();
	
		
		if(envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset = utf-8"); 
		} else {
			corpoEmail.setText(textoEmail);
		}
		
		//2ª parte
		MimeBodyPart anexoEmail = new MimeBodyPart();
		
		/*Onde é passado o simuladorDePDF(), deve ir o arquivo que se deseja, seja gravado no banco de dados ou outros*/
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladorDePDF(), "application/pdf")));
		anexoEmail.setFileName("anexoemail.pdf");
		
		//3ª parte
		Multipart multipart = new MimeMultipart(); //junta as duas partes
		multipart.addBodyPart(corpoEmail);
		multipart.addBodyPart(anexoEmail);
				
		message.setContent(multipart);
		
		Transport.send(message);
		
	}
	
	/**
	 * Esse metodo simula o PDF ou qualquer arquivo qque possa ser enviado por anexo no e-mail.
	 * Pode-se pegar o arquivo no banco de dados podendo ser base64, byte[], strean de arquivos.
	 * pode estar em um banco de dados, ou em pasta, aqui é para simular.
	 * 
	 * basicamente retorna um PDF em branco com o texto do paragrafo de exemplo passado
	 * */
	
	private FileInputStream simuladorDePDF() throws Exception{
		
		Document document = new Document();//document do itextpdf
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		/*escreve dentro do pdf(document) o conteudo do arquivo(file)*/
		PdfWriter.getInstance(document, new FileOutputStream(file));//passa o documento e a escrita desse pdf
		document.open();//abri o ducumento
		document.add(new Paragraph("Conteúdo do PDF anexo com Javamail, este texto e do PDF data 03/11/2021"));
		document.close();//fecha o documento
		
		return new FileInputStream(file);
		
	}
	
}
