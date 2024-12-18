
package javaapplication37;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class User {
    private double balance; // Biến lưu số dư hiện tại của người chơi

    public User(double balance) {
        this.balance = balance; // Gán số dư ban đầu khi tạo người chơi mới
    }

    public double getBalance() {
        return balance; // Trả về số dư hiện tại
    }

    public void updateBalance(double amount) {
        balance += amount; // Cập nhật số dư (cộng hoặc trừ số tiền)
    }
}

public class TaiXiuGameApp extends JFrame {
    private User user; // Đối tượng người chơi
    private JLabel balanceLabel; // Nhãn hiển thị số dư
    private JTextField betAmountField; // Ô nhập số tiền cược
    private JComboBox<String> betChoiceBox; // Lựa chọn Tài hoặc Xỉu
    private JTextArea resultArea; // Khu vực hiển thị kết quả
    private JButton playButton; // Nút chơi
    private JButton resetButton; // Nút đặt lại

    public TaiXiuGameApp() {
        user = new User(1000); // Khởi tạo người chơi với số dư ban đầu 1000

        setTitle("Tai Xiu Game"); // Tiêu đề cửa sổ
        setSize(500, 400); // Kích thước cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng ứng dụng khi tắt cửa sổ
        setLayout(new BorderLayout(10, 10)); // Sắp xếp các thành phần giao diện

        // Panel trên cùng hiển thị số dư
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Căn giữa các thành phần
        balanceLabel = new JLabel("Balance: " + user.getBalance()); // Nhãn hiển thị số dư
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Định dạng phông chữ
        topPanel.add(balanceLabel);
        add(topPanel, BorderLayout.NORTH);

        // Panel trung tâm chứa các tùy chọn và nút
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel betChoiceLabel = new JLabel("Bet Choice:"); // Nhãn lựa chọn cược
        betChoiceLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Định dạng phông chữ
        centerPanel.add(betChoiceLabel);

        betChoiceBox = new JComboBox<>(new String[]{"Tai", "Xiu"}); // Thả xuống chọn Tài hoặc Xỉu
        centerPanel.add(betChoiceBox);

        JLabel betAmountLabel = new JLabel("Bet Amount:"); // Nhãn nhập số tiền cược
        betAmountLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Định dạng phông chữ
        centerPanel.add(betAmountLabel);

        betAmountField = new JTextField(); // Ô nhập số tiền cược
        centerPanel.add(betAmountField);

        playButton = new JButton("Play"); // Nút chơi
        playButton.setFont(new Font("Arial", Font.BOLD, 14)); // Định dạng phông chữ
        centerPanel.add(playButton);

        resetButton = new JButton("Reset"); // Nút đặt lại trò chơi
        resetButton.setFont(new Font("Arial", Font.BOLD, 14)); // Định dạng phông chữ
        centerPanel.add(resetButton);

        add(centerPanel, BorderLayout.CENTER);

        // Panel dưới cùng hiển thị kết quả
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JLabel resultLabel = new JLabel("Game Results:"); // Nhãn kết quả trò chơi
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Định dạng phông chữ
        bottomPanel.add(resultLabel, BorderLayout.NORTH);

        resultArea = new JTextArea(8, 40); // Khu vực hiển thị kết quả
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Định dạng phông chữ
        resultArea.setEditable(false); // Không cho phép chỉnh sửa
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        // Chức năng nút Play
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playGame(); // Gọi phương thức chơi game
            }
        });

        // Chức năng nút Reset
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame(); // Gọi phương thức đặt lại game
            }
        });

        setVisible(true); // Hiển thị giao diện
    }

    private void playGame() {
        try {
            String betChoice = (String) betChoiceBox.getSelectedItem(); // Lấy lựa chọn cược
            double betAmount = Double.parseDouble(betAmountField.getText()); // Lấy số tiền cược

            if (betAmount <= 0) { // Kiểm tra số tiền cược hợp lệ
                resultArea.append("Bet amount must be greater than zero!\n");
                JOptionPane.showMessageDialog(this, "Bet amount must be greater than zero!", "Invalid Bet", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (betAmount > user.getBalance()) { // Kiểm tra số dư đủ cược
                resultArea.append("Bet amount exceeds your balance!\n");
                JOptionPane.showMessageDialog(this, "Bet amount exceeds your balance!", "Insufficient Balance", JOptionPane.WARNING_MESSAGE);
                return;
            }

            user.updateBalance(-betAmount); // Trừ số tiền cược khỏi số dư
            Random rand = new Random(); // Tạo đối tượng ngẫu nhiên
            int dice1 = rand.nextInt(6) + 1; // Xúc xắc 1
            int dice2 = rand.nextInt(6) + 1; // Xúc xắc 2
            int dice3 = rand.nextInt(6) + 1; // Xúc xắc 3
            int total = dice1 + dice2 + dice3; // Tổng số điểm

            String outcome = total >= 11 ? "Tai" : "Xiu"; // Xác định kết quả Tài hoặc Xỉu

            if (outcome.equalsIgnoreCase(betChoice)) { // Kiểm tra người chơi thắng hay thua
                double winnings = betAmount * 2; // Tính tiền thắng
                user.updateBalance(winnings); // Cộng số tiền thắng vào số dư
                resultArea.append("You won! Dice: " + dice1 + ", " + dice2 + ", " + dice3 + " (Total: " + total + ")\n");
                JOptionPane.showMessageDialog(this, "You won! Congratulations!", "Game Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resultArea.append("You lost! Dice: " + dice1 + ", " + dice2 + ", " + dice3 + " (Total: " + total + ")\n");
                JOptionPane.showMessageDialog(this, "You lost! Better luck next time!", "Game Result", JOptionPane.INFORMATION_MESSAGE);
            }

            balanceLabel.setText("Balance: " + user.getBalance()); // Cập nhật số dư hiển thị
        } catch (NumberFormatException ex) {
            resultArea.append("Invalid bet amount! Please enter a numeric value.\n");
            JOptionPane.showMessageDialog(this, "Invalid bet amount! Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetGame() {
        user = new User(1000); // Đặt lại số dư ban đầu
        balanceLabel.setText("Balance: " + user.getBalance()); // Cập nhật nhãn số dư
        resultArea.setText(""); // Xóa kết quả hiển thị
        betAmountField.setText(""); // Xóa ô nhập số tiền cược
        betChoiceBox.setSelectedIndex(0); // Đặt lại lựa chọn Tài/Xỉu
        resultArea.append("Game reset! Your balance has been restored to 1000.\n");
        JOptionPane.showMessageDialog(this, "Game has been reset. Balance restored to 1000.", "Game Reset", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaiXiuGameApp()); // Chạy ứng dụng
    }
}