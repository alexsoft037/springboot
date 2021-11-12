package xin.xiaoer.common.utils;

import com.itextpdf.kernel.pdf.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFChapterReader {

    private ArrayList<Map<String, Object>> allChapters;

    public PDFChapterReader () {
        allChapters = new ArrayList<>();
    }

    public ArrayList<Map<String, Object>> readerChapters(String filePath) throws Exception{
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(filePath));
        PdfNameTree destsTree = pdfDocument.getCatalog().getNameTree(PdfName.Dests);
        PdfOutline outlines = pdfDocument.getOutlines(false);
        List<PdfOutline> bookmarks = outlines.getAllChildren();

        for (PdfOutline bookmark : bookmarks) {
            allChapters.add(getChapter(bookmark, destsTree.getNames(), pdfDocument));
        }
        pdfDocument.close();

        return this.allChapters;
    }

    private Map<String, Object> getChapter(PdfOutline outline, Map<String, PdfObject> names, PdfDocument pdfDocument) {
//        System.out.println(outline.getTitle());
        Map<String, Object> chapter = new HashMap<>();

        String title = outline.getTitle();
        int pageNumber = 0;
        if (outline.getDestination() != null) {
            pageNumber = pdfDocument.getPageNumber((PdfDictionary) outline.getDestination().getDestinationPage(names));
        }
        chapter.put("title", title);
        chapter.put("page", pageNumber);

        ArrayList<Map<String, Object>> chapters = new ArrayList<>();

        List<PdfOutline> kids = outline.getAllChildren();
        if (kids != null) {
            for (PdfOutline kid : kids) {
                chapters.add(getChapter(kid, names, pdfDocument));
            }
        }
        chapter.put("children", chapters);
        return chapter;
    }
}
