package com.izforge.izpack.installer.panel;

import java.util.List;


/**
 * Manages navigation between panels.
 *
 * @author Tim Anderson
 */
public interface Panels<T extends PanelView>
{

    /**
     * Returns the panels.
     *
     * @return the panels
     */
    List<T> getPanels();

    /**
     * Returns the current panel.
     *
     * @return the current panel, or {@code null} if there is no current panel
     */
    T getPanel();

    /**
     * Returns the current panel index.
     *
     * @return the current panel index, or {@code -1} if there is no current panel
     */
    int getIndex();

    /**
     * Determines if there is another panel after the current panel.
     *
     * @return {@code true} if there is another panel
     */
    boolean hasNext();

    /**
     * Navigates to the next panel.
     *
     * @return {@code true} if the next panel was navigated to
     */
    boolean next();

    /**
     * Navigates to the next panel.
     *
     * @param validate if {@code true}, only move to the next panel if validation succeeds
     * @return {@code true} if the next panel was navigated to
     */
    boolean next(boolean validate);

    /**
     * Determines if the next panel may be navigated to.
     *
     * @param enable if {@code true}, enable navigation, otherwise disable it
     */
    void setNextEnabled(boolean enable);

    /**
     * Determines if navigation to the next panel has been enabled.
     * <p/>
     * return {@code true} if navigation is enabled
     */
    boolean isNextEnabled();

    /**
     * Navigates to the previous panel.
     *
     * @return {@code true} if the previous panel was navigated to
     */
    boolean previous();

    /**
     * Determines if the previous panel may be navigated to.
     *
     * @param enable if {@code true}, enable navigation, otherwise disable it
     */
    void setPreviousEnabled(boolean enable);

    /**
     * Determines if navigation to the previous panel has been enabled.
     * <p/>
     * return {@code true} if navigation is enabled
     */
    boolean isPreviousEnabled();

    /**
     * Navigates to the panel before the specified index.
     * <br/>
     * The target panel must be before the current panel.
     *
     * @return {@code true} if the previous panel was navigated to
     */
    boolean previous(int index);

    /**
     * Determines if there is another panel after the specified index.
     *
     * @param index       the panel index
     * @param visibleOnly if {@code true}, only examine visible panels
     * @return the next panel index, or {@code -1} if there are no more panels
     */
    int getNext(int index, boolean visibleOnly);

    /**
     * Determines if there is another panel prior to the specified index.
     *
     * @param index       the panel index
     * @param visibleOnly if {@code true}, only examine visible panels
     * @return the previous panel index, or {@code -1} if there are no more panels
     */
    int getPrevious(int index, boolean visibleOnly);

    /**
     * Returns the index of a visible panel, relative to other visible panels.
     *
     * @param panel the panel
     * @return the panel's visible index, or {@code -1} if the panel is not visible
     */
    int getVisibleIndex(T panel);

    /**
     * Returns the number of visible panels.
     *
     * @return the number of visible panels
     */
    int getVisible();
}