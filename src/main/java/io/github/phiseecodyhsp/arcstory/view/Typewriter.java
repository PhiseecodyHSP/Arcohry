package io.github.phiseecodyhsp.arcstory.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * 文本打字机, 随时间逐个键入给定文本字符.
 *
 * <p>该类不实际渲染任何元素. 要使用该类, 应给控件绑定文本属性至
 * {@link #currentText} 属性, 并通过 {@link #play(String)} 传入文本. 随后,
 * 文本就会立即播放, 并实时更改 {@link #currentText} 的值.
 *
 * <p>我们提供了 {@link io.github.phiseecodyhsp.arcstory.view.TextPlayer}
 * 类来播放文本. 它是该类的一个简单的包装类, 并实现了 View 层的文本渲染功能.
 *
 * <p>需要注意的是, 该类仍属于 <b>View 层</b>, 因为其使用了 JavaFX 动画.
 * 因此, 你<b>不应该</b>在 ViewModel 层使用这个类.
 *
 * @see io.github.phiseecodyhsp.arcstory.view.TextPlayer
 *
 * @author RikkaKawaii0612
 */
public class Typewriter {

    /**
     * 每个字符播放的间隔时长, 以秒为单位.
     */
    private static final double INTERVAL = 0.05D;

    /**
     * 空行停顿的原理为插入大量空格, 这里计算插入空格的数量.
     */
    private static final String SPACES = " ".repeat((int) (1.5D / INTERVAL));

    private Timeline timeline;

    private final StringProperty currentText = new SimpleStringProperty();

    private final IntegerProperty currentIndex = new SimpleIntegerProperty();

    private final ObjectProperty<Runnable> onFinished = new SimpleObjectProperty<>();

    private final StringProperty fullText = new SimpleStringProperty();

    public Typewriter() {
        this.currentText.bind(this.currentIndex.map(i -> {
            String str = this.fullText.getValueSafe();
            return str.substring(Math.clamp((int) i, 0, str.length()));
        }));
    }

    public void play(@NotNull String text) {
        if (this.timeline != null) {
            this.timeline.stop();
        }

        this.fullText.setValue(text.replaceAll("(?m)^$", SPACES).replaceAll("\\R", "\n"));
        this.currentIndex.setValue(0);

        if (text.isEmpty()) {
            return;
        }

        int s = this.fullText.getValue().length();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(INTERVAL),
                _ -> this.currentIndex.setValue(1 + this.currentIndex.getValue())));
        this.timeline.setCycleCount(s);
        this.timeline.setOnFinished(_ -> {
            this.timeline = null;
            this.handleOnFinished();
        });

        this.timeline.play();
    }

    public void stop() {
        if (this.timeline != null) {
            this.timeline.stop();
        }
        this.currentIndex.setValue(this.fullText.length().getValue());
        this.handleOnFinished();
    }

    private void handleOnFinished() {
        Runnable runnable = this.onFinished.get();
        if (runnable != null) {
            runnable.run();
        }
    }

    public boolean isPlaying() {
        return this.timeline != null;
    }

    public String getCurrentText() {
        return this.currentText.get();
    }

    public ReadOnlyStringProperty currentTextProperty() {
        return this.currentText;
    }

    public int getCurrentIndex() {
        return this.currentIndex.get();
    }

    public IntegerProperty currentIndexProperty() {
        return this.currentIndex;
    }

    public String getFullText() {
        return this.fullText.get();
    }

    public StringProperty fullTextProperty() {
        return this.fullText;
    }

    public Runnable getOnFinished() {
        return this.onFinished.get();
    }

    public void setOnFinished(Runnable action) {
        this.onFinished.setValue(action);
    }

    public ObjectProperty<Runnable> onFinishedProperty() {
        return this.onFinished;
    }
}
