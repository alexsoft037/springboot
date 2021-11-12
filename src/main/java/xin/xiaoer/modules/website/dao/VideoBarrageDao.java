package xin.xiaoer.modules.website.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.website.entity.VideoBarrage;

import java.util.List;

public interface VideoBarrageDao extends BaseDao<VideoBarrage> {
    List<VideoBarrage> getByVideoId(@Param("videoId") Integer videoId);
}
