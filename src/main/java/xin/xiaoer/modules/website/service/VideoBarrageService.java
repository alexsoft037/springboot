package xin.xiaoer.modules.website.service;

import xin.xiaoer.modules.website.entity.VideoBarrage;

import java.util.List;

/**
 * @return
 * @Description TODO
 * @Param
 * @Author XiaoDong
 **/
public interface VideoBarrageService {
    void save(VideoBarrage videoBarrage);

    List<VideoBarrage> getByVideoId(Integer videoId);
}
