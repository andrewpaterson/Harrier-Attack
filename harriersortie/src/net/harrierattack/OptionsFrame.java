package net.harrierattack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class OptionsFrame extends JFrame
{
  public int exitType;
  public int mountains;
  public boolean showStatistics;

  public OptionsFrame() throws HeadlessException
  {
    super("Options");
    exitType = 0;
    mountains = 4;
    showStatistics = true;
    setSize(new Dimension(320, 120));
    setLocation(160, 120);

    JButton fullScreenButton = new JButton("Fullscreen");
    fullScreenButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        exitType = 1;
      }
    });

    JButton windowedButton = new JButton("Windowed");
    windowedButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        exitType = 2;
      }
    });

    JButton exitButton = new JButton("Exit");
    exitButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        exitType = 3;
      }
    });

    JLabel mountainLabel = new JLabel("Mountain layers:");
    JComboBox mountainNumber = new JComboBox(new Object[]{"0", "1", "2", "3", "4"});
    mountainNumber.addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent e)
      {
        if (e.getStateChange() == ItemEvent.SELECTED)
        {
          mountains = Integer.valueOf((String) e.getItem());
        }
      }
    });
    mountainNumber.setSelectedItem("4");

    JCheckBox checkBox = new JCheckBox("Show Statistics");
    checkBox.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        showStatistics = checkBox.isSelected();
      }
    });
    checkBox.setSelected(true);

    this.setLayout(new GridBagLayout());

    JPanel mountainPanel = new JPanel(new GridBagLayout());
    this.add(mountainPanel, constraints(0, 0, 1, 1));
    mountainPanel.add(mountainLabel, constraints(0, 0, 1, 0));
    mountainPanel.add(mountainNumber, constraints(1, 0, 1, 0));

    this.add(checkBox, constraints(0, 1, 1, 1));

    this.add(new JPanel(), constraints(0, 2, 1, 1));

    JPanel buttonPanel = new JPanel(new GridBagLayout());
    this.add(buttonPanel, constraints(0, 3, 1, 1));
    buttonPanel.add(windowedButton, constraints(0, 0, 1, 0));
    buttonPanel.add(fullScreenButton, constraints(1, 0, 1, 0));
    buttonPanel.add(exitButton, constraints(2, 0, 1, 0));
  }

  private GridBagConstraints constraints(int gridx, int gridy, double weightx, double weighty)
  {
    return new GridBagConstraints(gridx, gridy, 1, 1, weightx, weighty, GridBagConstraints.BASELINE, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
  }
}
