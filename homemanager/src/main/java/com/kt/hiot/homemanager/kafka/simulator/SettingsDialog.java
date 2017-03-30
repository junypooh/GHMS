package com.kt.hiot.homemanager.kafka.simulator;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class SettingsDialog extends JDialog {
    

	private static final long serialVersionUID = 1L;

	public SettingsDialog(Frame owner, LogTail tailer) {
        super(owner);
        _tailer = tailer;
        _list= new JList(_tailer.getRules().toArray());
        
        Container pane = this.getContentPane();
        
        JPanel rulesPanel = new JPanel(new BorderLayout());
        JPanel buttonsPanel = new JPanel(new GridLayout(5, 1));
        
        buttonsPanel.add(_newRule);
        buttonsPanel.add(_modifyRule);
        buttonsPanel.add(_deleteRule);
        buttonsPanel.add(_moveUp);
        buttonsPanel.add(_moveDown);

        _newRule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                HighlightRule rule = new HighlightRule("NewRule", "^.*$", false, false, false, false, Color.black);
                HighlightRuleDialog dialog = new HighlightRuleDialog(SettingsDialog.this, rule);
                rule = dialog.getRule();
                
                if (rule != null) {
                    ArrayList<HighlightRule> rules = _tailer.getRules();
                    rules.add(0, rule);
                    _list.setListData(rules.toArray());
                }
            }
        });
        
        _modifyRule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int selection = _list.getSelectedIndex();
                if (selection >= 0) {
                    ArrayList<HighlightRule> rules = _tailer.getRules();
                    HighlightRule rule = (HighlightRule)rules.get(selection);
                    HighlightRuleDialog dialog = new HighlightRuleDialog(SettingsDialog.this, rule);
                    if (dialog.getRule() != null) {
                        rules.set(selection, dialog.getRule());
                    }
                    _list.setListData(rules.toArray());
                }
            }
        });
        
        _deleteRule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int selection = _list.getSelectedIndex();
                if (selection >= 0) {
                    ArrayList<HighlightRule> rules = _tailer.getRules();
                    rules.remove(selection);
                    _list.setListData(rules.toArray());
                }
            }
        });
        
        _moveUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int selection = _list.getSelectedIndex();
                if (selection > 0) {
                    ArrayList<HighlightRule> rules = _tailer.getRules();
                    HighlightRule temp = rules.get(selection);
                    rules.set(selection, rules.get(selection - 1));
                    rules.set(selection - 1, temp);
                    _list.setListData(rules.toArray());
                    _list.setSelectedIndex(selection - 1);
                }
            }
        });
        
        _moveDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int selection = _list.getSelectedIndex();
                ArrayList<HighlightRule> rules = _tailer.getRules();
                if (selection < rules.size() - 1) {
                	HighlightRule temp = rules.get(selection + 1);
                    rules.set(selection + 1, rules.get(selection));
                    rules.set(selection, temp);
                    _list.setListData(rules.toArray());
                    _list.setSelectedIndex(selection + 1);
                }
            }
        });
        
        _okay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        
        JScrollPane scroller = new JScrollPane(_list);
        scroller.setPreferredSize(new Dimension(200, 300));
        
        rulesPanel.add(new JLabel("Ordered rules settings"), BorderLayout.NORTH);
        rulesPanel.add(scroller, BorderLayout.CENTER);
        rulesPanel.add(buttonsPanel, BorderLayout.EAST);
        rulesPanel.add(_okay, BorderLayout.SOUTH);
        
        pane.add(rulesPanel, BorderLayout.CENTER);
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setTitle("Options for " + _tailer.getFilename());
        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    private JList _list;
    private LogTail _tailer;
    
    private JButton _newRule = new JButton("New");
    private JButton _modifyRule = new JButton("Modify");
    private JButton _deleteRule = new JButton("Delete");
    private JButton _moveUp = new JButton("Move up");
    private JButton _moveDown = new JButton("Move down");
    private JButton _okay = new JButton("Okay");
    
}