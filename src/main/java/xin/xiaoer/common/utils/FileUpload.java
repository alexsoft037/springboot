package xin.xiaoer.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import xin.xiaoer.common.oss.OSSFactory;

/**
 * 上传文件
 * 创建人：FH Q313596790
 * 创建时间：2014年12月23日
 * @version
 */
public class FileUpload {

	/**
	 * @param file 			//文件对象
	 * @param filePath		//上传路径
	 * @param fileName		//文件名
	 * @return  文件名
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName){
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0){
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
		} catch (IOException e) {
			System.out.println(e);
		}
		return fileName+extName;
	}

	public static String fileUp(MultipartFile file, String filePath, String fileName, String thumbnailPath, int thumbnailWith, int thumbnailHeight){
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0){
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
			File thumbDirectory = new File(thumbnailPath);
			if (!thumbDirectory.exists()) {
				thumbDirectory.mkdirs();
			}
			Thumbnails.of(new File(filePath, fileName+extName)).size(thumbnailWith, thumbnailHeight).crop(Positions.CENTER).keepAspectRatio(true).toFile(new File(thumbnailPath, fileName+extName));
		} catch (IOException e) {
			System.out.println(e);
		}
		return fileName+extName;
	}

	public static String fileUpLoadImageContent(String fileContent, int thumbnailWith, int thumbnailHeight){

		String url = "";

//		String ffile = DateUtil.getYmd();
//		String photoPath = ffile + "/" + String.format("%s.jpg", UuidUtil.get32UUID());
//		java.io.File directory = new java.io.File(PathUtil.getClassResources() +  Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + ffile);
//		java.io.File thumbDirectory = new java.io.File(PathUtil.getClassResources() +  Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD_THUMBNAIL + ffile);
//		if (!directory.exists()) {
//			directory.mkdirs();
//		}
//		if (!thumbDirectory.exists()) {
//			thumbDirectory.mkdirs();
//		}

		byte[] data = Base64.decodeBase64(fileContent.replaceFirst("^data:image/[^;]*;base64,?", ""));
		try {

//			String filePath = PathUtil.getClassResources() +  Const.FILEPATH_STATIC +  Const.FILEPATH_UPLOAD + photoPath;
//			String thumbnailPath = PathUtil.getClassResources() +  Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD_THUMBNAIL + photoPath;
//			url = photoPath;
//			java.io.File f = new java.io.File(filePath);
//			f.createNewFile();
//			FileOutputStream os = new FileOutputStream(f);
//			os.write(data);
//			os.close();
//			Thumbnails.of(f).size(thumbnailWith, thumbnailHeight).crop(Positions.CENTER).keepAspectRatio(true).toFile(thumbnailPath);

			url = OSSFactory.build().uploadSuffix(data, ".jpg");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}
	
	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @throws IOException
	 */
	private static String copyFile(InputStream in, String dir, String realName)
			throws IOException {
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}
}
