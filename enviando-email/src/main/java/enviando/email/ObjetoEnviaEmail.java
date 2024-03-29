package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import com.itextpdf.text.List;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {
	
	private String userName = "\"seuEmailQueVaiEnviarProsDestinatario@gmail.com";
	private String senha = "suaSenha";
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	
	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail) {
		
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
		
	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception{
		
		
		/* Configurações do smtp do seu email*/
		
		Properties properties = new Properties(); /**/
		
		properties.put("mail.smtp.ssl.trust", "*"); /* Autorização */
		properties.put("mail.smtp.auth", "true"); /* Autorização */
		properties.put("mail.smtp.starttls", "true"); /* Autenticação */
		properties.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor gmail Google*/
		properties.put("mail.smtp.port", "465"); /* Porta do Servidor */
		properties.put("mail.smtp.socketFactory.port", "465"); /*Expecifica a porta a ser conectada pelo socket  */
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); /* Classe socket de conexão ao smtp */
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
			
				return new PasswordAuthentication(userName, senha);
			}
		});
		
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); /* Quem está enviando */
		message.setRecipients(Message.RecipientType.TO, toUser); /* E-mail de destino */
		message.setSubject(assuntoEmail); /* Assunto do e-mail */
		
		/*Parte 1 do e-mail qque é texto e a descrição do e-mail */
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		
		if(envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
			
		}else {
			
			corpoEmail.setText(textoEmail);
		}
		
		java.util.List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePDF());/*Certificado*/
		arquivos.add(simuladorDePDF());/*nota fiscal*/
		arquivos.add(simuladorDePDF());/*documento texto*/
		arquivos.add(simuladorDePDF()); /*Imagem*/
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);
		
		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {
			
		/* Parte 2 do e-mail que são os anexos do pdf */
		MimeBodyPart anexoEmail = new MimeBodyPart();
		
		/* Onde é passado o simuladorDePDF voce passa o seu arquivo gravado no bando de dados */
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladorDePDF(), "application/pdf")));
		anexoEmail.setFileName("anexoemail.pdf");
		
	
		multipart.addBodyPart(anexoEmail);
		index++;
		
	}
		message.setContent(multipart);
		
		Transport.send(message);
	}
	
	/* Esse método simula o PDF ou qualquer arquivo que possa ser enviado por anexo no email. 
	   Voce pode pegar o arquivo no seu banco de dados base64, byte[], Stream de Arquivos.
	   Pode estar em um banco de dados ou em uma pasta.
	   
	   Retorna um PDF em Branco com o texto do Paragrafo de exemplo
	   */
	
	private FileInputStream simuladorDePDF() throws Exception{
			Document document = new Document();
			File file = new File("fileanexo.pdf");
			file.createNewFile();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			document.add(new Paragraph("Conteúdo do PDF anexo com Java Mail, esse texto é do PDF"));
			document.close();
			
			return new FileInputStream(file);
		
	}

}
