package xyz.symhx.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import xyz.symhx.mail.entity.MailInfo;
import xyz.symhx.mail.service.MailService;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SendEmailApplicationTests {

    @Resource
    MailService mailService;

    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() {
        MailInfo mailInfo = new MailInfo();
        String[] toUser = {"接受人qq邮箱"};
        mailInfo.setTo(toUser);
        mailInfo.setSubject("普通邮件");
        mailInfo.setContent("Hello mail!");
        mailService.sendSimpleMail(mailInfo);
    }

    @Test
    public void sendHtmlMail() {
        MailInfo mailInfo = new MailInfo();
        String[] toUser = {"接受人qq邮箱"};
        mailInfo.setTo(toUser);
        mailInfo.setSubject("HTML邮件");
        String content ="<html>\n <body>\n <h1> HTML邮件！</h1>\n </body>\n </html>\n ";
        mailInfo.setContent(content);
        mailService.sendHtmlMessage(mailInfo);
    }

    @Test
    public void sendAttachMentMail() {
        MailInfo mailInfo = new MailInfo();
        String[] toUser = {"接受人qq邮箱"};
        mailInfo.setTo(toUser);
        mailInfo.setSubject("附件邮件");
        String content ="<html>\n <body>\n <h1> 附件邮件 </h1>\n </body>\n </html>\n ";
        mailInfo.setContent(content);
        String[] path = {"I:\\WorkSpace\\project\\mail\\src\\main\\resources\\application.yml","I:\\WorkSpace\\project\\mail\\src\\main\\java\\xyz\\symhx\\mail\\service\\MailService.java"};
        mailInfo.setAttachmentPath(Arrays.asList(path));
        mailService.sendAttachMentMessage(mailInfo);
    }

    @Test
    public void sendImageMail() {
        MailInfo mailInfo = new MailInfo();
        String[] toUser = {"接受人qq邮箱"};
        mailInfo.setTo(toUser);
        mailInfo.setSubject("图片邮件");
        String content ="<html><body><img src='cid:p1'><img src='cid:p2'></body></html>";
        mailInfo.setContent(content);
        String[] path = {"I:\\1.jpg","I:\\d.png"};
        String[] rid = {"p1", "p2"};
        mailInfo.setImagePath(path);
        mailInfo.setRscId(rid);
        mailService.sendImageMessage(mailInfo);
    }

    @Test
    public void sendTemplateMail() {
        MailInfo mailInfo = new MailInfo();
        String[] toUser = {"接受人qq邮箱"};
        mailInfo.setTo(toUser);
        mailInfo.setSubject("模板邮件");
        Context context = new Context();
        String emailContent = templateEngine.process("/mail",context);
        mailInfo.setContent(emailContent);
        mailService.sendHtmlMessage(mailInfo);
    }
}
