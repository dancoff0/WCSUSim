import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollBar;
import javax.swing.event.TableModelEvent;
import javax.swing.JCheckBox;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import javax.swing.Box;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionListener;

// 
// Decompiled by Procyon v0.5.30
// 
@SuppressWarnings("unused")
public class GUI implements ActionListener, TableModelListener
{
    private final Machine mac;
    public static String LOOKANDFEEL;
    private final JFrame frame;
    private final JFileChooser fileChooser;
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenu aboutMenu;
    private final JMenuItem openItem;
    private final JMenuItem quitItem;
    private final JMenuItem commandItem;
    private final JMenuItem versionItem;
    private final String openActionCommand = "Open";
    private final String quitActionCommand = "Quit";
    private final String openCOWActionCommand = "OutputWindow";
    private final String versionActionCommand = "Version";
    private final JPanel leftPanel;
    private final JPanel controlPanel;
    private final JButton nextButton;
    private final String nextButtonCommand = "Next";
    private final JButton stepButton;
    private final String stepButtonCommand = "Step";
    private final JButton continueButton;
    private final String continueButtonCommand = "Continue";
    private final JButton stopButton;
    private final String stopButtonCommand = "Stop";
    private final String statusLabelRunning = "    Running ";
    private final String statusLabelSuspended = "Suspended ";
    private final String statusLabelHalted = "       Halted ";
    private final JLabel statusLabel;
    private final Color runningColor;
    private final Color suspendedColor;
    private final Color haltedColor;
    private final JTable regTable;
    private final CommandLinePanel commandPanel;
    private final CommandOutputWindow commandOutputWindow;
    private final JPanel memoryPanel;
    private final JTable memTable;
    private final JScrollPane memScrollPane;
    public static final Color BreakPointColor;
    public static final Color PCColor;
    private final JPanel devicePanel;
    private final JPanel registerPanel;
    private final TextConsolePanel ioPanel;
    private final VideoConsole video;
    
    private void setupMemoryPanel() {
        this.memoryPanel.add(this.memScrollPane, "Center");
        this.memoryPanel.setMinimumSize(new Dimension(400, 100));
        this.memoryPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Memory"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.memTable.getModel().addTableModelListener(this);
        this.memTable.getModel().addTableModelListener(this.video);
        this.memTable.getModel().addTableModelListener((TableModelListener)this.memScrollPane.getVerticalScrollBar());
        this.memTable.setPreferredScrollableViewportSize(new Dimension(400, 460));
    }
    
    private void setupControlPanel() {
        this.controlPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        this.nextButton.setActionCommand("Next");
        this.nextButton.addActionListener(this);
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.controlPanel.add(this.nextButton, gridBagConstraints);
        this.stepButton.setActionCommand("Step");
        this.stepButton.addActionListener(this);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.controlPanel.add(this.stepButton, gridBagConstraints);
        this.continueButton.setActionCommand("Continue");
        this.continueButton.addActionListener(this);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        this.controlPanel.add(this.continueButton, gridBagConstraints);
        this.stopButton.setActionCommand("Stop");
        this.stopButton.addActionListener(this);
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        this.controlPanel.add(this.stopButton, gridBagConstraints);
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 0;
        gridBagConstraints.anchor = 22;
        this.setStatusLabelSuspended();
        this.controlPanel.add(this.statusLabel, gridBagConstraints);
        final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.gridwidth = 6;
        this.controlPanel.add(Box.createRigidArea(new Dimension(5, 5)), gridBagConstraints2);
        final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 2;
        gridBagConstraints3.gridwidth = 6;
        gridBagConstraints3.gridheight = 1;
        gridBagConstraints3.ipady = 100;
        gridBagConstraints3.weightx = 1.0;
        gridBagConstraints3.weighty = 1.0;
        gridBagConstraints3.fill = 1;
        this.controlPanel.add(this.commandPanel, gridBagConstraints3);
        this.controlPanel.setMinimumSize(new Dimension(100, 150));
        this.controlPanel.setPreferredSize(new Dimension(100, 150));
        this.controlPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Controls"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.controlPanel.setVisible(true);
    }
    
    private void setupRegisterPanel() {
        this.registerPanel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = 2;
        this.registerPanel.add(this.regTable, gridBagConstraints);
        this.registerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Registers"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.registerPanel.setVisible(true);
    }
    
    private void setupDevicePanel() {
        this.devicePanel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 10;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        this.devicePanel.add(this.video, gridBagConstraints);
        final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.fill = 0;
        this.devicePanel.add(this.ioPanel, gridBagConstraints2);
        this.devicePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Devices"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.devicePanel.setVisible(true);
    }
    
    public GUI(final Machine mac, final CommandLine commandLine) {
        this.frame = new JFrame("PennSim - " + PennSim.version + " - " + PennSim.getISA());
        this.fileChooser = new JFileChooser(".");
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.aboutMenu = new JMenu("About");
        this.openItem = new JMenuItem("Open .obj File");
        this.quitItem = new JMenuItem("Quit");
        this.commandItem = new JMenuItem("Open Command Output Window");
        this.versionItem = new JMenuItem("Simulator Version");
        this.leftPanel = new JPanel();
        this.controlPanel = new JPanel();
        this.nextButton = new JButton("Next");
        this.stepButton = new JButton("Step");
        this.continueButton = new JButton("Continue");
        this.stopButton = new JButton("Stop");
        this.statusLabel = new JLabel("");
        this.runningColor = new Color(43, 129, 51);
        this.suspendedColor = new Color(209, 205, 93);
        this.haltedColor = new Color(161, 37, 40);
        this.memoryPanel = new JPanel(new BorderLayout());
        this.devicePanel = new JPanel();
        this.registerPanel = new JPanel();
        this.mac = mac;
        this.regTable = new JTable(mac.getRegisterFile());
        final TableColumn column = this.regTable.getColumnModel().getColumn(0);
        column.setMaxWidth(30);
        column.setMinWidth(30);
        final TableColumn column2 = this.regTable.getColumnModel().getColumn(2);
        column2.setMaxWidth(30);
        column2.setMinWidth(30);
        this.memTable = new JTable(mac.getMemory()) {
            /**
			 * Generated SerialID
			 */
			private static final long serialVersionUID = -6422775795423096001L;

			public Component prepareRenderer(final TableCellRenderer tableCellRenderer, final int n, final int n2) {
                final Component prepareRenderer = super.prepareRenderer(tableCellRenderer, n, n2);
                if (n2 == 0) {
                    final JCheckBox checkBox = new JCheckBox();
                    if (n < 65024) {
                        if (GUI.this.mac.getMemory().isBreakPointSet(n)) {
                            checkBox.setSelected(true);
                            checkBox.setBackground(GUI.BreakPointColor);
                            checkBox.setForeground(GUI.BreakPointColor);
                        }
                        else {
                            checkBox.setSelected(false);
                            checkBox.setBackground(this.getBackground());
                        }
                    }
                    else {
                        checkBox.setEnabled(false);
                        checkBox.setBackground(Color.lightGray);
                    }
                    return checkBox;
                }
                if (n == GUI.this.mac.getRegisterFile().getPC()) {
                    prepareRenderer.setBackground(GUI.PCColor);
                }
                else if (GUI.this.mac.getMemory().isBreakPointSet(n)) {
                    prepareRenderer.setBackground(GUI.BreakPointColor);
                }
                else {
                    prepareRenderer.setBackground(this.getBackground());
                }
                return prepareRenderer;
            }
            
            public void tableChanged(final TableModelEvent tableModelEvent) {
                if (mac != null) {
                    super.tableChanged(tableModelEvent);
                }
            }
        };
        this.memScrollPane = new JScrollPane(this.memTable) {
            /**
			 * Generated SerialID
			 */
			private static final long serialVersionUID = -2450534793070167023L;

			public JScrollBar createVerticalScrollBar() {
                return new HighlightScrollBar(mac);
            }
        };
        this.memScrollPane.getVerticalScrollBar().setBlockIncrement(this.memTable.getModel().getRowCount() / 512);
        this.memScrollPane.getVerticalScrollBar().setUnitIncrement(1);
        final TableColumn column3 = this.memTable.getColumnModel().getColumn(0);
        column3.setMaxWidth(20);
        column3.setMinWidth(20);
        column3.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        final TableColumn column4 = this.memTable.getColumnModel().getColumn(2);
        column4.setMinWidth(50);
        column4.setMaxWidth(50);
        this.commandPanel = new CommandLinePanel(mac, commandLine);
        (this.commandOutputWindow = new CommandOutputWindow("Command Output")).addWindowListener(new WindowListener() {
            public void windowActivated(final WindowEvent windowEvent) {
            }
            
            public void windowClosed(final WindowEvent windowEvent) {
            }
            
            public void windowClosing(final WindowEvent windowEvent) {
                GUI.this.commandOutputWindow.setVisible(false);
            }
            
            public void windowDeactivated(final WindowEvent windowEvent) {
            }
            
            public void windowDeiconified(final WindowEvent windowEvent) {
            }
            
            public void windowIconified(final WindowEvent windowEvent) {
            }
            
            public void windowOpened(final WindowEvent windowEvent) {
            }
        });
        this.commandOutputWindow.setSize(700, 600);
        Console.registerConsole(this.commandPanel);
        Console.registerConsole(this.commandOutputWindow);
        (this.ioPanel = new TextConsolePanel(mac.getMemory().getKeyBoardDevice(), mac.getMemory().getMonitorDevice())).setMinimumSize(new Dimension(256, 85));
        this.video = new VideoConsole(mac);
        this.commandPanel.setGUI(this);
    }
    
    public void setUpGUI() {
        initLookAndFeel();
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.mac.setStoppedListener(this.commandPanel);
        this.fileChooser.setFileSelectionMode(2);
        this.fileChooser.addChoosableFileFilter(new FileFilter() {
            public boolean accept(final File file) {
                if (file.isDirectory()) {
                    return true;
                }
                final String name = file.getName();
                return name != null && name.toLowerCase().endsWith(".obj");
            }
            
            public String getDescription() {
                return "*.obj";
            }
        });
        this.openItem.setActionCommand("Open");
        this.openItem.addActionListener(this);
        this.fileMenu.add(this.openItem);
        this.commandItem.setActionCommand("OutputWindow");
        this.commandItem.addActionListener(this);
        this.fileMenu.add(this.commandItem);
        this.fileMenu.addSeparator();
        this.quitItem.setActionCommand("Quit");
        this.quitItem.addActionListener(this);
        this.fileMenu.add(this.quitItem);
        this.versionItem.setActionCommand("Version");
        this.versionItem.addActionListener(this);
        this.aboutMenu.add(this.versionItem);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.aboutMenu);
        this.frame.setJMenuBar(this.menuBar);
        this.setupControlPanel();
        this.setupDevicePanel();
        this.setupMemoryPanel();
        this.setupRegisterPanel();
        this.regTable.getModel().addTableModelListener(this);
        this.frame.getContentPane().setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.gridwidth = 0;
        this.frame.getContentPane().add(this.controlPanel, gridBagConstraints);
        final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.gridwidth = 1;
        gridBagConstraints2.gridheight = 1;
        gridBagConstraints2.weightx = 0.0;
        gridBagConstraints2.fill = 2;
        this.frame.getContentPane().add(this.registerPanel, gridBagConstraints2);
        final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 2;
        gridBagConstraints3.weightx = 0.0;
        gridBagConstraints3.gridheight = 1;
        gridBagConstraints3.gridwidth = 1;
        gridBagConstraints3.fill = 1;
        this.frame.getContentPane().add(this.devicePanel, gridBagConstraints3);
        final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
        gridBagConstraints4.gridx = 1;
        gridBagConstraints4.gridy = 1;
        gridBagConstraints4.gridheight = 2;
        gridBagConstraints4.gridwidth = 0;
        gridBagConstraints4.fill = 1;
        gridBagConstraints4.weightx = 1.0;
        this.frame.getContentPane().add(this.memoryPanel, gridBagConstraints4);
        this.frame.setSize(new Dimension(700, 725));
        this.frame.setDefaultCloseOperation(3);
        this.frame.pack();
        this.frame.setVisible(true);
        this.scrollToPC();
        this.commandPanel.actionPerformed(null);
    }
    
    public void scrollToIndex(final int n) {
        this.memTable.scrollRectToVisible(this.memTable.getCellRect(n, 0, true));
    }
    
    public void scrollToPC() {
        this.scrollToPC(0);
    }
    
    public void scrollToPC(final int n) {
        this.memTable.scrollRectToVisible(this.memTable.getCellRect(this.mac.getRegisterFile().getPC() + n, 0, true));
    }
    
    public void tableChanged(final TableModelEvent tableModelEvent) {
        if (!this.mac.isContinueMode()) {}
    }
    
    public void confirmExit() {
        final Object[] array = { "Yes", "No" };
        if (JOptionPane.showOptionDialog(this.frame, "Are you sure you want to quit?", "Quit verification", 0, 3, null, array, array[1]) == 0) {
            this.mac.cleanup();
            System.exit(0);
        }
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        try {
            try {
                this.scrollToIndex(Integer.parseInt(actionEvent.getActionCommand()));
            }
            catch (NumberFormatException ex2) {
                if ("Next".equals(actionEvent.getActionCommand())) {
                    this.mac.executeNext();
                }
                else if ("Step".equals(actionEvent.getActionCommand())) {
                    this.mac.executeStep();
                }
                else if ("Continue".equals(actionEvent.getActionCommand())) {
                    this.mac.executeMany();
                }
                else if ("Quit".equals(actionEvent.getActionCommand())) {
                    this.confirmExit();
                }
                else if ("Stop".equals(actionEvent.getActionCommand())) {
                    Console.println(this.mac.stopExecution(true));
                }
                else if ("OutputWindow".equals(actionEvent.getActionCommand())) {
                    this.commandOutputWindow.setVisible(true);
                }
                else if ("Version".equals(actionEvent.getActionCommand())) {
                    JOptionPane.showMessageDialog(this.frame, PennSim.getVersion(), "Version", 1);
                }
                else if ("Open".equals(actionEvent.getActionCommand())) {
                    if (this.fileChooser.showOpenDialog(this.frame) == 0) {
                        Console.println(this.mac.loadObjectFile(this.fileChooser.getSelectedFile()));
                    }
                    else {
                        Console.println("Open command cancelled by user.");
                    }
                }
            }
        }
        catch (ExceptionException ex) {
            ex.showMessageDialog(this.frame);
        }
    }
    
    public static void initLookAndFeel() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        if (GUI.LOOKANDFEEL != null) {
            String lookAndFeel;
            if (GUI.LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            else if (GUI.LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            }
            else if (GUI.LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            }
            else if (GUI.LOOKANDFEEL.equals("GTK+")) {
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            }
            else {
                ErrorLog.logError("Unexpected value of LOOKANDFEEL specified: " + GUI.LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            try {
                UIManager.setLookAndFeel(lookAndFeel);
            }
            catch (ClassNotFoundException ex2) {
                ErrorLog.logError("Couldn't find class for specified look and feel:" + lookAndFeel);
                ErrorLog.logError("Did you include the L&F library in the class path?");
                ErrorLog.logError("Using the default look and feel.");
            }
            catch (UnsupportedLookAndFeelException ex3) {
                ErrorLog.logError("Can't use the specified look and feel (" + lookAndFeel + ") on this platform.");
                ErrorLog.logError("Using the default look and feel.");
            }
            catch (Exception ex) {
                ErrorLog.logError("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.");
                ErrorLog.logError("Using the default look and feel.");
                ErrorLog.logError(ex);
            }
        }
    }
    
    public JFrame getFrame() {
        return this.frame;
    }
    
    public void setStatusLabelRunning() {
        this.statusLabel.setText("    Running ");
        this.statusLabel.setForeground(this.runningColor);
    }
    
    public void setStatusLabelSuspended() {
        this.statusLabel.setText("Suspended ");
        this.statusLabel.setForeground(this.suspendedColor);
    }
    
    public void setStatusLabelHalted() {
        this.statusLabel.setText("       Halted ");
        this.statusLabel.setForeground(this.haltedColor);
    }
    
    public void setStatusLabel(final boolean b) {
        if (b) {
            this.setStatusLabelSuspended();
        }
        else {
            this.setStatusLabelHalted();
        }
    }
    
    public void setTextConsoleEnabled(final boolean enabled) {
        this.ioPanel.setEnabled(enabled);
    }
    
    public void reset() {
        this.setTextConsoleEnabled(true);
        this.commandPanel.reset();
        this.video.reset();
        this.scrollToPC();
    }
    
    static {
        GUI.LOOKANDFEEL = "Metal";
        BreakPointColor = new Color(241, 103, 103);
        PCColor = Color.YELLOW;
    }
}
