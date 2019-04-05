import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser
import java.awt.BorderLayout
import java.net.InetAddress
import javax.swing.*
import com.sun.jna.platform.win32.BaseTSD



class GUI{
    private val frame = JFrame("Android Control")
    private val label = JLabel("Press the button to reveal your IP.")
    private val button = JButton("Reveal")
    private val panel = JPanel()
    private val panel2 = JPanel()
    private val textField = JTextField(30)

    fun createGUI(){
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(500, 200)

        textField.isEditable = false
        textField.text
        panel.add(label)
        textField.horizontalAlignment = JTextField.CENTER
        panel2.add(textField, BorderLayout.WEST)
        panel2.add(button, BorderLayout.EAST)
        frame.contentPane.add(BorderLayout.PAGE_START, panel)
        frame.contentPane.add(BorderLayout.CENTER, panel2)
        frame.isVisible = true

        button.addActionListener {
            val address = InetAddress.getLocalHost()
            textField.text = address.hostAddress

        }

    }
}