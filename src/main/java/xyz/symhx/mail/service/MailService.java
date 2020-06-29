package xyz.symhx.mail.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import xyz.symhx.mail.entity.MailInfo;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Slf4j
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送简单的文本邮件
     * @param mailInfo
     */
    public void sendSimpleMail(MailInfo mailInfo){
        log.info("发送邮件开始。相关参数:{}",mailInfo.toString());
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(mailInfo.getTo());
            simpleMailMessage.setSubject(mailInfo.getSubject());
            simpleMailMessage.setText(mailInfo.getContent());
            simpleMailMessage.setFrom(from);
            javaMailSender.send(simpleMailMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
            log.error("发送邮件失败:{}",e.getMessage());
        }
    }

    /**
     * 发送HTML格式文件
     * @param mailBean
     * @throws Exception
     */
    public void  sendHtmlMessage(MailInfo mailBean) {
        log.info("发送邮件开始。相关参数:{}",mailBean.toString());
        try{
            MimeMessage mimeMessage=javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(mailBean.getTo());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent(),true);
            mimeMessageHelper.setFrom(from);
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
            log.error("发送邮件失败:{}",e.getMessage());
        }
    }

    /**
     *发送带有附件的邮件
     * @param mailInfo
     */
    public void sendAttachMentMessage(MailInfo mailInfo){
        log.info("发送邮件开始。相关参数:{}",mailInfo.toString());
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mailInfo.getTo());
            mimeMessageHelper.setSubject(mailInfo.getSubject());
            mimeMessageHelper.setText(mailInfo.getContent(),true);
            mimeMessageHelper.setFrom(from);
            //准备附件相关
            List<String> paths=mailInfo.getAttachmentPath();
            for (String path:paths) {
                FileSystemResource file= new FileSystemResource(new File(path));
                String fileName = file.getFilename();
                mimeMessageHelper.addAttachment(fileName,file);
            }
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
            log.error("发送邮件失败:{}",e.getMessage());
        }
    }

    /**
     * 发送带有图片的邮件
     * @param mailInfo
     */
    public void sendImageMessage(MailInfo mailInfo){
        log.info("发送邮件开始。相关参数:{}",mailInfo.toString());
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(mailInfo.getTo());
            mimeMessageHelper.setSubject(mailInfo.getSubject());
            mimeMessageHelper.setText(mailInfo.getContent(),true);
            mimeMessageHelper.setFrom(from);
            //准备图片附件相关imagePath
            for(int i = 0; i < mailInfo.getImagePath().length; i++){
                FileSystemResource res = new FileSystemResource(new File(mailInfo.getImagePath()[i]));
                mimeMessageHelper.addInline(mailInfo.getRscId()[i], res);
            }
            javaMailSender.send(mimeMessage);
            log.info("发送邮件成功!");
        }catch (Exception e){
            log.error("发送邮件失败:{}",e.getMessage());
        }
    }

}
