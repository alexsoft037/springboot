package xin.xiaoer.modules.story.entity;

import java.io.Serializable;



/**
 *
 *
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:15
 */
public class SpaceStoryItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/****/
	private Long storyId;
	/****/
	private String itemImage;
	/****/
	private String itemContent;

	private String type;

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
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	/**
	 * 获取：
	 */
	public String getItemImage() {
		return itemImage;
	}
	/**
	 * 设置：
	 */
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	/**
	 * 获取：
	 */
	public String getItemContent() {
		return itemContent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
