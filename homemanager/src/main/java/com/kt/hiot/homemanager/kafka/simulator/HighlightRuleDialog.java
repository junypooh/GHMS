package com.kt.hiot.homemanager.kafka.simulator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class HighlightRuleDialog extends JDialog {
    

	private static final long serialVersionUID = 1L;

	public HighlightRuleDialog(Dialog owner, HighlightRule rule) {
        super(owner);
        
        _ruleField = new JTextField(rule.getName());
        _textField = new JTextField(rule.getRegexp());
        _bold.setSelected(rule.getBold());
        _underlined.setSelected(rule.getUnderlined());
        _filtered.setSelected(rule.getFiltered());
        _beep.setSelected(rule.getBeep());
        _colorField = new JTextField("#" + Integer.toHexString(rule.getColor().getRGB()).substring(2));
        
        Container pane = this.getContentPane();
        JPanel panel = new JPanel(new GridLayout(12, 1));
        
        pane.add(panel, BorderLayout.CENTER);
        
        panel.add(_ruleField);
        panel.add(new JLabel("If the line matches this regular expression:"));
        panel.add(_textField);
        panel.add(new JLabel("then:"));
        panel.add(_bold);
        panel.add(_underlined);
        panel.add(_filtered);
        panel.add(_beep);
        panel.add(new JLabel("and color the text with:"));
        panel.add(_colorField);
        panel.add(_okay);
        panel.add(_cancel);
        
        _okay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    _rule = new HighlightRule(_ruleField.getText(), _textField.getText(), _underlined.isSelected(), _bold.isSelected(), _filtered.isSelected(), _beep.isSelected(), Color.decode(_colorField.getText()));
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(HighlightRuleDialog.this, "Your regular expression syntax is not valid, please try again.", "Regexp failed", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                dispose();
            }
        });
        
        _cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setTitle("Rule specification");
        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    public HighlightRule getRule() {
        return _rule;
    }
    
    private JTextField _textField;
    private JTextField _colorField;
    private JTextField _ruleField;
    
    private JCheckBox _bold = new JCheckBox("Highlight in bold");
    private JCheckBox _underlined = new JCheckBox("Underline the text");
    private JCheckBox _filtered = new JCheckBox("Do not display the line");
    private JCheckBox _beep = new JCheckBox("Make a beep sound");
    
    private JButton _okay = new JButton("Okay");
    private JButton _cancel = new JButton("Cancel");
    
    private HighlightRule _rule = null;
    
}