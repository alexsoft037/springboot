package xin.xiaoer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import xin.xiaoer.common.utils.Const;
import xin.xiaoer.common.utils.PathUtil;

import java.io.Serializable;
import java.util.Date;


/**
 * 地产附件表
 * 
 * @author chenyi
 * @email qq228112142@qq.com
 * @date 2017-11-17 11:52:01
 */
public class File implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**id**/
	private String id;
	/****/
	private Integer spaceId;
	/****/
	private String spaceName;
	/**关联标识(UUID)**/
	private String uploadId;
	/**url**/
	private String url;
	/**thumbnail url**/
	private String thumbnailUrl;
	/**文件名**/
	private String fileName;
	/**上传时间**/
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**附件类型**/
	private String fileType;
	/**附件大小**/
	private String fileSize;

	private String ossYn;

	private Long createBy;

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置：url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：文件名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置：附件类型
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * 获取：附件类型
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * 设置：附件大小
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * 获取：附件大小
	 */
	public String getFileSize() {
		return fileSize;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getOssYn() {
		return ossYn;
	}

	public void setOssYn(String ossYn) {
		this.ossYn = ossYn;
	}

	public boolean deleteFile(){
		try {
			java.io.File mainFile = new java.io.File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + this.url);
			if (mainFile.exists()){
				mainFile.delete();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		try {
			java.io.File thumbFile = new java.io.File(PathUtil.getClassResources() + Const.FILEPATH_STATIC + this.thumbnailUrl);
			if (thumbFile.exists()){
				thumbFile.delete();
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return true;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
}
