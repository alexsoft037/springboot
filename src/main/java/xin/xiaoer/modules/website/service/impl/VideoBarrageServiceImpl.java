package xin.xiaoer.modules.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.website.dao.VideoBarrageDao;
import xin.xiaoer.modules.website.entity.VideoBarrage;
import xin.xiaoer.modules.website.service.VideoBarrageService;

import java.util.List;

/**
 * @ClassName VideoBarrageServiceImpl
 * @Description TODO
 * @Author XiaoDong
 **/
@Service("videoBarrageService")
@Transactional
public class VideoBarrageServiceImpl implements VideoBarrageService {

    @Autowired
    private VideoBarrageDao videoBarrageDao;

    @Override
    public void save(VideoBarrage videoBarrage) {
        videoBarrageDao.save(videoBarrage);
    }

    @Override
    public List<VideoBarrage> getByVideoId(Integer videoId) {
        return videoBarrageDao.getByVideoId(videoId);
    }
}
