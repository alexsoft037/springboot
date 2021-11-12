package xin.xiaoer.modules.review.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.entity.Article;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityAttendService;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.classroom.entity.LiveRoom;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.LiveRoomService;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyAttendService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.review.dao.ReviewDao;
import xin.xiaoer.modules.review.entity.Review;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.servicealliance.service.ServiceAllianceService;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.modules.spaceshow.entity.SpaceShow;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.service.SysUserService;

import java.util.List;
import java.util.Map;


@Service("reviewService")
@Transactional
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewDao reviewDao;

	@Autowired
	private DonateSpaceService donateSpaceService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityAttendService activityAttendService;

	@Autowired
	private BookService bookService;

	@Autowired
	private FavouriteService favouriteService;

	@Autowired
	private SpaceStoryService spaceStoryService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ServiceAllianceService serviceAllianceService;

	@Autowired
	private ActivityReportService activityReportService;

	@Autowired
	private SharingLessonService sharingLessonService;

	@Autowired
	private StudyRoomService studyRoomService;

	@Autowired
	private SpaceHeadlineService spaceHeadlineService;

	@Autowired
	private LikeService likeService;

	@Autowired
	private LiveRoomService liveRoomService;

	@Autowired
	private StudyAttendService studyAttendService;

	@Autowired
	private SpaceShowService spaceShowService;

	@Override
	public Review get(Long reviewId){
		return reviewDao.get(reviewId);
	}

	@Override
	public List<Review> getList(Map<String, Object> map){
		return reviewDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return reviewDao.getCount(map);
	}

	@Override
	public void save(Review review){
		reviewDao.save(review);
	}

	@Override
	public void update(Review review){
		reviewDao.update(review);
	}

	@Override
	public void delete(Long reviewId){
		reviewDao.delete(reviewId);
	}

	@Override
	public void deleteBatch(Long[] reviewIds){
		reviewDao.deleteBatch(reviewIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Review review=get(Long.parseLong(id));
			review.setState(stateValue);
            update(review);
        }
    }

    @Override
	public int deleteByArticle(String articleType, Long articleId){
		return reviewDao.deleteByArticle(articleType, articleId);
	}

	@Override
	public int getCountByCodeAndId(String articleType, Long articleId){
		return reviewDao.getCountByCodeAndId(articleType, articleId);
	}

	@Override
	public Review getByArticleAndUser(String articleType, Long articleId, Long userId){
		return reviewDao.getByArticleAndUser(articleType, articleId, userId);
	}

	@Override
	public int deleteByArticleAndUser(String articleType, Long articleId, Long userId){
		return reviewDao.deleteByArticleAndUser(articleType, articleId, userId);
	}

	@Override
	public List<Review> getArticleList(Map<String, Object> map){
		return reviewDao.getArticleList(map);
	}

	@Override
	public int getArticleCount(Map<String, Object> map){
		return reviewDao.getArticleCount(map);
	}

	@Override
	public int getCountByArticle(Map<String, Object> map){
		return reviewDao.getCountByArticle(map);
	}

	@Override
	public Review filterReviewData(Review review){
		if (review.getArticleTypeCode().equals("AT0001")){
			DonateSpace donateSpace = donateSpaceService.get(review.getArticleId());
			if (donateSpace != null){
				review.setArticleTitle(donateSpace.getTitle());
			}
		} else if (review.getArticleTypeCode().equals("AT0002")) {
			SpaceHeadline spaceHeadline = spaceHeadlineService.get(review.getArticleId().intValue());
			if (spaceHeadline != null){
				review.setArticleTitle(spaceHeadline.getTitle());
			}

		} else if (review.getArticleTypeCode().equals("AT0003")){
			ActivityReport activityReport = activityReportService.get(review.getArticleId().intValue());
			if (activityReport != null){
				review.setArticleTitle(activityReport.getTitle());
			}
		} else if (review.getArticleTypeCode().equals("AT0004")){
			Activity activity = activityService.get(review.getArticleId().intValue());
			if (activity != null){
				review.setArticleTitle(activity.getTitle());
			}
		} else if (review.getArticleTypeCode().equals("AT0005")){
			SpaceStory spaceStory = spaceStoryService.get(review.getArticleId());
			if (spaceStory != null){
				review.setArticleTitle(spaceStory.getTitle());
			}
		} else if (review.getArticleTypeCode().equals("AT0006")){
			Book book = bookService.get(review.getArticleId());
			if (book != null){
				review.setArticleTitle(book.getSubject());
			}
		} else if (review.getArticleTypeCode().equals("AT0007")){
			LiveRoom liveRoom = liveRoomService.get(review.getArticleId().intValue());
			if (liveRoom != null){
				review.setArticleTitle(liveRoom.getTitle());
			}
		} else if (review.getArticleTypeCode().equals("AT0008")){
			SharingLesson sharingLesson = sharingLessonService.get(review.getArticleId().intValue());
			if (sharingLesson != null){
				review.setArticleTitle(sharingLesson.getTitle());
			}
		} else if (review.getArticleTypeCode().equals("AT0009")){
			StudyRoom studyRoom = studyRoomService.get(review.getArticleId().intValue());
			if (studyRoom != null){
				review.setArticleTitle(studyRoom.getTitle());
			}
		} else if (review.getArticleTypeCode().equals(Article.SPACE_SHOW)){
			SpaceShow spaceShow = spaceShowService.get(review.getArticleId());
			if (spaceShow != null){
				review.setArticleTitle(spaceShow.getTitle());
			}
		}
		return review;
	}

	@Override
	public List<Review> getAdminList(Map<String, Object> map){
		return reviewDao.getAdminList(map);
	}

	@Override
	public int getAdminCount(Map<String, Object> map){
		return reviewDao.getAdminCount(map);
	}

	@Override
	public  Review getDetail(Long reviewId){
		return reviewDao.getDetail(reviewId);
	}

	@Override
	public List getreviewList(Map<String, Object> map) {
		return reviewDao.getreviewList(map);
	}

	@Override
	public int getreviewCount(Map<String, Object> map) {
		return reviewDao.getreviewCount(map);
	}
}
