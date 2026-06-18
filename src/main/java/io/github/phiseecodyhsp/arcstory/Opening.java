package io.github.phiseecodyhsp.arcstory;

import io.github.phiseecodyhsp.arcstory.storage.Resources;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;

//TODO
public class Opening extends StackPane {
    private static final Font EN_FONT = Resources.getFont(Resources.GeosansLight_FONT, 0);;
    private static final Font BIG_EN_FONT = Resources.getFont(Resources.GeosansLight_FONT, 0);;
    private static final Font JP_FONT = Resources.getFont(Resources.GeosansLight_FONT, 0);;
    private static final TextFlow[] ETERNAL;
    private static final TextFlow[] FINAL;
    private static final TextFlow[] SILENT;
    private static final TextFlow[] LUCENT;

    private final ImageView bg = new ImageView();
    
    public Opening() {}

    public void play(StackPane parent, Type type, Node nextNode) {
        getChildren().clear();
        getChildren().addAll(bg, type.flows[0], type.flows[1], type.flows[2]);
        parent.getChildren().add(this);
    }

    private static void setFont(Font font, Text... texts) {
        for (Text t : texts) {
            t.setFont(font);
        }
    }

    private static void setStroke(Paint paint, Text... texts) {
        for (Text t : texts) {
            t.setStroke(paint);
            t.setStrokeWidth(0);
        }
    }

    static {
        Text en1 = new Text("A HARMONY OF ");
        Text L = new Text("L");
        Text ight = new Text("ight");
        Text n1 = new Text("\n");
        Text jp1 = new Text("「");
        Text hikari = new Text("光");
        Text jp2 = new Text("奏でる調和」が");

        Text en2 = new Text("AWAITS YOU IN A LOST WORLD");
        Text n2 = new Text("\n");
        Text jp3 = new Text("「音が");
        Text tairitsu = new Text("対立");
        Text jp4 = new Text("する」失われる世界で");

        Text en3 = new Text("OF MUSICAL ");
        Text C = new Text("C");
        Text onflict = new Text("onflict...");
        Text n3 = new Text("\n");
        Text jp5 = new Text("あなたを待ち受ける…");

        setFont(EN_FONT, en1, ight, en2, en3, onflict);
        setFont(BIG_EN_FONT, L, C);

        ETERNAL = new TextFlow[]{
                new TextFlow(en1, L, ight, n1, jp1, hikari, jp2),
                new TextFlow(en2, n2, jp3, tairitsu, jp4),
                new TextFlow(en3, C, onflict, n3, jp5)};
    }

    static {
        Text en1 = new Text("A HARMONY OF ");
        Text L = new Text("L");
        Text ight = new Text("ight");
        Text n1 = new Text("\n");
        Text jp1 = new Text("「");
        Text hikari = new Text("光");
        Text jp2 = new Text("奏でる調和」が");

        Text en2 = new Text("AWAITS YOU IN A LOST WORLD");
        Text n2 = new Text("\n");
        Text jp3 = new Text("「音が");
        Text tairitsu = new Text("対立");
        Text jp4 = new Text("する」失われる世界で");

        Text en3 = new Text("OF MUSICAL ");
        Text C = new Text("C");
        Text onflict = new Text("onflict...");
        Text n3 = new Text("\n");
        Text jp5 = new Text("あなたを待ち受ける…");

        setFont(EN_FONT, en1, ight, en2, en3, onflict);
        setFont(BIG_EN_FONT, L, C);
        setFont(JP_FONT, jp1, jp2, jp3, jp4, jp5);

        FINAL = new TextFlow[]{
                new TextFlow(en1, L, ight, n1, jp1, hikari, jp2),
                new TextFlow(en2, n2, jp3, tairitsu, jp4),
                new TextFlow(en3, C, onflict, n3, jp5)};
    }

    static {
        Text en1 = new Text("A FADING ");
        Text L = new Text("L");
        Text ight = new Text("ight");
        Text n1 = new Text("\n");
        Text jp1 = new Text("『消えゆく");
        Text hikari = new Text("光");
        Text jp2 = new Text("』が");

        Text en2 = new Text("AWAITS TWO IN A CLOSED WORLD");
        Text n2 = new Text("\n");
        Text jp3 = new Text("『忘れられた");
        Text tairitsu = new Text("対立");
        Text jp4 = new Text("』の果て");

        Text en3 = new Text("OF MUSICAL ");
        Text C = new Text("C");
        Text onflict = new Text("onflict...");
        Text n3 = new Text("\n");
        Text jp5 = new Text("閉じ往く世界で、二人を待つ");

        setFont(EN_FONT, en1, ight, en2, en3, onflict);
        setFont(BIG_EN_FONT, L, C);
        setFont(JP_FONT, jp1, jp2, jp3, jp4, jp5);

        SILENT = new TextFlow[]{
                new TextFlow(en1, L, ight, n1, jp1, hikari, jp2),
                new TextFlow(en2, n2, jp3, tairitsu, jp4),
                new TextFlow(en3, C, onflict, n3, jp5)};
    }

    static {
        Text en1 = new Text("DIVINE GRACE");
        Text n1 = new Text("\n");
        Text jp1 = new Text("『光』も『対立』もない");

        Text en2 = new Text("FALLS UPON YOU IN A DEAD WORLD");
        Text n2 = new Text("\n");
        Text jp2 = new Text("死んだ世界の中心でいま");

        Text en3 = new Text("WITHOUT LIGHT OR CONFLICT");
        Text n3 = new Text("\n");
        Text jp3 = new Text("『神の祝福』があなたに降り注ぐ…");

        setFont(EN_FONT, en1, en2, en3);
        setFont(JP_FONT, jp1, jp2, jp3);

        LUCENT = new TextFlow[]{
                new TextFlow(en1, n1, jp1),
                new TextFlow(en2, n2, jp2),
                new TextFlow(en3, n3, jp3)};
    }

    //TODO
    public enum Type {
        ETERNAL(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.ETERNAL),
        VICIOUS(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.ETERNAL),
        LUMINOUS(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.ETERNAL),
        BLACK(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.ETERNAL),
        FINAL(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.FINAL),
        SILENT(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.SILENT),
        ASCENSION(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.LUCENT),
        NEXT(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.LUCENT),
        DEFAULT(Resources.STORY_MODE_BGM, 0, Resources.CHAPTER5_SCENERY, Opening.LUCENT);

        private final String musicPath;
        private final double climaxTime;
        private final String bgPath;
        private final TextFlow[] flows;

        Type(String musicPath,
             double climaxTime,
             String bgPath,
             TextFlow[] flows) {
            this.musicPath = musicPath;
            this.climaxTime = climaxTime;
            this.bgPath = bgPath;

            if (flows.length != 3) {
                throw new IllegalArgumentException();
            } else {
                this.flows = flows;
            }
        }
    }
}
