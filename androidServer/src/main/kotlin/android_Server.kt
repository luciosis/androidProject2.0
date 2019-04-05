import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sun.jna.platform.win32.BaseTSD
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinDef.DWORD
import com.sun.jna.platform.win32.WinUser
import java.awt.*
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

const val SERVER_PORT = 8998
var isConnected = true
lateinit var input : BufferedReader
lateinit var client: Socket
lateinit var server: ServerSocket
val gson = Gson()
val robot: Robot = Robot()
val windowsInput: WinUser.INPUT = WinUser.INPUT()
val gui = GUI()


fun main(args : Array<String>){

    try {
        server = ServerSocket(SERVER_PORT)
        gui.createGUI()
        client = server.accept()

        input = BufferedReader(InputStreamReader(client.getInputStream()))
    }
    catch(e: IOException){
        print("Error in opening the socket")
        System.exit(-1)
    }
    catch(e: AWTException){
        print("Error in creating the robot instance")
        System.exit(-1)
    }
    while(isConnected){
        try {
            val line = input.readLine()
            val json = gson.fromJson<JsonObject>(line, JsonObject::class.java)
            val type = json.get("type").asString

            when (type) {
                "player" -> when (json.get("value").asString) {
                    "next" -> keyboardPress(0xB0)
                    "play" -> keyboardPress(0xB3)
                    "previous" -> keyboardPress(0xB1)
                    "volume up" -> keyboardPress(0xAF)
                    "volume down" -> keyboardPress(0xAE)
                    "mute" -> keyboardPress(0xAD)
                }
                "mouse" -> {
                    val movex: Float = json.get("x").asFloat//extract movement in x direction
                    val movey: Float = json.get("y").asFloat//extract movement in y direction
                    val point: Point = MouseInfo.getPointerInfo().location//Get current mouse position
                    val nowx: Float = point.x.toFloat()
                    val nowy: Float = point.y.toFloat()
                    robot.mouseMove((nowx + movex).toInt(), (nowy + movey).toInt())
                }
                "mouseclick" -> when (json.get("value").asString) {
                    "Left click" -> mousePress("left", json.get("isPressed").asBoolean)
                    "Right click" -> mousePress("right", json.get("isPressed").asBoolean)
                }
                "keyboard" -> keyboardPress(json.get("value").asCharacter)
                "keyboard_action" -> {
                    when (json.get("value").asString) {
                        "BACKSPACE" -> {
                            robot.keyPress(KeyEvent.VK_BACK_SPACE)
                            robot.keyRelease(KeyEvent.VK_BACK_SPACE)
                        }
                        "ENTER" -> {
                            robot.keyPress(KeyEvent.VK_ENTER)
                            robot.keyRelease(KeyEvent.VK_ENTER)
                        }
                    }

                }
            }
        }
        catch(e: IOException){
            println("Read failed")
            System.exit(-1)
        }
        catch(e: NullPointerException){
            client.close()
            input.close()
            client = server.accept()
            input = BufferedReader(InputStreamReader(client.getInputStream()))
        }
    }
}
fun keyboardPress(keycode: Long){
    windowsInput.type = DWORD(WinUser.INPUT.INPUT_KEYBOARD.toLong())
    windowsInput.input.setType("ki") // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
    windowsInput.input.ki.wScan = WinDef.WORD(0)
    windowsInput.input.ki.time = DWORD(0)
    windowsInput.input.ki.dwExtraInfo = BaseTSD.ULONG_PTR(0)

    windowsInput.input.ki.wVk = WinDef.WORD(keycode)
    windowsInput.input.ki.dwFlags = DWORD(0)
    User32.INSTANCE.SendInput(DWORD(1), windowsInput.toArray(1) as Array<WinUser.INPUT>, windowsInput.size())
    windowsInput.input.ki.wVk = WinDef.WORD(keycode) // 0x41
    windowsInput.input.ki.dwFlags = DWORD(2)  // keyup

    User32.INSTANCE.SendInput(DWORD(1), windowsInput.toArray(1) as Array<WinUser.INPUT>, windowsInput.size())
}
fun keyboardPress(keycode: Char) {
    try {

        val uppercase = keycode.isUpperCase()
        if (symbolMap.containsKey(keycode)) {
            robot.keyPress(KeyEvent.VK_SHIFT)
            robot.keyPress(AWTKeyStroke.getAWTKeyStroke(symbolMap[keycode].toString()).keyCode)
            robot.keyRelease(KeyEvent.VK_SHIFT)
        }
        else if(altCasesMap.containsKey(keycode)){
            robot.keyPress(KeyEvent.VK_CONTROL)
            robot.keyPress(KeyEvent.VK_ALT)
            robot.keyPress(AWTKeyStroke.getAWTKeyStroke(altCasesMap[keycode].toString()).keyCode)
            robot.keyRelease(KeyEvent.VK_CONTROL)
            robot.keyRelease(KeyEvent.VK_ALT)
        }
        else if(keycode == ' '){
            robot.keyPress(KeyEvent.VK_SPACE)
            robot.keyRelease(KeyEvent.VK_SPACE)

        }
        else {
            val key = keycode.toUpperCase()
            if (uppercase) {
                robot.keyPress(KeyEvent.VK_SHIFT)
                robot.keyPress(AWTKeyStroke.getAWTKeyStroke(key.toString()).keyCode)
                robot.keyRelease(KeyEvent.VK_SHIFT)
            } else {
                if(!specialCasesMap.containsKey(key)) {
                    robot.keyPress(AWTKeyStroke.getAWTKeyStroke(key.toString()).keyCode)
                }
                else{
                    robot.keyPress(specialCasesMap[key]!!.toInt())
                }
            }
        }
    }
    catch (e: Exception){
        robot.keyRelease(KeyEvent.VK_SHIFT)
    }
}
fun mousePress(input: String, isPressed: Boolean){
    if(input == "left") {
        if (isPressed) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        } else {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
        }
    }
    if(input == "right") {
        if (isPressed) {
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK)
        } else {
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK)
        }
    }
}
val symbolMap: Map<Char, Int> = mapOf(
        '!' to 1,
        '"' to 2,
        '#' to 3,
        '%' to 5,
        '&' to 6,
        '/' to 7,
        '(' to 8,
        ')' to 9,
        '=' to 6,
        '?' to '+'.toInt(),
        ':' to '.'.toInt(),
        ';' to ','.toInt(),
        '_' to KeyEvent.VK_MINUS
)
val specialCasesMap: Map<Char, Int> = mapOf(
        '.' to KeyEvent.VK_PERIOD,
        ',' to KeyEvent.VK_COMMA,
        '\'' to KeyEvent.VK_QUOTE,
        '+' to KeyEvent.VK_PLUS,
        '-' to KeyEvent.VK_MINUS
)
val altCasesMap: Map<Char, Int> = mapOf(
        '@' to 2,
        '£' to 3,
        '$' to 4,
        '€' to 5,
        '{' to 7,
        '[' to 8,
        ']' to 9,
        '}' to 0
)