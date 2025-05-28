import java.util.*;

import com.augmentedlogic.wikithings.*;

public class Example
{

    public static void main(String[] args) {

        try {

            WikithingsClient wikithingsClient = new WikithingsClient();
            wikithingsClient.setLanguage("en");
            wikithingsClient.setLimit(100);

            WikipediaExtract extract = wikithingsClient.getExtract("Alberta");
            System.out.println(extract.getText());

            // TODO
            WikipediaArticle wa = wikithingsClient.getArticle("Alberta");


            ArrayList<String> aimages = wa.getImages();
            System.out.println(aimages);

            ArrayList<String> exlinks = wa.getExternalLinks();
            for(String exlink : exlinks) {
                System.out.println(exlink);
            }
            // loop

            System.out.println("---------------------------");
            System.out.println(wa.getText()); // HTML
            System.out.println("---------------------------");
            System.out.println(wa.getPlainText()); // TEXT BLOCK
            System.out.println("---------------------------");
            System.out.println(wa.getPrettyPrintText()); // TEXT PRETTY


            // WORKS
            ArticleImage articleImage = wikithingsClient.getArticleImage("Albert_Einstein");
            int w = articleImage.getHeight();
            int h = articleImage.getWidth();
            System.out.println(w + "x" + h);

            // WORKS
            Category[] categories = wikithingsClient.getPageCategories("Albert_Einstein");
            for(Category category : categories) {
                System.out.println(category.getTitle());
                System.out.println(category.getNamespace());
            }

            // CHECK
            FileInfo fileInfo = wikithingsClient.getFileInfo("Brad_Pitt_at_Incirlik2.jpg");

            // TODO: all fields

            // WORKS, DOCUMENT
            CategoryMember[] categoryMembers = wikithingsClient.getCategoryMembers("Spaceflight");
            for(CategoryMember categoryMember: categoryMembers) {
                System.out.println(categoryMember.getTitle());
                System.out.println(categoryMember.getPageId());
            }

            SearchResult[] searchResults = wikithingsClient.searchArticles("Einstein");
            for(SearchResult searchResult : searchResults) {
                System.out.println(searchResult.getTitle());
                System.out.println(searchResult.getPageId());
                System.out.println(searchResult.getSize());
                System.out.println(searchResult.getWordcount());
                System.out.println(searchResult.getSnippet());
                System.out.println(searchResult.getPlainTextSnippet());
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
