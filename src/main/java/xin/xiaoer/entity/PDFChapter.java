package xin.xiaoer.entity;

import java.io.Serializable;
import java.util.List;

public class PDFChapter implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private Integer page;

    private List<PDFChapter> chapterList;
}
