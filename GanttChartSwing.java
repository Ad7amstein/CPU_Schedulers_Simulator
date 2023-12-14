import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GanttChartSwing extends JFrame {
    Scheduler scheduler;
    String title;
    int maxTime;
    private JPanel[][] gridPanels;
    public GanttChartSwing(Scheduler sch, String title) {
        this.scheduler = sch;
        this.title = title;
        this.maxTime = scheduler.calculateMaxTime();
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(title);
        setLocationRelativeTo(null);
        int rows = scheduler.complete_list.size() + 1, columns = maxTime + 2;
        gridPanels = new JPanel[rows][columns];

        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, columns));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(30, 30));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanels[i][j] = panel;

                gbc.gridx = j;
                gbc.gridy = i;

                gridPanel.add(panel, gbc);
            }
        }

        addHeaders();

        add(gridPanel, BorderLayout.CENTER);
        JPanel textPanel = new JPanel();

        JLabel textLabel = new JLabel("");
        textPanel.add(textLabel);
        add(textPanel, BorderLayout.SOUTH);
        for (Map.Entry<String, List<Pair>> entry : scheduler.execution_order.entrySet()) {
            String name = entry.getKey();
            Process p = scheduler.getProcess(name);
            for(Pair pair: entry.getValue()){
                int st = pair.start, end = pair.end;
                for (int i = st+1; i <= end + 1; i++) {
                    JPanel curr = gridPanels[Integer.parseInt(name.substring(1))][i];
                    curr.setBackground(p.color);
                }
            }

        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addHeaders() {
        for (int i = 1; i <= scheduler.complete_list.size(); i++) {
            JPanel curr = gridPanels[i][0];
            JLabel label = new JLabel("P" + i);
            curr.add(label);
        }
        for (int i = 1; i <= maxTime + 1; i++) {
            JPanel curr = gridPanels[0][i];
            JLabel label = new JLabel(String.valueOf(i-1));
            curr.add(label);
        }
    }
}