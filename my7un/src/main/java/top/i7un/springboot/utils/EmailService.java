package top.i7un.springboot.utils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 
* @ClassName: EmailService  
* @Description: 发送邮件  
* @author zhaojun 
* @date 2018年6月22日  
*
 */

@Service("emailService")
public class EmailService {

	private static Logger log = LoggerFactory.getLogger(EmailService.class);
//
//	@Value("${order.mail.SMTPUsername}")
	private String from;
//
//	@Value("${order.mail.SMTPPassword}")
	private String pwd;
//
//	@Value("${order.mail.SMTPHost}")
	private String host;

	private int port = 25;

	public boolean sendMail(String subject, String content,Boolean isMustMulti, List<String> filePaths, List<String> receivers, List<String> copytos,List<String> bccs) {

		boolean isSuccess = false;
		boolean isExistMulti = false;//是否存在附件

		try {

			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			if(CollectionUtils.isNotEmpty(receivers)){
				for(String receiver : receivers){
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
				}
			}

			if(CollectionUtils.isNotEmpty(copytos)){
				for(String copyto : copytos){
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(copyto));
				}
			}
			
			if(CollectionUtils.isNotEmpty(bccs)){
				for(String bcc : bccs){
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
				}
			}
			
			message.setSubject(subject);
			message.addHeader("charset", "UTF-8");

			/* 添加正文内容 */
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html; charset=GBK");
			contentPart.setHeader("Content-Type", "text/html; charset=GBK");
			multipart.addBodyPart(contentPart);

			/* 添加附件 */
			if(CollectionUtils.isNotEmpty(filePaths)){
				for (String file : filePaths) {
					File usFile = new File(file);
					if(usFile.exists()){
						isExistMulti = true;
						MimeBodyPart fileBody = new MimeBodyPart();
						DataSource source = new FileDataSource(file);
						fileBody.setDataHandler(new DataHandler(source));
						fileBody.setFileName(MimeUtility.encodeText(usFile.getName()));
						multipart.addBodyPart(fileBody);
					}
				}
			}

			//不必须附件；必须附件且存在附件
			if (!isMustMulti || (isMustMulti && isExistMulti)) {
				message.setContent(multipart);
				message.setSentDate(new Date());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");

				transport.connect(host, port, from, pwd);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				isSuccess = true;
			}

		} catch (Exception e) {
			isSuccess = false;
			log.error("发送邮件失败，主题为：" + subject, e);
		}

		return isSuccess;
	}

}
