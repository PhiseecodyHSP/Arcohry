package io.github.phiseecodyhsp.arcstory.view;

import io.github.phiseecodyhsp.arcstory.res.ResourceLoader;
import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * 文本播放器.
 *
 * @see Typewriter
 *
 * @author RikkaKawaii0612
 */
public class TextPlayer extends TextFlow {

    private static final double FONT_SIZE = 30.0D;

    private static final double LINE_SPACING = FONT_SIZE / 2.0D;

    private static final Font FONT = ResourceLoader.loadFont(ResourceLocation.font("noto_sans_regular"), FONT_SIZE);

    private final ObservableList<Text> characters = FXCollections.observableArrayList();

    public TextPlayer(Typewriter printer) {
        this.setMaxHeight(0.0D);
        this.setLineSpacing(LINE_SPACING);

        Bindings.bindContent(this.getChildren(), this.characters);

        printer.fullTextProperty().addListener((_, _, s) -> {
            this.characters.clear();
            for (char c : s.toCharArray()) {
                Text text = new Text(String.valueOf(c));
                text.setFill(Color.TRANSPARENT);
                text.setFont(FONT);
                this.characters.add(text);
            }
        });

        printer.currentIndexProperty().addListener((_, oldVar, newVar) -> {
            int start = oldVar.intValue();
            int end = newVar.intValue();
            for (int i = start; i < end; i++) {
                this.characters.get(i).setFill(Color.WHITE);
            }
            this.requestLayout();
        });
    }
}
