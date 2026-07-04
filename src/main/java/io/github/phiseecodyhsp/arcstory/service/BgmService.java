package io.github.phiseecodyhsp.arcstory.service;

import io.github.phiseecodyhsp.arcstory.res.ResourceLocation;

/**
 * 管理 BGM 的服务.
 *
 * @author RikkaKawaii0612
 */
public interface BgmService {

    /**
     * 播放指定的 BGM.
     *
     * @param location BGM 资源路径
     */
    void playBgm(ResourceLocation location);

    /**
     * 立即终止当前播放的 BGM. 如果当前没有播放 BGM, 则什么都不做.
     */
    void stopBgm();

    /**
     * 淡出当前的 BGM.
     *
     * @param seconds 淡出时长
     */
    void fadeOutBgm(double seconds);

    /**
     * 获取当前播放的 BGM, 包括正在淡出的 BGM.
     *
     * @return 当前播放 BGM 的资源路径, 若没有 BGM 播放则为 {@code null}
     */
    ResourceLocation getCurrentBgm();

    /**
     * 判断当前是否正在播放 BGM, 包括正在淡出的 BGM.
     *
     * @return 当前是否正在播放 BGM
     */
    boolean isPlaying();

    /**
     * 设置 BGM 的播放音量.
     *
     * @param volume 播放音量
     */
    void setVolume(double volume);

    /**
     * 获取当前 BGM 的播放音量.
     *
     * @return 当前播放音量
     */
    double getVolume();
}
