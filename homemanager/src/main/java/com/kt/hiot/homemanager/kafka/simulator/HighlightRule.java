package com.kt.hiot.homemanager.kafka.simulator;

import java.awt.Color;
import javax.swing.text.*;
import java.util.regex.*;

public class HighlightRule implements java.io.Serializable {
    
	private static final long serialVersionUID = 1L;
	
	public HighlightRule() {
        this("Default", "^.*$", false, false, false, false, Color.black);
    }
    
    public HighlightRule(String name, String regexp, boolean underlined, boolean bold, boolean filtered, boolean beep, Color color) throws PatternSyntaxException {
        _name = name;
        _regexp = regexp;
        _pattern = Pattern.compile(regexp);
        _underlined = underlined;
        _bold = bold;
        _beep = beep;
        _filtered = filtered;
        _color = color;
    }
    
    public void alterAttributeSet(SimpleAttributeSet attr) {
        StyleConstants.setForeground(attr, _color);
        StyleConstants.setBold(attr, _bold);
        StyleConstants.setUnderline(attr, _underlined);
    }
    
    public String getName() {
        return _name;
    }
    
    public String toString() {
        return getName();
    }
    
    public String getRegexp() {
        return _regexp;
    }
    
    public Pattern getPattern() {
        return _pattern;
    }
    
    public boolean getFiltered() {
        return _filtered;
    }
    
    public boolean getBold() {
        return _bold;
    }
    
    public boolean getUnderlined() {
        return _underlined;
    }
    
    public boolean getBeep() {
        return _beep;
    }
    
    public Color getColor() {
        return _color;
    }

    private String _name;
    private String _regexp;
    private Pattern _pattern;
    private boolean _underlined;
    private boolean _bold;
    private boolean _filtered;
    private boolean _beep;
    private Color _color = null;
}