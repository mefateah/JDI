package com.epam.jdi.uitests.gui.sikuli.elements.composite;

import com.epam.commons.map.MapArray;
import com.epam.jdi.uitests.core.interfaces.complex.IForm;
import com.epam.jdi.uitests.gui.sikuli.elements.base.Element;

import java.util.List;

/**
 * Created by Natalia_Grebenshchikova on 1/14/2016.
 */
public class Form<T> extends Element implements IForm<T> {

    /**
     * @param map Specify entity as map
     *            Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity
     */
    @Override
    public void fill(MapArray<String, String> map) {

    }

    /**
     * @param map Specify entity as mapArray
     *            Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity
     */
    @Override
    public List<String> verify(MapArray<String, String> map) {
        return null;
    }

    /**
     * @param map Specify entity as mapArray
     *            Verify that form filled correctly. If not throws error
     */
    @Override
    public void check(MapArray<String, String> map) {

    }

    /**
     * @param text Specify text
     *             Fill first setable field with value and click on Button “submit” <br>
     * @apiNote To use this option Form pageObject should have at least one ISetValue element and only one IButton Element
     */
    @Override
    public void submit(String text) {

    }

    /**
     * @param text       Specify text
     * @param buttonName button name for form submiting
     *                   Fill first setable field with value and click on Button “buttonName” <br>
     * @apiNote To use this option Form pageObject should have at least one ISetValue element <br>
     * Allowed different buttons to send one form e.g. save/ publish / cancel / search update ...
     */
    @Override
    public void submit(String text, String buttonName) {

    }

    /**
     * @param buttonName Specify Button Name
     * @param entity     Specify entity
     *                   Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     * @apiNote To use this option Form pageObject should have button names in specific format <br>
     * e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     * * Letters case in button name  no matters
     */
    @Override
    public void submit(T entity, String buttonName) {

    }

    /**
     * @param buttonName Specify Button Name
     * @param entity     Specify entity
     *                   Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     * @apiNote To use this option Form pageObject should have button names in specific format <br>
     * e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     * * Letters case in button name  no matters
     */
    @Override
    public void submit(T entity, Enum buttonName) {

    }

    /**
     * @param objStrings Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     * @apiNote To use this option Form pageObject should have button names in specific format <br>
     * e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     * * Letters case in button name  no matters
     */
    @Override
    public void submit(MapArray<String, String> objStrings) {

    }

    /**
     * @param value Specify element value
     *              Set value to Element
     */
    @Override
    public void setValue(String value) {

    }

    /**
     * @return Get value of Element
     */
    @Override
    public String getValue() {
        return null;
    }
}
