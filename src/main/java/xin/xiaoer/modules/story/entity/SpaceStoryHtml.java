package xin.xiaoer.modules.story.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:19
 */
public class SpaceStoryHtml implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Long storyId;
	/****/
	private String htmlLink;

	/**
	 * 设置：
	 */
	public void setStoryId(Long storyId) {
		this.storyId = storyId;
	}
	/**
	 * 获取：
	 */
	public Long getStoryId() {
		return storyId;
	}
	/**
	 * 设置：
	 */
	public void setHtmlLink(String htmlLink) {
		this.htmlLink = htmlLink;
	}
	/**
	 * 获取：
	 */
	public String getHtmlLink() {
		return htmlLink;
	}
}
