package xyz.symhx.mail.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class MailInfo implements Serializable {

    /**
     * 发送对象
     */
    private String [] to;

    /**
     * 抄送
     */
    private List<String> cc;

    /**
     * 发送人
     */
    private String from;

    /**
     * 主题
     */
    private String subject;

    /**
     * 正文内容
     */
    private String content;

    /**
     * 附件
     */
    private List<String> attachmentPath;

    /**
     * 图片路径
     */
    private String[] imagePath;

    /**
     * 资源id
     */
    private String[] rscId;


}
